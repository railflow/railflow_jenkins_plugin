package io.railflow;

import java.nio.file.Path;
import java.util.List;

/**
 * Provides a list of report files.
 * 
 * @author Sergey Oplavin
 */
@FunctionalInterface
public interface ReportFilesProvider {
	List<Path> getReportFiles() throws Exception;
}
