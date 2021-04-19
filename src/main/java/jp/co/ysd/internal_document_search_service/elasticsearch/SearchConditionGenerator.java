package jp.co.ysd.internal_document_search_service.elasticsearch;

import static jp.co.ysd.ysd_util.stream.StreamWrapperFactory.*;

import java.util.Collections;
import java.util.List;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import jp.co.ysd.internal_document_search_service.model.CompileWord;
import jp.co.ysd.internal_document_search_service.service.CompileWordService;
import lombok.Data;

/**
 *
 * @author yuichi
 *
 */
@Component
public class SearchConditionGenerator {

	@Data
	public static class SearchCondition {
		private final NativeSearchQuery nativeSearchQuery;
		private final List<String> allWords;
	}

	@Autowired
	private CompileWordService compileWordService;

	private NativeSearchQueryBuilder getNativeSearchQueryBuilder() {
		NativeSearchQueryBuilder nsqb = new NativeSearchQueryBuilder();
		nsqb.withSort(SortBuilders.fieldSort("updatedAt").order(SortOrder.DESC));
		// nsqb.withPageable(PageRequest.of(0, 10000));
		return nsqb;
	}

	private List<String> minifiedToList(String minified) {
		return !ObjectUtils.isEmpty(minified) ? stream(minified.split("(&|\\|)")).end() : Collections.emptyList();
	}

	public SearchCondition getSearchCondition(List<String> targetFields, String query) {
		NativeSearchQueryBuilder nsqb = getNativeSearchQueryBuilder();
		// 空文字の場合は全検索
		if (ObjectUtils.isEmpty(query)) {
			nsqb.withQuery(QueryBuilders.matchAllQuery());
			return new SearchCondition(nsqb.build(), Collections.emptyList());
		}
		// 圧縮(共通のセパレータに統一)
		String minified = query.replaceAll("(\\s|　)+OR(\\s|　)+", "|").replaceAll("(\\s|　)+", "&");
		// 翻訳ワードコンパイル
		for (CompileWord cw : compileWordService.readByCompileBefores(minifiedToList(minified))) {
			minified = minified.replaceAll(cw.getCompileBefore(), cw.getCompileAfter());
		}
		// 検索条件オブジェクトの作成
		BoolQueryBuilder bqb = QueryBuilders.boolQuery();
		for (String and : minified.split("&")) {
			BoolQueryBuilder sbqb = QueryBuilders.boolQuery();
			for (String or : and.split("\\|")) {
				if (!or.startsWith("-")) {
					for (String targetField : targetFields) {
						sbqb.should(QueryBuilders.matchPhraseQuery(targetField, or));
					}
				} else {
					// -から始まる文字列は除外対象
					for (String targetField : targetFields) {
						sbqb.mustNot(QueryBuilders.matchPhraseQuery(targetField, or.replaceFirst("-", "")));
					}
				}
			}
			bqb.filter(sbqb);
		}
		nsqb.withQuery(bqb);
		return new SearchCondition(nsqb.build(),
				stream(minifiedToList(minified)).filter(s -> !s.startsWith("-")).end());
	}

	public NativeSearchQuery getPerfectMatchCondition(String targetField, String query) {
		QueryBuilder qb = QueryBuilders.termQuery(targetField + ".keyword", query);
		return getNativeSearchQueryBuilder().withQuery(qb).build();
	}

	public NativeSearchQuery getIdsCondition(List<String> ids) {
		QueryBuilder qb = QueryBuilders.idsQuery().addIds(ids.toArray(new String[ids.size()]));
		return getNativeSearchQueryBuilder().withQuery(qb).build();
	}

}
