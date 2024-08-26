package io.jenkins.plugins.railflow.testrail;

import java.util.List;

import io.jenkins.plugins.railflow.testrail.client.model.Run;

/**
 * Interface for results uploader.
 *
 * @author Sergey Oplavin
 * @author Liu Yang
 */
public interface ResultsUploader {

	/**
	 * Upload result.
	 *
	 * @author Sergey Oplavin
	 *
	 */
	class UploadResult {
		private final List<Run> runs;
		private final int numberOfResultsExported;

		public UploadResult(final List<Run> runs, final int numberOfResultsExported) {
			super();
			this.runs = runs;
			this.numberOfResultsExported = numberOfResultsExported;
		}

		public List<Run> getRuns() {
			return this.runs;
		}

		public int getNumberOfResultsExported() {
			return this.numberOfResultsExported;
		}

	}

}