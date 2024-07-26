package io.railflow;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import io.railflow.commons.SearchMode;
import io.railflow.commons.UploadMode;
import io.railflow.testreport.model.ReportFormat;

/**
 * Contains basic parameters required for any type of upload.
 *
 * @author Liu Yang
 * @author Sergey Oplavin
 */
public interface UploadParameters extends Serializable {

    /**
     * New builder.
     *
     * @param railFlowProject railflow project name.
     * @param testPath        test path, e.g. 'Master/Section'.
     * @param reportFormat    report format.
     * @return builder.
     */
	static Builder builder(final String railFlowProject, final String testPath, final ReportFormat reportFormat, final SearchMode searchMode) {
		return new Builder(railFlowProject, testPath, reportFormat, searchMode);
    }


    /**
     * Gets the project name.
     *
     * @return the project name
     */
    @NotNull
    String getProject();

    /**
     * Gets the test path.
     *
     * @return the test path
     */
    @NotNull
    String getTestPath();

    /**
     * Gets the testreport format.
     *
     * @return the testreport format
     */
    @NotNull
    ReportFormat getReportFormat();

	/**
	 * @return specifies the test case lookup algorithm.
	 */
	@NotNull
	SearchMode getSearchMode();

    /**
     * Gets the test run.
     *
     * @return the test run
     */
    Optional<String> getTestRun();

    /**
     * Gets the test plan.
     *
     * @return the test plan
     */
    Optional<String> getTestPlan();

    /**
     * Gets the milestone path.
     *
     * @return the milestone path
     */
    Optional<String> getMilestonePath();

    /**
     * Gets the case fields.
     *
     * @return the case fields
     */
	Optional<Map<String, Object>> getCaseFields();

    /**
     * Gets the result fields.
     *
     * @return the result fields
     */
	Optional<Map<String, Object>> getResultFields();

    /**
     * @return string list containing configuration names (paths)
     */
    Optional<List<String>> getConfigNames();

    /**
     * Checks if is close run.
     *
     * @return true, if is close run.
     */
    boolean isCloseRun();

    /**
     * Checks if is close plan.
     *
     * @return true, if is close plan.
     */
    boolean isClosePlan();

    /**
     * @return string containing mappings between test name and test case ID in
     * TestRail.
     */
    Optional<Map<String, Integer>> getCaseNameToIdMap();

    /**
     * @return flag indicates whether or not sub section creation (grouping) is
     * disabled. <code>true</code> - creation of subsections is disabled so
     * all test cases will be uploaded into one section defined by test path
     * parameter, <code>false</code> - creation of subsection is enabled, so
     * one or more subsection will be created.
     */
    boolean isDisableGrouping();

    /**
     * Gets the template name.
     *
     * @return the template name
     */
    Optional<String> getTemplateName();

    /**
     * Gets the case type.
     *
     * @return the case type
     */
    Optional<String> getCaseType();

    /**
     * Gets the case priority.
     *
     * @return the case priority
     */
    Optional<String> getCasePriority();

    /**
     * @return the ID of the TestRail Run to update or 0.
     */
	Optional<RunId> getRunId();

	/**
	 * @return specifies test cases upload mode.
	 */
	Optional<UploadMode> getUploadMode();

    /**
     * @return string list of user email addresses.
     */
    Optional<List<String>> getSmartTestFailureAssignment();

	/**
	 * Gets the name of the case filed in TestRail which will be using for
	 * searching for existing test cases instead of test case title.
	 *
	 * @return the name of a case field.
	 */
	Optional<String> getCaseSearchField();

	/**
	 * Checks if full case names allowed for test case names.
	 *
	 * @return true, if full case names allowed.
	 */
	boolean isFullCaseNamesAllowed();

	/**
	 * Gets the tags field name.
	 *
	 * @return the tags field name.
	 */
	Optional<String> getTagsFieldName();

    class Builder {

        private final UploadParametersImpl uploadParameters;

		private Builder(final String project, final String testPath, final ReportFormat reportFormat, final SearchMode searchMode) {
			this.uploadParameters = new UploadParametersImpl(project, testPath, reportFormat, searchMode);
        }

        public Builder testRun(final String testRun) {
            this.uploadParameters.setTestRun(testRun);
            return this;
        }

        public Builder testPlan(final String testPlan) {
            this.uploadParameters.setTestPlan(testPlan);
            return this;
        }

        public Builder milestonePath(final String milestonePath) {
            this.uploadParameters.setMilestonePath(milestonePath);
            return this;
        }

		public Builder caseFields(final Map<String, Object> caseFields) {
            this.uploadParameters.setCaseFields(caseFields);
            return this;
        }

		public Builder resultFields(final Map<String, Object> resultFields) {
            this.uploadParameters.setResultFields(resultFields);
            return this;
        }

        public Builder configNames(final List<String> configNames) {
            this.uploadParameters.setConfigNames(configNames);
            return this;
        }

        public Builder closeRun(final boolean closeRun) {
            this.uploadParameters.setCloseRun(closeRun);
            return this;
        }

        public Builder closePlan(final boolean closePlan) {
            this.uploadParameters.setClosePlan(closePlan);
            return this;
        }

        public Builder caseNameToIdMap(final Map<String, Integer> caseNameToIdMap) {
            this.uploadParameters.setCaseNameToIdMap(caseNameToIdMap);
            return this;
        }

        public Builder disableGrouping(final boolean disableGrouping) {
            this.uploadParameters.setDisableGrouping(disableGrouping);
            return this;
        }

        public Builder templateName(final String templateName) {
            this.uploadParameters.setTemplateName(templateName);
            return this;
        }

        public Builder caseType(final String caseType) {
            this.uploadParameters.setCaseType(caseType);
            return this;
        }

        public Builder casePriority(final String casePriority) {
            this.uploadParameters.setCasePriority(casePriority);
            return this;
        }

		public Builder runId(final RunId runId) {
            this.uploadParameters.setRunId(runId);
            return this;
        }

        public Builder smartTestFailureAssignment(final List<String> smartTestFailureAssignment) {
            this.uploadParameters.setSmartTestFailureAssignment(smartTestFailureAssignment);
            return this;
        }

		public Builder uploadMode(final UploadMode uploadMode) {
			this.uploadParameters.setUploadMode(uploadMode);
			return this;
		}

		public Builder caseSearchField(final String caseSearchField) {
			this.uploadParameters.setCaseSearchField(caseSearchField);
			return this;
		}

		public Builder fullCaseNames(final boolean fullCaseNamesAllowed) {
			this.uploadParameters.setFullCaseNamesAllowed(fullCaseNamesAllowed);
			return this;
		}

		public Builder tagsFieldName(final String tagsFieldName) {
			this.uploadParameters.setTagsFieldName(tagsFieldName);
			return this;
		}

        public UploadParametersImpl build() {
            return this.uploadParameters;
        }
    }
}
