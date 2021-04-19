
package jp.co.ysd.internal_document_search_service.model;

import java.util.Date;

import lombok.Data;

/**
 *
 * @author yuichi
 *
 */
@Data
public class CompileWordGroup {

	private long id;
	private String name;
	private Date createdAt;
	private String createdBy;
	private Date updatedAt;
	private String updatedBy;

}
