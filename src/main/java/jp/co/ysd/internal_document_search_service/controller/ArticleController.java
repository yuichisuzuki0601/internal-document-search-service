package jp.co.ysd.internal_document_search_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jp.co.ysd.internal_document_search_service.Route;
import jp.co.ysd.internal_document_search_service.model.Article;
import jp.co.ysd.internal_document_search_service.service.ArticleService;

/**
 *
 * @author yuichi
 *
 */
@RestController
@RequestMapping(Route.ARTICLE)
public class ArticleController {

	@Autowired
	private ArticleService service;

	@GetMapping(value = "/search", params = "query")
	public List<Article> search(@RequestParam String query) {
		return service.search(query);
	}

	@GetMapping(params = "ids")
	public List<Article> getByIds(@RequestParam List<String> ids) {
		return service.readByIds(ids);
	}

	@GetMapping("{id}")
	public Article getById(@PathVariable String id) {
		return service.readById(id);
	}

	@PostMapping
	public Article post(@RequestBody Article article) throws Exception {
		return service.create(article);
	}

	@PutMapping("{id}")
	public Article put(@PathVariable String id, @RequestBody Article article) throws Exception {
		return service.update(id, article);
	}

	@DeleteMapping(params = "ids")
	public List<String> deleteByIds(@RequestParam List<String> ids) {
		return service.deleteByIds(ids);
	}

	@DeleteMapping("{id}")
	public String deleteById(@PathVariable String id) {
		return service.deleteById(id);
	}

}
