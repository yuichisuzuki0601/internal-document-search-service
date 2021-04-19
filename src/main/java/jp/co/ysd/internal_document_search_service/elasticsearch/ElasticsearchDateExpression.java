package jp.co.ysd.internal_document_search_service.elasticsearch;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Date;
import java.util.TimeZone;

import org.springframework.stereotype.Component;

import jp.co.ysd.internal_document_search_service.Const;

/**
 *
 * @author yuichi
 *
 */
@Component
public class ElasticsearchDateExpression {

	private static final DateFormat DATE_FORMAT = new SimpleDateFormat(Const.DATE_FORMAT);
	static {
		DATE_FORMAT.setTimeZone(TimeZone.getTimeZone(ZoneId.of("UTC")));
	}

	public String today() {
		return DATE_FORMAT.format(new Date());
	}

	public String format(Date target) {
		return DATE_FORMAT.format(target);
	}

}
