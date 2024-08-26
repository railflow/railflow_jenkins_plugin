package io.jenkins.plugins.railflow.jenkins.log;

import static org.junit.Assert.assertEquals;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.junit.Test;

/**
 * Tests for {@link LogFileFormatter}.
 *
 * @author Sergey Oplavin
 */
public class LogFileFormatterTest {

	private final LogFileFormatter formatter = new LogFileFormatter();
	private final MessageFormat messageFormat = new MessageFormat("{0,time,dd-MMM-yyyy HH:mm:ss.SSS} [{1}] {2} {3}" + System.lineSeparator(), Locale.ENGLISH);

	@Test
	public void format_noError() {
		long time = System.currentTimeMillis();
		String errorMessage = "Message";
		String className = "some.class";
		LogRecord logRecord = new LogRecord(Level.ALL, errorMessage);
		logRecord.setMillis(time);
		logRecord.setSourceClassName(className);

		assertEquals(messageFormat.format(new Object[] { time, "ALL", className, errorMessage }),
				formatter.format(logRecord));
	}

	@Test
	public void format_error() {
		long time = System.currentTimeMillis();
		String errorMessage = "Message";
		String className = "some.class";
		LogRecord logRecord = new LogRecord(Level.ALL, errorMessage);
		logRecord.setMillis(time);
		logRecord.setSourceClassName(className);
		IllegalArgumentException exception = new IllegalArgumentException("error");
		logRecord.setThrown(exception);
		assertEquals(
				messageFormat.format(new Object[] { time, "ALL", className, errorMessage }) + ExceptionUtils.getStackTrace(exception) + System.lineSeparator(),
				formatter.format(logRecord));
	}

}