package jp.co.ysd.internal_document_search_service.service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.ysd.internal_document_search_service.Const;
import jp.co.ysd.internal_document_search_service.elasticsearch.ElasticsearchDao;
import jp.co.ysd.internal_document_search_service.elasticsearch.SearchConditionGenerator.SearchCondition;
import jp.co.ysd.internal_document_search_service.model.Article;
import jp.co.ysd.internal_document_search_service.util.HtmlDecorator;

/**
 *
 * @author yuichi
 *
 */
@Service
public class ArticleService {

	@Autowired
	private ElasticsearchDao dao;

	public List<Article> search(String query) {
		return dao.search(Article.class, Arrays.asList("content"), query, t -> {
			SearchCondition condition = t.one();
			List<String> allWords = condition.getAllWords();
			Article article = t.two();
			article.setContent(new HtmlDecorator(article.getContent()).htmlize().emphasize(allWords).toString());
			return article;
		});
	}

	public Article searchByCode(String code) throws Exception {
		List<Article> articles = dao.perfectMatchSearch(Article.class, "code", code);
		if (articles.isEmpty()) {
			return null;
		} else if (articles.size() == 1) {
			return articles.get(0);
		} else {
			throw new Exception("cannot narrow down.");
		}
	}

	public List<Article> readByIds(List<String> ids) {
		return dao.readByIds(Article.class, ids);
	}

	public Article readById(String id) {
		return dao.readById(Article.class, id);
	}

	@Transactional
	public Article create(Article article) throws Exception {
		if (searchByCode(article.getCode()) != null) {
			throw new Exception("already exists.");
		}
		article.setCreatedBy(Const.OWNER);
		article.setUpdatedBy(Const.OWNER);
		return dao.create(article);
	}

	@Transactional
	public Article update(String id, Article article) throws Exception {
		return dao.update(Article.class, id, prev -> {
			article.setId(id);
			article.setCreatedAt(prev.getCreatedAt());
			article.setCreatedBy(prev.getCreatedBy());
			article.setUpdatedAt(new Date());
			article.setUpdatedBy(Const.OWNER);
			return article;
		});
	}

	@Transactional
	public List<String> deleteByIds(List<String> ids) {
		return dao.deleteByIds(Article.class, ids);
	}

	@Transactional
	public String deleteById(String id) {
		return dao.deleteById(Article.class, id);
	}

	@Transactional
	public void deleteLessThanDate(Date date) throws Exception {
		dao.deleteIndexLessThanDate(Article.class, date);
	}

}
