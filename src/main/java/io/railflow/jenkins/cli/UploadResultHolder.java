package io.railflow.jenkins.cli;

import java.util.ArrayList;
import java.util.List;

import io.railflow.testrail.ResultsUploader.UploadResult;
import io.railflow.testrail.client.model.Run;

public class UploadResultHolder {

    private List<RunHolder> runs;
    private int numberOfResultsExported;

    public UploadResultHolder() {
    }

    public List<RunHolder> getRuns() {
        return runs;
    }

    public void setRuns(final List<RunHolder> runs) {
        this.runs = runs;
    }

    public int getNumberOfResultsExported() {
        return numberOfResultsExported;
    }

    public void setNumberOfResultsExported(final int numberOfResultsExported) {
        this.numberOfResultsExported = numberOfResultsExported;
    }

    public UploadResult map() {
        final List<Run> runList = new ArrayList<>();
        for (final RunHolder run : runs) {
            runList.add(run.map());
        }

        return new UploadResult(runList, numberOfResultsExported);
    }
}
