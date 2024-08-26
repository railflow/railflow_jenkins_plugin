package io.jenkins.plugins.railflow.jenkins.testresults;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hudson.FilePath;
import io.jenkins.plugins.railflow.ReportFilesProvider;

/**
 * Default implementation of {@link ReportFilesProvider}.
 *
 * @author Sergey Oplavin
 */
public class ReportFilesProviderImpl implements ReportFilesProvider {
	private static final Logger LOGGER = LoggerFactory.getLogger(ReportFilesProviderImpl.class);
	private final String filePattern;
	private final FilePath workspace;

	ReportFilesProviderImpl(final String filePattern, final FilePath workspace) {
		this.filePattern = filePattern;
		this.workspace = workspace;
	}

	@Override
	public List<Path> getReportFiles() throws Exception {
		LOGGER.info("Searching for test report files matching pattern: {}, directory: {}", this.filePattern, this.workspace.getRemote());
		final List<Path> files = new ArrayList<>();

		final FilePath[] filePaths = this.workspace.list(this.filePattern);
		if (filePaths.length > 0) {
			for (final FilePath filePath : filePaths) {
				files.add(Paths.get(filePath.getRemote()));
			}
		}
		return files;
	}
}
