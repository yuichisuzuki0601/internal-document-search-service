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
import jp.co.ysd.internal_document_search_service.model.CompileWordGroup;
import jp.co.ysd.internal_document_search_service.service.CompileWordGroupService;

/**
 *
 * @author yuichi
 *
 */
@RestController
@RequestMapping(Route.COMPILE_WORD_GROUP)
public class CompileWordGroupController {

	@Autowired
	private CompileWordGroupService service;

	@GetMapping
	public List<CompileWordGroup> get() {
		return service.read();
	}

	@GetMapping("{id}")
	public CompileWordGroup get(@PathVariable long id) {
		return service.readById(id);
	}

	@PostMapping
	public CompileWordGroup post(@RequestBody CompileWordGroup cwg) {
		return service.create(cwg);
	}

	@PutMapping("{id}")
	public CompileWordGroup put(@PathVariable long id, @RequestBody CompileWordGroup cwg) {
		return service.update(id, cwg);
	}

	@DeleteMapping(params = "ids")
	public List<Long> deleteByIds(@RequestParam List<Long> ids) {
		service.deleteByIds(ids);
		return ids;
	}

	@DeleteMapping("{id}")
	public long deleteById(@PathVariable long id) {
		service.deleteById(id);
		return id;
	}

}
