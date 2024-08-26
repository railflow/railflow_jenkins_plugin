package io.jenkins.plugins.railflow.jenkins.testresults;

import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;

import hudson.Util;
import io.jenkins.plugins.railflow.commons.SearchMode;
import io.jenkins.plugins.railflow.commons.UploadMode;
import io.jenkins.plugins.railflow.testreport.model.ReportFormat;

/**
 * Holds the upload configuration in the job.
 *
 * @author Sergey Oplavin
 */
public class RailflowUploadJobConfiguration {
	private String testCasePath;
	private ReportFormat testReportFormat;
	private SearchMode searchMode;
	private String resultFilePattern;
	private String testCaseCustomFields;
	private String testRunName;
	private String testPlanName;
	private String milestonePath;
	private String testResultCustomFields;
	private String smartTestFailureAssignment;
	private String configurationNames;
	private boolean disableGrouping;
	private boolean closeRun;
	private boolean closePlan;
	private String testCaseType;
	private String testCasePriority;
	private String testCaseTemplate;
	private UploadMode uploadMode;
	private String caseSearchField;
	private boolean fullCaseNamesAllowed;
	private String tagsFieldName;
	private String runId;

	@DataBoundConstructor
	public RailflowUploadJobConfiguration() {
	}

	public String getTestCasePath() {
		return this.testCasePath;
	}

	@DataBoundSetter
	public void setTestCasePath(final String testCasePath) {
		this.testCasePath = Util.fixEmptyAndTrim(testCasePath);
	}

	public ReportFormat getTestReportFormat() {
		return this.testReportFormat;
	}

	@DataBoundSetter
	public void setTestReportFormat(final ReportFormat testReportFormat) {
		this.testReportFormat = testReportFormat;
	}

	public SearchMode getSearchMode() {
		return this.searchMode;
	}

	@DataBoundSetter
	public void setSearchMode(final SearchMode searchMode) {
		this.searchMode = searchMode;
	}

	public String getResultFilePattern() {
		return this.resultFilePattern;
	}

	@DataBoundSetter
	public void setResultFilePattern(final String resultFilePattern) {
		this.resultFilePattern = Util.fixEmptyAndTrim(resultFilePattern);
	}

	public String getTestCaseCustomFields() {
		return this.testCaseCustomFields;
	}

	@DataBoundSetter
	public void setTestCaseCustomFields(final String testCaseCustomFields) {
		this.testCaseCustomFields = Util.fixEmptyAndTrim(testCaseCustomFields);
	}

	public String getTestRunName() {
		return this.testRunName;
	}

	@DataBoundSetter
	public void setTestRunName(final String testRunName) {
		this.testRunName = Util.fixEmptyAndTrim(testRunName);
	}

	public String getTestPlanName() {
		return this.testPlanName;
	}

	@DataBoundSetter
	public void setTestPlanName(final String testPlanName) {
		this.testPlanName = Util.fixEmptyAndTrim(testPlanName);
	}

	public String getMilestonePath() {
		return this.milestonePath;
	}

	@DataBoundSetter
	public void setMilestonePath(final String milestonePath) {
		this.milestonePath = Util.fixEmptyAndTrim(milestonePath);
	}

	public String getTestResultCustomFields() {
		return this.testResultCustomFields;
	}

	@DataBoundSetter
	public void setTestResultCustomFields(final String testResultCustomFields) {
		this.testResultCustomFields = Util.fixEmptyAndTrim(testResultCustomFields);
	}

	public String getSmartTestFailureAssignment() {
		return this.smartTestFailureAssignment;
	}

	@DataBoundSetter
	public void setSmartTestFailureAssignment(final String smartTestFailureAssignment) {
		this.smartTestFailureAssignment = Util.fixEmptyAndTrim(smartTestFailureAssignment);
	}

	public String getConfigurationNames() {
		return this.configurationNames;
	}

	@DataBoundSetter
	public void setConfigurationNames(final String configurationNames) {
		this.configurationNames = Util.fixEmptyAndTrim(configurationNames);
	}

	public boolean isDisableGrouping() {
		return this.disableGrouping;
	}

	@DataBoundSetter
	public void setDisableGrouping(final boolean disableGrouping) {
		this.disableGrouping = disableGrouping;
	}

	public boolean isCloseRun() {
		return this.closeRun;
	}

	@DataBoundSetter
	public void setCloseRun(final boolean closeRun) {
		this.closeRun = closeRun;
	}

	public boolean isClosePlan() {
		return this.closePlan;
	}

	@DataBoundSetter
	public void setClosePlan(final boolean closePlan) {
		this.closePlan = closePlan;
	}

	public String getTestCaseType() {
		return this.testCaseType;
	}

	@DataBoundSetter
	public void setTestCaseType(final String testCaseType) {
		this.testCaseType = Util.fixEmptyAndTrim(testCaseType);
	}

	public String getTestCasePriority() {
		return this.testCasePriority;
	}

	@DataBoundSetter
	public void setTestCasePriority(final String testCasePriority) {
		this.testCasePriority = Util.fixEmptyAndTrim(testCasePriority);
	}

	public String getTestCaseTemplate() {
		return this.testCaseTemplate;
	}

	@DataBoundSetter
	public void setTestCaseTemplate(final String testCaseTemplate) {
		this.testCaseTemplate = Util.fixEmptyAndTrim(testCaseTemplate);
	}

	public UploadMode getUploadMode() {
		return this.uploadMode;
	}

	@DataBoundSetter
	public void setUploadMode(final UploadMode uploadMode) {
		this.uploadMode = uploadMode;
	}

	public String getCaseSearchField() {
		return this.caseSearchField;
	}

	@DataBoundSetter
	public void setCaseSearchField(final String caseSearchField) {
		this.caseSearchField = caseSearchField;
	}
	public boolean isFullCaseNamesAllowed() {
		return this.fullCaseNamesAllowed;
	}

	@DataBoundSetter
	public void setFullCaseNamesAllowed(final boolean fullCaseNamesAllowed) {
		this.fullCaseNamesAllowed = fullCaseNamesAllowed;
	}

	public String getTagsFieldName() {
		return this.tagsFieldName;
	}

	@DataBoundSetter
	public void setTagsFieldName(final String tagsFieldName) {
		this.tagsFieldName = tagsFieldName;
	}

	public String getRunId() {
		return this.runId;
	}

	@DataBoundSetter
	public void setRunId(final String runId) {
		this.runId = runId;
	}
}
