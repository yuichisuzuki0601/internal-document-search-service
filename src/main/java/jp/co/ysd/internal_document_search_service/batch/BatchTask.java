package jp.co.ysd.internal_document_search_service.batch;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author yuichi
 *
 */
public abstract class BatchTask extends TimerTask implements Cloneable {

	protected final Logger l = LoggerFactory.getLogger(getClass());

	private Timer timer;

	protected abstract boolean canExecute();

	protected abstract int getExecuteIntervalSeconds();

	protected abstract void execute() throws Exception;

	public void schedule() {
		if (timer != null) {
			timer.cancel();
			timer.purge();
			this.timer = null;
		}
		String taskName = getClass().getSimpleName();
		if (canExecute()) {
			l.info(taskName + " are registered!!");
			this.timer = new Timer();
			try {
				timer.schedule((TimerTask) this.clone(), 0, TimeUnit.SECONDS.toMillis(getExecuteIntervalSeconds()));
			} catch (CloneNotSupportedException e) {
				throw new InternalError(e.getMessage(), e);
			}
		} else {
			l.info(taskName + " are not registered.");
		}
	}

	@Override
	public synchronized void run() {
		String taskName = getClass().getSimpleName();
		try {
			l.debug(taskName + " started!!");
			execute();
			l.debug(taskName + " finished!!");
		} catch (Exception e) {
			l.error("an error has occurred.", e);
		}
	}

}
