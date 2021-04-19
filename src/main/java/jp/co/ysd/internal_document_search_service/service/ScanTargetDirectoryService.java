package jp.co.ysd.internal_document_search_service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.ysd.internal_document_search_service.Const;
import jp.co.ysd.internal_document_search_service.model.ScanTargetDirectory;
import jp.co.ysd.ysd_dao.bean.Query;
import jp.co.ysd.ysd_dao.dao.PojoDao;

/**
 *
 * @author yuichi
 *
 */
@Service
public class ScanTargetDirectoryService {

	@Autowired
	private PojoDao dao;

	@Autowired
	@Qualifier("scanTargetDirectoriesSelect")
	private Query select;

	@Autowired
	@Qualifier("scanTargetDirectoriesSelectById")
	private Query selectById;

	@Autowired
	@Qualifier("scanTargetDirectoriesInsert")
	private Query insert;

	@Autowired
	@Qualifier("scanTargetDirectoriesUpdateById")
	private Query update;

	@Autowired
	@Qualifier("scanTargetDirectoriesDeleteByIds")
	private Query deleteByIds;

	@Autowired
	@Qualifier("scanTargetDirectoriesDeleteById")
	private Query deleteById;

	public List<ScanTargetDirectory> read() {
		return dao.query(ScanTargetDirectory.class, select);
	}

	public ScanTargetDirectory readById(long id) {
		return dao.queryById(ScanTargetDirectory.class, selectById, id);
	}

	@Transactional
	public ScanTargetDirectory create(ScanTargetDirectory std) {
		std.setCreatedBy(Const.OWNER);
		std.setUpdatedBy(Const.OWNER);
		std.setId(dao.insert(insert, std));
		return std;
	}

	@Transactional
	public ScanTargetDirectory update(long id, ScanTargetDirectory std) {
		std.setId(id);
		std.setUpdatedBy(Const.OWNER);
		return dao.updateForSingle(update, std);
	}

	@Transactional
	public boolean deleteByIds(List<Long> ids) {
		return dao.deleteByIds(deleteByIds, ids) > 0;
	}

	@Transactional
	public boolean deleteById(long id) {
		return dao.deleteById(deleteById, id);
	}

}
