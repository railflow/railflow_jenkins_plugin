package io.jenkins.plugins.railflow.jenkins.cli;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import io.jenkins.plugins.railflow.ReportFilesProvider;

public class ReportFilesHolder {

    private List<String> reportFiles;

    public ReportFilesHolder(final ReportFilesProvider provider) {
        this.reportFiles = new ArrayList<>();
        try {
            for (final Path entry : provider.getReportFiles()) {
                reportFiles.add(entry.toAbsolutePath().toString());
            }
        } catch (final Exception e) {
        }
    }

    public List<String> getReportFiles() {
        return reportFiles;
    }

    public void setReportFiles(final List<String> reportFiles) {
        this.reportFiles = reportFiles;
    }
}
