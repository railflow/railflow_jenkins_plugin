package io.railflow.jenkins.testresults;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import hudson.FilePath;

/**
 * Tests for {@link ReportFilesProviderImpl}.
 *
 * @author Sergey Oplavin
 */
public class ReportFilesProviderImplTest {

	@Rule
	public TemporaryFolder tempFolder = new TemporaryFolder();

	@Test
	public void getReportFilesPassTest() throws Exception {
		tempFolder.newFile("report_file_1.xml");
		tempFolder.newFile("report_file_2.xml");
		tempFolder.newFile("report_file_3.xml");
		File folder = tempFolder.newFolder();
		new File(folder, "report_file_4.xml").createNewFile();
		final FilePath filePath = new FilePath(tempFolder.newFile().getParentFile());
		final ReportFilesProviderImpl reportFilesProvider = new ReportFilesProviderImpl("**/*.xml", filePath);
		assertEquals(4, reportFilesProvider.getReportFiles().size());
	}

	@Test
	public void getReportFilesFailTest() throws Exception {
		tempFolder.newFile("report_file_1.xml");
		tempFolder.newFile("report_file_2.xml");
		tempFolder.newFile("report_file_3.xml");
		final FilePath filePath = new FilePath(tempFolder.newFile().getParentFile());
		final ReportFilesProviderImpl reportFilesProvider = new ReportFilesProviderImpl("*.txt", filePath);
		assertEquals(0, reportFilesProvider.getReportFiles().size());
	}

}