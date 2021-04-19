package jp.co.ysd.internal_document_search_service.elasticsearch;

import static jp.co.ysd.ysd_util.stream.StreamWrapperFactory.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

import org.apache.commons.io.IOUtils;
import org.elasticsearch.ElasticsearchStatusException;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.data.elasticsearch.core.query.UpdateResponse;
import org.springframework.data.elasticsearch.core.query.UpdateResponse.Result;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import jp.co.ysd.internal_document_search_service.elasticsearch.SearchConditionGenerator.SearchCondition;
import jp.co.ysd.ysd_util.tuple.Tuple2;

/**
 *
 * @author yuichi
 *
 */
@Component
public class ElasticsearchDao {

	private static final String SETTINGS = "elasticsearch-settings.json";

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private RestHighLevelClient highLevelClient;

	@Autowired
	private ElasticsearchOperations operations;

	@Autowired
	private SearchConditionGenerator searchConditionGenerator;

	@Autowired
	private ElasticsearchDateExpression dateExpression;

	private <T> String getIndexPrefix(Class<T> clazz) {
		return clazz.getAnnotation(org.springframework.data.elasticsearch.annotations.Document.class).indexName()
				.replaceAll("#\\{.*?\\}", "");
	}

	private <T> String getWildcardIndexName(Class<T> clazz) {
		return getIndexPrefix(clazz) + "*";
	}

	private <T> IndexCoordinates getWildcardIndexCoordinates(Class<T> clazz) {
		return IndexCoordinates.of(getWildcardIndexName(clazz));
	}

	public <T> List<T> search(Class<T> clazz, List<String> targetFields, String query,
			Function<Tuple2<SearchCondition, T>, T> processor) {
		SearchCondition condition = searchConditionGenerator.getSearchCondition(targetFields, query);
		NativeSearchQuery nsq = condition.getNativeSearchQuery();
		SearchHits<T> searchHits = operations.search(nsq, clazz, getWildcardIndexCoordinates(clazz));
		return stream(searchHits.getSearchHits()).map(sh -> sh.getContent()).map(doc -> {
			return processor.apply(new Tuple2<>(condition, doc));
		}).end();
	}

	public <T> List<T> perfectMatchSearch(Class<T> clazz, String targetField, String query) {
		NativeSearchQuery nsq = searchConditionGenerator.getPerfectMatchCondition(targetField, query);
		SearchHits<T> searchHits = operations.search(nsq, clazz, getWildcardIndexCoordinates(clazz));
		return stream(searchHits.getSearchHits()).map(sh -> sh.getContent()).end();
	}

	private <T> List<SearchHit<T>> readWrapperByIds(Class<T> clazz, List<String> ids) {
		NativeSearchQuery nsq = searchConditionGenerator.getIdsCondition(ids);
		SearchHits<T> searchHits = operations.search(nsq, clazz, getWildcardIndexCoordinates(clazz));
		return searchHits.getSearchHits();
	}

	private <T> SearchHit<T> readWrapperById(Class<T> clazz, String id) {
		List<SearchHit<T>> list = readWrapperByIds(clazz, Arrays.asList(id));
		return list.size() == 1 ? list.get(0) : null;
	}

	public <T> List<T> readByIds(Class<T> clazz, List<String> ids) {
		return stream(readWrapperByIds(clazz, ids)).map(w -> w.getContent()).end();
	}

	public <T> T readById(Class<T> clazz, String id) {
		List<T> list = readByIds(clazz, Arrays.asList(id));
		return list.size() == 1 ? list.get(0) : null;
	}

	public <T> T create(T doc) throws IOException {
		IndexOperations indexOpts = operations.indexOps(doc.getClass());
		if (!indexOpts.exists()) {
			try (InputStream settings = getClass().getClassLoader().getResourceAsStream(SETTINGS)) {
				indexOpts.create(Document.parse(IOUtils.toString(settings, Charset.defaultCharset())));
			}
		}
		return operations.save(doc);
	}

	public <T> T update(Class<T> clazz, String id, Function<T, T> merger) throws Exception {
		SearchHit<T> searchHit = readWrapperById(clazz, id);
		if (searchHit == null) {
			return null;
		}
		T prev = searchHit.getContent();
		T next = merger.apply(prev);
		Document d = Document.parse(objectMapper.writeValueAsString(next));
		UpdateQuery uq = UpdateQuery.builder(id).withDocument(d).build();
		UpdateResponse ur = operations.update(uq, IndexCoordinates.of(searchHit.getIndex()));
		if (ur.getResult() == Result.UPDATED) {
			return next;
		} else {
			throw new Exception();
		}
	}

	public <T> List<String> deleteByIds(Class<T> clazz, List<String> ids) {
		List<SearchHit<T>> searchHits = readWrapperByIds(clazz, ids);
		if (CollectionUtils.isEmpty(searchHits)) {
			return Collections.emptyList();
		}
		return stream(searchHits).map(sh -> operations.delete(sh.getId(), IndexCoordinates.of(sh.getIndex()))).end();
	}

	public <T> String deleteById(Class<T> clazz, String id) {
		List<String> list = deleteByIds(clazz, Arrays.asList(id));
		return list.size() == 1 ? list.get(0) : null;
	}

	public void deleteIndexIfExists(String indexName) {
		IndexOperations indexOpts = operations.indexOps(IndexCoordinates.of(indexName));
		if (indexOpts.exists()) {
			indexOpts.delete();
		}
	}

	public <T> void deleteIndexLessThanDate(Class<T> clazz, Date date) throws IOException {
		GetIndexRequest request = new GetIndexRequest(getWildcardIndexName(clazz));
		try {
			GetIndexResponse response = highLevelClient.indices().get(request, RequestOptions.DEFAULT);
			stream(response.getIndices())
					.filter(i -> i.compareTo(getIndexPrefix(clazz) + dateExpression.format(date)) <= 0)
					.forEach(this::deleteIndexIfExists);
		} catch (ElasticsearchStatusException e) {
			if (!e.getMessage().contains("index_not_found_exception")) {
				throw e;
			}
		}
	}

}
