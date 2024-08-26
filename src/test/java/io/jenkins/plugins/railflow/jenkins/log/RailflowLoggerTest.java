package io.jenkins.plugins.railflow.jenkins.log;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.logging.FileHandler;

import javax.annotation.Nonnull;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableMap;

import hudson.FilePath;
import hudson.model.TaskListener;

/**
 * Tests for {@link RailflowLogger}
 *
 * @author Sergey Oplavin
 */
public class RailflowLoggerTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(RailflowLoggerTest.class);

	final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

	FilePath workspace;

	@Before
	public void setup() {
		this.workspace = new FilePath(this.temporaryFolder.getRoot());
	}

	@Test
	public void test_single_thread() throws Exception {
		final String id = "42";
		try (final RailflowLogger logger = new RailflowLogger(new TestTaskListener(new PrintStream(this.outputStream)), id,
				this.workspace, true)) {
			LOGGER.info("message one");
			LOGGER.debug("debug one");
			LOGGER.info("message two");
			LOGGER.debug("debug two");
		}
		assertEquals("message one" + System.lineSeparator() + "message two" + System.lineSeparator(),
				this.outputStream.toString(StandardCharsets.UTF_8.toString()));
		final File logFile = new File(this.workspace.getRemote(), ".railflow.log");
		assertTrue(logFile.exists());
		final String logContent = FileUtils.readFileToString(logFile, StandardCharsets.UTF_8);
		assertTrue(logContent.contains("message one"));
		assertTrue(logContent.contains("debug one"));
		assertTrue(logContent.contains("message two"));
		assertTrue(logContent.contains("debug two"));
	}

	@Test
	public void test_concurrent() throws InterruptedException {
		final Map<String, ByteArrayOutputStream> jobs = ImmutableMap.of("12", new ByteArrayOutputStream(), "23",
				new ByteArrayOutputStream(), "34", new ByteArrayOutputStream());
		final CountDownLatch latch = new CountDownLatch(jobs.size());

		for (final Map.Entry<String, ByteArrayOutputStream> entry : jobs.entrySet()) {
			final Thread thread = this.createThread(entry.getKey(), entry.getValue(), latch);
			thread.setUncaughtExceptionHandler((t, e) -> {
				throw new RuntimeException(e);
			});
			thread.start();
		}
		latch.await(2, TimeUnit.MINUTES);
		jobs.forEach((k, v) -> this.assertLog(k, v));
	}

	@Test
	public void test_file_handler_log_level() throws Exception {
		final String id = "42";
		assertLogLevel(true, id);
		assertLogLevel(false, id);
	}

	private Thread createThread(final String jobId, final ByteArrayOutputStream out, final CountDownLatch latch) {
		return new Thread(() -> {
			try {
				final File workspaceDir = this.temporaryFolder.newFolder(jobId);
				final FilePath workspace = new FilePath(workspaceDir);
				try (final RailflowLogger logger = new RailflowLogger(new TestTaskListener(new PrintStream(out)), jobId,
						workspace, true)) {
					LOGGER.info(jobId + " info");
					LOGGER.debug(jobId + " debug");
					Thread.sleep(100);
					LOGGER.info(jobId + " info two");
					LOGGER.debug(jobId + " debug two");
				} catch (final Exception e) {
					throw new RuntimeException(e);
				}
			} catch (final Exception ex) {
				throw new RuntimeException(ex);
			} finally {
				latch.countDown();
			}
		});
	}

	private void assertLog(final String jobId, final ByteArrayOutputStream out) {
		try {
			final String consoleOut = out.toString(StandardCharsets.UTF_8.toString());
			assertEquals(jobId + " info" + System.lineSeparator() + jobId + " info two" + System.lineSeparator(),
					consoleOut);
			final File logFile = Paths.get(this.temporaryFolder.getRoot().getAbsolutePath(), jobId, ".railflow.log")
					.toFile();
			assertTrue(logFile.exists());
			final String logContent = FileUtils.readFileToString(logFile, StandardCharsets.UTF_8);
			System.out.println(logContent);
			assertEquals(4, logContent.split(System.lineSeparator()).length);
		} catch (final Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	private void assertLogLevel(final boolean isDebugLogEnabled, final String id)
			throws Exception {
		final RailflowLogger logger = new RailflowLogger(new TestTaskListener(new PrintStream(this.outputStream)), id,
				this.workspace, isDebugLogEnabled);
		final FileHandler fileHandler = logger.getFileHandler();
		if (isDebugLogEnabled) {
			assertNotNull(fileHandler);
			assertEquals(java.util.logging.Level.ALL, fileHandler.getLevel());
		}
	}

	private static class TestTaskListener implements TaskListener {
		private static final long serialVersionUID = 487023486073513890L;
		private final PrintStream out;

		public TestTaskListener(final PrintStream out) {
			this.out = out;
		}

		@Nonnull
		@Override
		public PrintStream getLogger() {
			return this.out;
		}
	}

}
