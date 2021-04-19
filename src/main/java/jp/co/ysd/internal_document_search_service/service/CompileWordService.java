package jp.co.ysd.internal_document_search_service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.ysd.internal_document_search_service.Const;
import jp.co.ysd.internal_document_search_service.model.CompileWord;
import jp.co.ysd.ysd_dao.bean.Query;
import jp.co.ysd.ysd_dao.dao.PojoDao;
import jp.co.ysd.ysd_util.map.MapBuilder;

/**
 *
 * @author yuichi
 *
 */
@Service
public class CompileWordService {

	@Autowired
	private PojoDao dao;

	@Autowired
	@Qualifier("compileWordsSelectById")
	private Query selectById;

	@Autowired
	@Qualifier("compileWordsSelectByCompileWordGroupId")
	private Query selectByCompileWordGroupId;

	@Autowired
	@Qualifier("compileWordsSelectByCompileBefores")
	private Query selectByCompileBefores;

	@Autowired
	@Qualifier("compileWordsInsert")
	private Query insert;

	@Autowired
	@Qualifier("compileWordsUpdateById")
	private Query updateById;

	@Autowired
	@Qualifier("compileWordsDeleteByIds")
	private Query deleteByIds;

	@Autowired
	@Qualifier("compileWordsDeleteById")
	private Query deleteById;

	public CompileWord readById(long id) {
		return dao.queryById(CompileWord.class, selectById, id);
	}

	public List<CompileWord> readByCompileWordGourpId(long cwgId) {
		MapBuilder param = new MapBuilder("compileWordGroupId", cwgId);
		return dao.query(CompileWord.class, selectByCompileWordGroupId, param.build());
	}

	public List<CompileWord> readByCompileBefores(List<String> compileBefores) {
		MapBuilder param = new MapBuilder("compileBefores", compileBefores);
		return dao.query(CompileWord.class, selectByCompileBefores, param.build());
	}

	@Transactional
	public CompileWord create(CompileWord cw) {
		cw.setCreatedBy(Const.OWNER);
		cw.setUpdatedBy(Const.OWNER);
		cw.setId(dao.insert(insert, cw));
		return cw;
	}

	@Transactional
	public CompileWord update(long id, CompileWord cw) {
		cw.setId(id);
		cw.setUpdatedBy(Const.OWNER);
		return dao.updateForSingle(updateById, cw);
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
