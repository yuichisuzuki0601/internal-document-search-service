package jp.co.ysd.internal_document_search_service.model;

import java.util.Date;

import lombok.Data;

/**
 *
 * @author yuichi
 *
 */
@Data
public class ScanTargetDirectory {

	private long id;
	private String name;
	private String path;
	private String extension;
	private Date createdAt;
	private String createdBy;
	private Date updatedAt;
	private String updatedBy;

}
