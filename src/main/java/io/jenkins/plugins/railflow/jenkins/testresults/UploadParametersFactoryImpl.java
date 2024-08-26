package io.jenkins.plugins.railflow.jenkins.testresults;

import hudson.EnvVars;
import io.jenkins.plugins.railflow.RunId;
import io.jenkins.plugins.railflow.UploadParameters;
import io.jenkins.plugins.railflow.jenkins.util.RailflowUtils;

/**
 * Default implementation of {@link UploadParametersFactory}.
 *
 * @author Sergey Oplavin
 */
public enum UploadParametersFactoryImpl implements UploadParametersFactory {

	THE_INSTANCE;

	@Override
	public UploadParameters create(final String projectName, final RailflowUploadJobConfiguration jobConfiguration, final EnvVars envVars) {
		return UploadParameters
				.builder(expand(envVars, projectName), expand(envVars, jobConfiguration.getTestCasePath()), jobConfiguration.getTestReportFormat(),
						jobConfiguration.getSearchMode())
				.testPlan(expand(envVars, jobConfiguration.getTestPlanName()))
				.testRun(expand(envVars, jobConfiguration.getTestRunName()))
				.caseFields(RailflowUtils.getCustomFields(expand(envVars, jobConfiguration.getTestCaseCustomFields())))
				.resultFields(RailflowUtils.getCustomFields(expand(envVars, jobConfiguration.getTestResultCustomFields())))
				.smartTestFailureAssignment(RailflowUtils.getAssignments(expand(envVars, jobConfiguration.getSmartTestFailureAssignment())))
				.caseType(expand(envVars, jobConfiguration.getTestCaseType()))
				.casePriority(expand(envVars, jobConfiguration.getTestCasePriority()))
				.closeRun(jobConfiguration.isCloseRun())
				.closePlan(jobConfiguration.isClosePlan())
				.disableGrouping(jobConfiguration.isDisableGrouping())
				.configNames(RailflowUtils.getLines(expand(envVars, jobConfiguration.getConfigurationNames())))
				.milestonePath(expand(envVars, jobConfiguration.getMilestonePath()))
				.templateName(expand(envVars, jobConfiguration.getTestCaseTemplate()))
				.uploadMode(jobConfiguration.getUploadMode())
				.caseSearchField(jobConfiguration.getCaseSearchField())
				.fullCaseNames(jobConfiguration.isFullCaseNamesAllowed())
				.tagsFieldName(jobConfiguration.getTagsFieldName())
				.runId(RunId.create(jobConfiguration.getRunId()))
				.build();
	}

	private String expand(final EnvVars envVars, final String value) {
		return envVars.expand(value);
	}

}
