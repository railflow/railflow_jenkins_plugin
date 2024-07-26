package io.railflow.jenkins.log;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

import org.apache.commons.lang3.exception.ExceptionUtils;

/**
 * Log formatter for log file.
 *
 * @author Sergey Oplavin
 */
public class LogFileFormatter extends Formatter {

	private static final ThreadLocal<MessageFormat> LOG_FORMAT = ThreadLocal.withInitial(
			() -> new MessageFormat("{0,time,dd-MMM-yyyy HH:mm:ss.SSS} [{1}] {2} {3}" + System.lineSeparator(), Locale.ENGLISH));

	@Override
	public String format(final LogRecord record) {
		final StringBuilder builder = new StringBuilder(
				LOG_FORMAT.get().format(new Object[] { record.getMillis(), record.getLevel(), record.getSourceClassName(), this
						.formatMessage(record) }));
		final Throwable thrown = record.getThrown();
		if (thrown != null) {
			builder.append(ExceptionUtils.getStackTrace(thrown)).append(System.lineSeparator());
		}
		return builder.toString();
	}
}
