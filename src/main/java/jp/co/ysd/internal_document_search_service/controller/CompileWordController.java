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
import jp.co.ysd.internal_document_search_service.model.CompileWord;
import jp.co.ysd.internal_document_search_service.service.CompileWordService;

/**
 *
 * @author yuichi
 *
 */
@RestController
@RequestMapping(Route.COMPILE_WORD)
public class CompileWordController {

	@Autowired
	private CompileWordService service;

	@GetMapping("{id}")
	public CompileWord get(@PathVariable long id) {
		return service.readById(id);
	}

	@GetMapping
	public List<CompileWord> getByCompileWorsGroupId(@RequestParam long compileWordGroupId) {
		return service.readByCompileWordGourpId(compileWordGroupId);
	}

	@PostMapping
	public CompileWord post(@RequestBody CompileWord cw) {
		return service.create(cw);
	}

	@PutMapping("{id}")
	public CompileWord put(@PathVariable long id, @RequestBody CompileWord cw) {
		return service.update(id, cw);
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
