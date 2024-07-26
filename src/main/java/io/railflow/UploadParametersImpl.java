package io.railflow;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import io.railflow.commons.SearchMode;
import io.railflow.commons.UploadMode;
import io.railflow.testreport.model.ReportFormat;

/**
 * Default implementation of {@link UploadParameters}.
 *
 * @author Sergey Oplavin
 */
public class UploadParametersImpl implements UploadParameters {
	private static final long serialVersionUID = 5491751009465501645L;
	private final String project;
	private final String testPath;
	private final ReportFormat reportFormat;
	private final SearchMode searchMode;
	private String testRun;
	private String testPlan;
	private String milestonePath;
	private Map<String, Object> caseFields;
	private Map<String, Object> resultFields;
	private List<String> configNames;
	private Map<String, Integer> caseNameToIdMap;
	private boolean closeRun;
	private boolean closePlan;
	private boolean disableGrouping;
	private String templateName;
	private String caseType;
	private String casePriority;
	private RunId runId;
	private List<String> smartTestFailureAssignment;
	private UploadMode uploadMode;
	private String caseSearchField;
	private boolean fullCaseNamesAllowed;
	private String tagsFieldName;

	public UploadParametersImpl(final String project, final String testPath, final ReportFormat reportFormat, final SearchMode searchMode) {
		this.project = Objects.requireNonNull(project, "Project is null");
		this.testPath = Objects.requireNonNull(testPath, "Test path is null");
		this.reportFormat = Objects.requireNonNull(reportFormat, "Report format is null");
		this.searchMode = Objects.requireNonNull(searchMode, "Search mode is null");
	}

	@Override
	public @NotNull String getProject() {
		return this.project;
	}

	@Override
	public @NotNull String getTestPath() {
		return this.testPath;
	}

	@Override
	public @NotNull ReportFormat getReportFormat() {
		return this.reportFormat;
	}

	@Override
	public SearchMode getSearchMode() {
		return this.searchMode;
	}

	@Override public Optional<String> getTestRun() {
		return Optional.ofNullable(this.testRun);
	}

	public void setTestRun(final String testRun) {
		this.testRun = testRun;
	}

	@Override
	public Optional<String> getTestPlan() {
		return Optional.ofNullable(this.testPlan);
	}

	public void setTestPlan(final String testPlan) {
		this.testPlan = testPlan;
	}

	@Override
	public Optional<String> getMilestonePath() {
		return Optional.ofNullable(this.milestonePath);
	}

	public void setMilestonePath(final String milestonePath) {
		this.milestonePath = milestonePath;
	}

	@Override
	public Optional<Map<String, Object>> getCaseFields() {
		return Optional.ofNullable(this.caseFields);
	}

	public void setCaseFields(final Map<String, Object> caseFields) {
		this.caseFields = caseFields;
	}

	@Override
	public Optional<Map<String, Object>> getResultFields() {
		return Optional.ofNullable(this.resultFields);
	}

	public void setResultFields(final Map<String, Object> resultFields) {
		this.resultFields = resultFields;
	}

	@Override
	public Optional<List<String>> getConfigNames() {
		return Optional.ofNullable(this.configNames);
	}

	public void setConfigNames(final List<String> configNames) {
		this.configNames = configNames;
	}

	@Override
	public boolean isCloseRun() {
		return this.closeRun;
	}

	public void setCloseRun(final boolean closeRun) {
		this.closeRun = closeRun;
	}

	@Override
	public boolean isClosePlan() {
		return this.closePlan;
	}

	public void setClosePlan(final boolean closePlan) {
		this.closePlan = closePlan;
	}

	@Override
	public Optional<Map<String
			, Integer>> getCaseNameToIdMap() {
		return Optional.ofNullable(this.caseNameToIdMap);
	}

	public void setCaseNameToIdMap(final Map<String, Integer> caseNameToIdMap) {
		this.caseNameToIdMap = caseNameToIdMap;
	}

	@Override
	public boolean isDisableGrouping() {
		return this.disableGrouping;
	}

	public void setDisableGrouping(final boolean disableGrouping) {
		this.disableGrouping = disableGrouping;
	}

	@Override
	public Optional<String> getTemplateName() {
		return Optional.ofNullable(this.templateName);
	}

	public void setTemplateName(final String templateName) {
		this.templateName = templateName;
	}

	@Override
	public Optional<String> getCaseType() {
		return Optional.ofNullable(this.caseType);
	}

	public void setCaseType(final String caseType) {
		this.caseType = caseType;
	}

	@Override
	public Optional<String> getCasePriority() {
		return Optional.ofNullable(this.casePriority);
	}

	public void setCasePriority(final String casePriority) {
		this.casePriority = casePriority;
	}

	@Override
	public Optional<RunId> getRunId() {
		return Optional.ofNullable(this.runId);
	}

	public void setRunId(final RunId runId) {
		this.runId = runId;
	}

	@Override
	public Optional<List<String>> getSmartTestFailureAssignment() {
		return Optional.ofNullable(this.smartTestFailureAssignment);
	}

	public void setSmartTestFailureAssignment(final List<String> smartTestFailureAssignment) {
		this.smartTestFailureAssignment = smartTestFailureAssignment;
	}

	@Override
	public Optional<UploadMode> getUploadMode() {
		return Optional.ofNullable(this.uploadMode);
	}

	public void setUploadMode(final UploadMode uploadMode) {
		this.uploadMode = uploadMode;
	}

	@Override
	public Optional<String> getCaseSearchField() {
		return Optional.ofNullable(this.caseSearchField);
	}

	public void setCaseSearchField(final String caseSearchField) {
		this.caseSearchField = caseSearchField;
	}

	@Override
	public boolean isFullCaseNamesAllowed() {
		return this.fullCaseNamesAllowed;
	}

	public void setFullCaseNamesAllowed(final boolean fullCaseNamesAllowed) {
		this.fullCaseNamesAllowed = fullCaseNamesAllowed;
	}

	@Override
	public Optional<String> getTagsFieldName() {
		return Optional.ofNullable(this.tagsFieldName);
	}

	public void setTagsFieldName(final String tagsFieldName) {
		this.tagsFieldName = tagsFieldName;
	}
}
