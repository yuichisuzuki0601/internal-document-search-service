package jp.co.ysd.internal_document_search_service.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import lombok.Data;

/**
 *
 * @author yuichi
 *
 */
@Data
@Document(indexName = "article-#{@elasticsearchDateExpression.today()}")
public class Article {

	@Id
	private String id;

	@Field(type = FieldType.Keyword)
	private String code;

	@Field(type = FieldType.Text)
	private String content;

	@Field(type = FieldType.Keyword)
	private String result;

	@Field(type = FieldType.Date, format = DateFormat.date_time)
	private Date createdAt = new Date();

	@Field(type = FieldType.Keyword)
	private String createdBy;

	@Field(type = FieldType.Date, format = DateFormat.date_time)
	private Date updatedAt = new Date();

	@Field(type = FieldType.Keyword)
	private String updatedBy;

}
