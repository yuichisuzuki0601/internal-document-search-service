package jp.co.ysd.internal_document_search_service.batch;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 *
 * @author yuichi
 *
 */
@Component
public class BatchTaskManager implements InitializingBean {

	private final Logger l = LoggerFactory.getLogger(getClass());

	@Value("${batch.enable:false}")
	private boolean enable;

	@Autowired
	private List<BatchTask> tasks;

	@Override
	public void afterPropertiesSet() throws Exception {
		prepare();
	}

	public void prepare() {
		if (enable) {
			for (BatchTask task : tasks) {
				task.schedule();
			}
		} else {
			l.info("batch tasks are not registered.");
		}
	}

}