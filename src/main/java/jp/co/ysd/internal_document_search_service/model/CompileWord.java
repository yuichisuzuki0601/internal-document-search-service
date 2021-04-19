package jp.co.ysd.internal_document_search_service.model;

import java.util.Date;

import lombok.Data;

/**
 *
 * @author yuichi
 *
 */
@Data
public class CompileWord {

	private long id;
	private long compileWordGroupId;
	private String compileBefore;
	private String compileAfter;
	private Date createdAt;
	private String createdBy;
	private Date updatedAt;
	private String updatedBy;

}
