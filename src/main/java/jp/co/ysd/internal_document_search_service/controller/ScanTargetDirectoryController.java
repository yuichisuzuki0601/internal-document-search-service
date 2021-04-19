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
import jp.co.ysd.internal_document_search_service.model.ScanTargetDirectory;
import jp.co.ysd.internal_document_search_service.service.ScanTargetDirectoryService;

/**
 *
 * @author yuichi
 *
 */
@RestController
@RequestMapping(Route.SCAN_TARGET_DIRECTORY)
public class ScanTargetDirectoryController {

	@Autowired
	private ScanTargetDirectoryService service;

	@GetMapping
	public List<ScanTargetDirectory> get() {
		return service.read();
	}

	@GetMapping("{id}")
	public ScanTargetDirectory get(@PathVariable long id) {
		return service.readById(id);
	}

	@PostMapping
	public ScanTargetDirectory post(@RequestBody ScanTargetDirectory ctd) {
		return service.create(ctd);
	}

	@PutMapping("{id}")
	public ScanTargetDirectory put(@PathVariable long id, @RequestBody ScanTargetDirectory ctd) {
		return service.update(id, ctd);
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
