package io.jenkins.plugins.railflow.jenkins.testresults;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;

import com.google.common.collect.ImmutableMap;

import hudson.EnvVars;
import io.jenkins.plugins.railflow.UploadParameters;
import io.jenkins.plugins.railflow.commons.SearchMode;
import io.jenkins.plugins.railflow.testreport.model.ReportFormat;

/**
 * Tests for {@link UploadParametersFactoryImpl}.
 *
 * @author Sergey Oplavin
 */
public class UploadParametersFactoryImplTest {
	private final UploadParametersFactoryImpl factory = UploadParametersFactoryImpl.THE_INSTANCE;

	@Test
	public void create() {
		final String projName = "some ${VAR} test";
		final String testPath = "Master/${VAR}";
		final EnvVars envVars = new EnvVars("VAR", "var value");
		final RailflowUploadJobConfiguration jobConfig = new RailflowUploadJobConfiguration();
		final String testPlan = "plan ${VAR}";
		final String testRun = "run ${VAR}";
		final String casePriority = "case ${VAR}";
		final String caseType = "${VAR} type";
		final String milestonePath = "M${VAR}/M2";
		final String templateName = "template ${VAR}";
		jobConfig.setTestReportFormat(ReportFormat.JUNIT);
		jobConfig.setSearchMode(SearchMode.NAME);
		jobConfig.setTestCasePath(testPath);
		jobConfig.setTestPlanName(testPlan);
		jobConfig.setTestRunName(testRun);
		jobConfig.setTestCasePriority(casePriority);
		jobConfig.setTestCaseType(caseType);
		jobConfig.setCloseRun(true);
		jobConfig.setClosePlan(true);
		jobConfig.setDisableGrouping(true);
		jobConfig.setMilestonePath(milestonePath);
		jobConfig.setTestCaseTemplate(templateName);
		jobConfig.setConfigurationNames("configname1/${VAR}\n${VAR}/configname2");
		jobConfig.setTestCaseCustomFields("case1=${VAR}\n${VAR}=value");
		jobConfig.setTestResultCustomFields("result=${VAR}\n${VAR}=result");
		jobConfig.setSmartTestFailureAssignment("user1,user2");
		jobConfig.setFullCaseNamesAllowed(true);
		final UploadParameters parameters = this.factory.create(projName, jobConfig, envVars);

		assertEquals(ReportFormat.JUNIT, parameters.getReportFormat());
		assertEquals(SearchMode.NAME, parameters.getSearchMode());
		assertEquals(this.resolve(projName), parameters.getProject());
		assertEquals(this.resolve(testPath), parameters.getTestPath());
		assertEquals(this.resolve(testPlan), parameters.getTestPlan().get());
		assertEquals(this.resolve(testRun), parameters.getTestRun().get());
		assertEquals(this.resolve(casePriority), parameters.getCasePriority().get());
		assertEquals(this.resolve(caseType), parameters.getCaseType().get());
		assertTrue(parameters.isCloseRun());
		assertTrue(parameters.isClosePlan());
		assertTrue(parameters.isDisableGrouping());
		assertEquals(this.resolve(milestonePath), parameters.getMilestonePath().get());
		assertEquals(this.resolve(templateName), parameters.getTemplateName().get());
		assertFalse(parameters.getCaseNameToIdMap().isPresent());
		assertEquals(Arrays.asList("configname1/var value", "var value/configname2"), parameters.getConfigNames().get());
		assertEquals(ImmutableMap.of("case1", "var value", "var value", "value"), parameters.getCaseFields().get());
		assertEquals(ImmutableMap.of("result", "var value", "var value", "result"), parameters.getResultFields().get());
		assertEquals(Arrays.asList("user1", "user2"), parameters.getSmartTestFailureAssignment().get());
		assertTrue(parameters.isFullCaseNamesAllowed());
	}

	private String resolve(final String value) {
		return value.replaceAll("\\$\\{VAR\\}", "var value");
	}
}
