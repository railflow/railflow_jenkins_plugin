package io.railflow.jenkins.log;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Filter;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import org.slf4j.MDC;

import hudson.FilePath;
import hudson.model.TaskListener;

/**
 * <b>Important: This class does not implement Serializable, so you cannot pass
 * it from server to agent</b> A logger wrapper which must be declared before
 * running railflow.
 *
 * @author Sergey Oplavin
 */
public class RailflowLogger implements AutoCloseable {

	public static final String JENKINS_BUILD_ID = "jenkins_build_id";
	public static final String RAILFLOW_PACKAGE_NAME = "io.railflow";
	private final Handler handler;
	private FileHandler fileHandler = null;

	public RailflowLogger(final TaskListener taskListener, final String jobId, final FilePath workspace, final boolean debugLogEnabled)
			throws IOException, IllegalArgumentException {
		MDC.put(JENKINS_BUILD_ID, jobId);
		final Filter filter = (record -> jobId.equals(MDC.get(JENKINS_BUILD_ID)));
		final Formatter formatter = new Formatter() {
			@Override
			public String format(final LogRecord record) {
				return formatMessage(record);
			}
		};
		final Logger logger = Logger.getLogger(RAILFLOW_PACKAGE_NAME);
		logger.setLevel(Level.ALL);
		this.handler = new JenkinsStreamHandler(taskListener, formatter);
		this.handler.setFilter(filter);
		this.handler.setLevel(Level.INFO);
		if (debugLogEnabled) {
			this.fileHandler = new FileHandler(workspace.getRemote() + "/.railflow.log");
			this.fileHandler.setFilter(filter);
			this.fileHandler.setLevel(Level.ALL);
			this.fileHandler.setFormatter(new LogFileFormatter());
			logger.addHandler(this.fileHandler);
		}
		logger.addHandler(this.handler);

	}

	@Override
	public void close() throws Exception {
		MDC.clear();
		final Logger logger = Logger.getLogger(RAILFLOW_PACKAGE_NAME);
		logger.removeHandler(this.handler);
		if (this.fileHandler != null) {
			logger.removeHandler(this.fileHandler);
			this.fileHandler.flush();
			this.fileHandler.close();
		}
	}

	FileHandler getFileHandler() {
		return this.fileHandler;
	}

	private static class JenkinsStreamHandler extends Handler {
		private final TaskListener taskListener;
		private final Formatter formatter;

		public JenkinsStreamHandler(final TaskListener taskListener, final Formatter formatter) {
			this.taskListener = taskListener;
			this.formatter = formatter;
		}

		@Override
		public void publish(final LogRecord record) {
			if (!isLoggable(record)) {
				return;
			}
			this.taskListener.getLogger().println(this.formatter.format(record));
		}

		@Override
		public void flush() {
			this.taskListener.getLogger().flush();
		}

		@Override
		public void close() throws SecurityException {
			// we should not close logging stream.
		}
	}

}
