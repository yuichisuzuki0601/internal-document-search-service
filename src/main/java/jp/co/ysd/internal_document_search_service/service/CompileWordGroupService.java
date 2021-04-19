package jp.co.ysd.internal_document_search_service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.ysd.internal_document_search_service.Const;
import jp.co.ysd.internal_document_search_service.model.CompileWordGroup;
import jp.co.ysd.ysd_dao.bean.Query;
import jp.co.ysd.ysd_dao.dao.PojoDao;

/**
 *
 * @author yuichi
 *
 */
@Service
public class CompileWordGroupService {

	@Autowired
	private PojoDao dao;

	@Autowired
	@Qualifier("compileWordGroupsSelect")
	private Query select;

	@Autowired
	@Qualifier("compileWordGroupsSelectById")
	private Query selectById;

	@Autowired
	@Qualifier("compileWordGroupsInsert")
	private Query insert;

	@Autowired
	@Qualifier("compileWordGroupsUpdateById")
	private Query update;

	@Autowired
	@Qualifier("compileWordGroupsDeleteByIds")
	private Query deleteByIds;

	@Autowired
	@Qualifier("compileWordGroupsDeleteById")
	private Query deleteById;

	public List<CompileWordGroup> read() {
		return dao.query(CompileWordGroup.class, select);
	}

	public CompileWordGroup readById(long id) {
		return dao.queryById(CompileWordGroup.class, selectById, id);
	}

	@Transactional
	public CompileWordGroup create(CompileWordGroup cwg) {
		cwg.setCreatedBy(Const.OWNER);
		cwg.setUpdatedBy(Const.OWNER);
		cwg.setId(dao.insert(insert, cwg));
		return cwg;
	}

	@Transactional
	public CompileWordGroup update(long id, CompileWordGroup cwg) {
		cwg.setId(id);
		cwg.setUpdatedBy(Const.OWNER);
		return dao.updateForSingle(update, cwg);
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
