package jp.co.ysd.internal_document_search_service.batch.task;

import java.util.Date;

import javax.annotation.Priority;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jp.co.ysd.internal_document_search_service.batch.BatchTask;
import jp.co.ysd.internal_document_search_service.service.ArticleService;
import jp.co.ysd.ysd_util.date.YsdDateUtil;

/**
 *
 * @author yuichi
 *
 */
@Component
@Priority(2)
public class PeriodicDeleteIndexTask extends BatchTask {

	@Value("${batch.periodicDeleteIndexTask.execute:false}")
	private boolean execute;

	@Value("${batch.periodicDeleteIndexTask.executeIntervalSeconds:86400}")
	private int executeIntervalSeconds;

	@Value("${batch.periodicDeleteIndexTask.expireDays:30}")
	private int expireDays;

	@Autowired
	private ArticleService articleService;

	@Override
	protected boolean canExecute() {
		return execute;
	}

	@Override
	protected int getExecuteIntervalSeconds() {
		return executeIntervalSeconds;
	}

	@Override
	protected void execute() throws Exception {
		Date targetDate = YsdDateUtil.addDate(new Date(), -expireDays);
		articleService.deleteLessThanDate(targetDate);
		l.debug("article before " + YsdDateUtil.date(targetDate) + " are deleted.");
	}

}
