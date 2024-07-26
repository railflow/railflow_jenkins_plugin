package io.railflow.jenkins.testresults;

import hudson.AbortException;
import hudson.EnvVars;
import hudson.FilePath;
import hudson.Launcher;
import hudson.model.Run;
import hudson.model.TaskListener;
import hudson.remoting.VirtualChannel;
import hudson.util.FormValidation;
import hudson.util.Secret;
import io.railflow.JiraParameters;
import io.railflow.TestRailParameters;
import io.railflow.TestRailParametersImpl;
import io.railflow.UploadParameters;
import io.railflow.UploadParametersImpl;
import io.railflow.commons.SearchMode;
import io.railflow.commons.statistics.ServerType;
import io.railflow.jenkins.Messages;
import io.railflow.jenkins.cli.RailflowJenkinsCli;
import io.railflow.jenkins.admin.GlobalConfig;
import io.railflow.jenkins.admin.GlobalConfigFactory;
import io.railflow.jenkins.admin.GlobalConfigValidator;
import io.railflow.jenkins.util.PasswordEncrypter;
import io.railflow.testreport.model.ReportFormat;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import jenkins.model.Jenkins;
import net.sf.json.JSONObject;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.kohsuke.stapler.StaplerRequest;
import org.mockito.InOrder;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link RailflowPublisher}.
 *
 * @author Sergey Oplavin
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ Jenkins.class, Secret.class, RailflowJenkinsCli.class })
public class RailflowPublisherTest {

	public static final String PROJECT_NAME = "project";
	public static final String SERVER_NAME = "server";
	public static final String URL = "url";
	public static final String USER = "user";
	public static final String PASSWORD = "pwd";
	private static final String RUN_ID = "42";
	private final GlobalConfigFactory configFactory = mock(GlobalConfigFactory.class);
	private final GlobalConfig globalConfig = mock(GlobalConfig.class);
	private final Jenkins jenkins = mock(Jenkins.class);
	private final GlobalConfigValidator globalConfigValidator = mock(GlobalConfigValidator.class);
	private final TestRailParametersFactory trParamFactory = mock(TestRailParametersFactory.class);
	private final UploadParametersFactory uploadParamFactory = mock(UploadParametersFactory.class);
	private final RailflowCallableFactory railflowCallableFactory = mock(RailflowCallableFactory.class);
	private final PasswordEncrypter passwordEncrypter = mock(PasswordEncrypter.class);
	private final Secret licenseSecret = PowerMockito.mock(Secret.class);
	private final String licenseContent = "licenseContent";
	private final Run<?, ?> run = mock(Run.class);
	private final TaskListener listener = mock(TaskListener.class);
	private final ByteArrayOutputStream out = new ByteArrayOutputStream();
	@Rule
	public TemporaryFolder temporaryFolder = new TemporaryFolder();

	@Before
	public void beforeTest() throws Exception {
		PowerMockito.mockStatic(Jenkins.class);
		PowerMockito.when(Jenkins.get()).thenReturn(this.jenkins);
		when(this.configFactory.create()).thenReturn(this.globalConfig);
		when(this.globalConfig.getLicenseContent()).thenReturn(this.licenseSecret);
		when(this.passwordEncrypter.decrypt(any())).thenReturn(this.licenseContent);
		when(this.listener.getLogger()).thenReturn(new PrintStream(this.out));
		when(this.run.getEnvironment(any())).thenReturn(new EnvVars("VAR", "variable"));
		when(this.run.getId()).thenReturn(RUN_ID);

		PowerMockito.mockStatic(RailflowJenkinsCli.class);
	}

	@Test
	public void perform_all_valid() throws Exception {
		final ParamsHolder holder1 = new ParamsHolder("${VAR}*.xml", Arrays.asList("url1", "url2"));
		final ParamsHolder holder2 = new ParamsHolder("*.json", Arrays.asList("url3", "url4"));
		this.callPerformAndVerify(Arrays.asList(holder1, holder2), true, null, null);
	}

	@Test
	public void perform_with_collectExportEvent() throws Exception {
		final VirtualChannel channel = mock(VirtualChannel.class);
		when(channel.call(any())).thenReturn(Arrays.asList("url1", "url2"));
		perform(false, true, false, channel);

		PowerMockito.verifyStatic(RailflowJenkinsCli.class, times(1));
		RailflowJenkinsCli.collectExportEvent(eq(ServerType.Jenkins), anyBoolean(), any(), any(TestRailParameters.class), any(UploadParameters.class),
				any());
	}

	@Test
	public void perform_without_collectExportEvent() throws Exception {
		final VirtualChannel channel = mock(VirtualChannel.class);
		when(channel.call(any())).thenReturn(Arrays.asList("url1", "url2"));
		perform(false, false, false, channel);

		PowerMockito.verifyStatic(RailflowJenkinsCli.class, times(0));
		RailflowJenkinsCli.collectExportEvent(eq(ServerType.Jenkins), anyBoolean(), any(), any(TestRailParameters.class), any(UploadParameters.class),
				any(JiraParameters.class));
	}

	@Test
	public void perform_trial() throws Exception {
		final VirtualChannel channel = mock(VirtualChannel.class);
		when(channel.call(any())).thenReturn(Arrays.asList("url1", "url2"));
		perform(false, false, true, channel);

		PowerMockito.verifyStatic(RailflowJenkinsCli.class, times(1));
		RailflowJenkinsCli.collectExportEvent(eq(ServerType.Jenkins), anyBoolean(), any(), any(TestRailParameters.class), any(UploadParameters.class),
				any());
	}

	@Test
	public void perform_failed_with_collectExportEvent() throws Exception {
		final VirtualChannel channel = mock(VirtualChannel.class);
		when(channel.call(any())).thenThrow(new Exception());
		perform(true, true, false, channel);

		PowerMockito.verifyStatic(RailflowJenkinsCli.class, times(1));
		RailflowJenkinsCli.collectExportEvent(eq(ServerType.Jenkins), anyString(), anyString(), anyBoolean(), any(), any(TestRailParameters.class),
				any(UploadParameters.class), any(), any(Throwable.class));
	}

	@Test
	public void perform_failed_without_collectExportEvent() throws Exception {
		final VirtualChannel channel = mock(VirtualChannel.class);
		when(channel.call(any())).thenThrow(new Exception());
		perform(true, false, false, channel);

		PowerMockito.verifyStatic(RailflowJenkinsCli.class, times(0));
		RailflowJenkinsCli.collectExportEvent(eq(ServerType.Jenkins), anyString(), anyString(), anyBoolean(), any(), any(TestRailParameters.class),
				any(UploadParameters.class), any(JiraParameters.class), any(Throwable.class));
	}

	@Test
	public void perform_trail_failed() throws Exception {
		final VirtualChannel channel = mock(VirtualChannel.class);
		when(channel.call(any())).thenThrow(new Exception());
		perform(true, false, true, channel);

		PowerMockito.verifyStatic(RailflowJenkinsCli.class, times(1));
		RailflowJenkinsCli.collectExportEvent(eq(ServerType.Jenkins), anyString(), anyString(), anyBoolean(), any(), any(TestRailParameters.class),
				any(UploadParameters.class), any(), any(Throwable.class));
	}

	@Test
	public void perform_all_valid_overridden_credentials() throws Exception {
		final ParamsHolder holder1 = new ParamsHolder("${VAR}*.xml", Arrays.asList("url1", "url2"));
		final ParamsHolder holder2 = new ParamsHolder("*.json", Arrays.asList("url3", "url4"));
		final Secret overriddenPwd = PowerMockito.mock(Secret.class);
		when(overriddenPwd.getPlainText()).thenReturn("overridden pwd");
		this.callPerformAndVerify(Arrays.asList(holder1, holder2), true, "overridden", overriddenPwd);
	}

	@Test
	public void perform_first_upload_config_failed_fail_flag_false() throws Exception {
		final String error = "oooops";
		final ParamsHolder holder1 = new ParamsHolder("*.xml", Arrays.asList("url1", "url2"));
		holder1.error = error;
		final ParamsHolder holder2 = new ParamsHolder("*.json", Arrays.asList("url3", "url4"));
		this.callPerformAndVerify(Arrays.asList(holder1, holder2), false, null, null);
		final String log = this.out.toString();
		assertTrue(log.contains(Messages.uploadErrorMessage() + error + System.lineSeparator()
				+ "java.lang.RuntimeException: " + error));
		assertTrue(log.contains(Messages.logFailIfUploadFalse()));
	}

	@Test
	public void perform_first_upload_config_failed_fail_flag_true() {
		final String error = "oooops";
		final ParamsHolder holder1 = new ParamsHolder("*.xml", Arrays.asList("url1", "url2"));
		holder1.error = error;
		final ParamsHolder holder2 = new ParamsHolder("*.json", Arrays.asList("url3", "url4"));
		final AbortException exception = assertThrows(AbortException.class,
				() -> this.callPerformAndVerify(Arrays.asList(holder1, holder2), true, null, null));
		assertEquals(Messages.logFailIfUploadTrue(), exception.getMessage());
		final String log = this.out.toString();
		assertTrue(log.contains(Messages.uploadErrorMessage() + error + System.lineSeparator()
				+ "java.lang.RuntimeException: " + error));
	}

	@Test
	public void perform_empty_job_configurations() throws Exception {
		final RailflowPublisher publisher = this.publisher(null, null, true, Collections.emptyList());
		final FilePath workspace = new FilePath(this.temporaryFolder.getRoot());
		final Launcher launcher = mock(Launcher.class);
		publisher.perform(this.run, workspace, launcher, this.listener);
		final String log = this.out.toString();
		assertTrue(log.endsWith(Messages.noUploadConfigs() + System.lineSeparator()));
	}

	@Test
	public void perform_null_virt_channel_error() {
		final RailflowPublisher publisher = this.publisher(null, null, true,
				Collections.singletonList(this.uploadJobConfiguration("*.xml")));
		final FilePath workspace = new FilePath(this.temporaryFolder.getRoot());
		final Launcher launcher = mock(Launcher.class);
		final AbortException abortException = assertThrows(AbortException.class, () -> publisher.perform(this.run, workspace, launcher, this.listener));
		assertEquals(Messages.virtualChannelIsNull(), abortException.getMessage());
	}

	@Test
	public void test_getters_setters() {
		final List<RailflowUploadJobConfiguration> configs = Arrays.asList(this.uploadJobConfiguration("*.xml"), this.uploadJobConfiguration("*.json"));
		final RailflowPublisher publisher = this.publisher(null, null, true, configs);
		this.assertPublisher(publisher, SERVER_NAME, PROJECT_NAME, configs);
		assertEquals(SERVER_NAME, publisher.getTestRailServerName());
		assertEquals(PROJECT_NAME, publisher.getTestRailProjectName());
		assertEquals(configs, publisher.getJobConfigurations());

		final String server1 = "server1";
		publisher.setTestRailServerName(server1);
		final String project1 = "project1";
		publisher.setTestRailProjectName(project1);
		final List<RailflowUploadJobConfiguration> configs1 = Arrays.asList(this.uploadJobConfiguration("*.txt"), this.uploadJobConfiguration("*.trx"));
		publisher.setJobConfigurations(configs1);
		this.assertPublisher(publisher, server1, project1, configs1);
	}

	@Test
	public void descriptor_newInstance() {
		final RailflowPublisher.DescriptorImpl descriptor = new RailflowPublisher.DescriptorImpl();
		final StaplerRequest request = mock(StaplerRequest.class);
		final JSONObject jsonObject = new JSONObject();
		descriptor.newInstance(request, jsonObject);
		verify(request).bindJSON(RailflowPublisher.class, jsonObject);
		verifyNoMoreInteractions(request);
	}

	@Test
	public void descriptor_newInstance_req_null_error() {
		final IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
				() -> new RailflowPublisher.DescriptorImpl().newInstance(null, null));
		assertEquals("Stapler request is null", exception.getMessage());
	}

	@Test
	public void doCheckTestPath_valid() {
		final RailflowPublisher.DescriptorImpl descriptor = new RailflowPublisher.DescriptorImpl();
		this.assertFormValidation(descriptor.doCheckTestCasePath("path", ReportFormat.JUNIT), FormValidation.Kind.OK);
	}

	@Test
	public void doCheckTestPath_blank() {
		final RailflowPublisher.DescriptorImpl descriptor = new RailflowPublisher.DescriptorImpl();
		final FormValidation formValidation = descriptor.doCheckTestCasePath("  ", ReportFormat.JUNIT);
		this.assertFormValidation(formValidation, FormValidation.Kind.ERROR);
	}

	@Test
	public void doCheckTestPath_trx_no_section() {
		final RailflowPublisher.DescriptorImpl descriptor = new RailflowPublisher.DescriptorImpl();
		final FormValidation formValidation = descriptor.doCheckTestCasePath("Master", ReportFormat.TRX);
		assertEquals(Messages.sectionNameInTestPathIsRequiredInTrxReportFormat(), formValidation.getMessage());
		assertEquals(FormValidation.Kind.WARNING, formValidation.kind);
	}

	@Test
	public void doCheckTestRailProjectName_valid() {
		final RailflowPublisher.DescriptorImpl descriptor = new RailflowPublisher.DescriptorImpl();
		this.assertFormValidation(descriptor.doCheckTestRailProjectName("proj"), FormValidation.Kind.OK);
	}

	@Test
	public void doCheckTestRailProjectName_blank() {
		final RailflowPublisher.DescriptorImpl descriptor = new RailflowPublisher.DescriptorImpl();
		final FormValidation formValidation = descriptor.doCheckTestRailProjectName("   ");
		this.assertFormValidation(formValidation, FormValidation.Kind.ERROR);
	}

	@Test
	public void doCheckResultPattern_valid() {
		final RailflowPublisher.DescriptorImpl descriptor = new RailflowPublisher.DescriptorImpl();
		this.assertFormValidation(descriptor.doCheckResultFilePattern("pattern"), FormValidation.Kind.OK);
	}

	@Test
	public void doCheckResultPattern_blank() {
		final RailflowPublisher.DescriptorImpl descriptor = new RailflowPublisher.DescriptorImpl();
		final FormValidation formValidation = descriptor.doCheckResultFilePattern("   ");
		this.assertFormValidation(formValidation, FormValidation.Kind.ERROR);
	}

	@Test
	public void doCheckMilestonePath_valid() {
		final RailflowPublisher.DescriptorImpl descriptor = new RailflowPublisher.DescriptorImpl();
		this.assertFormValidation(descriptor.doCheckMilestonePath(null), FormValidation.Kind.OK);
		this.assertFormValidation(descriptor.doCheckMilestonePath(""), FormValidation.Kind.OK);
		this.assertFormValidation(descriptor.doCheckMilestonePath(" "), FormValidation.Kind.OK);
		this.assertFormValidation(descriptor.doCheckMilestonePath("Milestone"), FormValidation.Kind.OK);
		this.assertFormValidation(descriptor.doCheckMilestonePath("Milestone/Section1"), FormValidation.Kind.OK);
		final FormValidation formValidation = descriptor.doCheckMilestonePath("Milestone/Section1/Section2");
		assertEquals(FormValidation.Kind.ERROR, formValidation.kind);
		assertEquals(Messages.milestonePathInvalid(), formValidation.getMessage());
	}

	private void assertFormValidation(final FormValidation formValidation, final FormValidation.Kind kind) {
		assertEquals(kind, formValidation.kind);
		if (FormValidation.Kind.ERROR == kind) {
			assertEquals(Messages.thisFieldIsRequired(), formValidation.getMessage());
		}
	}

	private void callPerformAndVerify(final List<ParamsHolder> paramsHolders, final boolean failIfUploadFailed, final String userName, final Secret password)
			throws Exception {
		try {
			final List<RailflowUploadJobConfiguration> jobConfigs = paramsHolders.stream().map(h -> h.configuration).collect(Collectors.toList());
			final RailflowPublisher publisher = this.publisher(userName, password, failIfUploadFailed, jobConfigs);
			final TestRailParametersImpl trParams = new TestRailParametersImpl(URL, USER, PASSWORD);
			when(this.trParamFactory.create(this.globalConfig, SERVER_NAME, userName, password)).thenReturn(trParams);

			final FilePath workspace = new FilePath(this.temporaryFolder.getRoot());
			final Launcher launcher = mock(Launcher.class);
			final VirtualChannel channel = mock(VirtualChannel.class);
			for (final ParamsHolder holder : paramsHolders) {
				when(this.railflowCallableFactory.create(eq(this.licenseContent), eq(trParams), eq(holder.parameters), eq(this.resolve(holder.resultPattern)),
						eq(RUN_ID), eq(this.listener), eq(workspace), eq(true))).thenReturn(holder.callable);
				if (holder.error != null) {
					when(channel.call(holder.callable)).thenThrow(new RuntimeException(holder.error));
				} else {
					when(channel.call(holder.callable)).thenReturn(holder.runUrls);
				}
			}
			when(launcher.getChannel()).thenReturn(channel);

			publisher.perform(this.run, workspace, launcher, this.listener);
			verify(this.globalConfigValidator).validate(this.globalConfig);

			final InOrder order = inOrder(channel, this.run);
			for (final ParamsHolder holder : paramsHolders) {
				verify(this.railflowCallableFactory).create(eq(this.licenseContent), eq(trParams), eq(holder.parameters),
						eq(this.resolve(holder.resultPattern)), eq(RUN_ID), eq(this.listener), eq(workspace), eq(true));
				order.verify(channel).call(holder.callable);
				for (final String runUrl : holder.runUrls) {
					if (holder.error != null && !failIfUploadFailed) {
						order.verify(this.run, never()).addAction(new RailflowRunLinkAction(runUrl));
					} else {
						order.verify(this.run).addAction(new RailflowRunLinkAction(runUrl));
					}
				}
			}
		} catch (final Exception ex) {
			System.out.println(this.out);
			throw ex;
		}
	}

	private RailflowPublisher publisher(final String userName, final Secret password, final boolean failIfUploadFailed,
			final List<RailflowUploadJobConfiguration> jobConfigs) {
		final RailflowPublisher publisher = new RailflowPublisher(SERVER_NAME, PROJECT_NAME, userName, password, failIfUploadFailed, jobConfigs, true);
		publisher.setGlobalConfigFactory(this.configFactory);
		publisher.setGlobalConfigValidator(this.globalConfigValidator);
		publisher.setTestRailParametersFactory(this.trParamFactory);
		publisher.setUploadParametersFactory(this.uploadParamFactory);
		publisher.setRailflowCallableFactory(this.railflowCallableFactory);
		publisher.setPasswordEncrypter(this.passwordEncrypter);
		return publisher;
	}

	private RailflowUploadJobConfiguration uploadJobConfiguration(final String resultPattern) {
		final RailflowUploadJobConfiguration configuration = new RailflowUploadJobConfiguration();
		configuration.setResultFilePattern(resultPattern);
		return configuration;
	}

	private void assertPublisher(final RailflowPublisher publisher, final String server, final String project,
			final List<RailflowUploadJobConfiguration> uploadConfigs) {
		assertEquals(server, publisher.getTestRailServerName());
		assertEquals(project, publisher.getTestRailProjectName());
		assertEquals(uploadConfigs, publisher.getJobConfigurations());
	}

	private String resolve(final String value) {
		return value.replaceAll("\\$\\{VAR}", "variable");
	}

	private void perform(final boolean failed, final boolean uploadStatistics, final boolean trial, final VirtualChannel channel) throws Exception {
		final TestRailParametersImpl trParams = new TestRailParametersImpl(URL, USER, PASSWORD);
		when(this.trParamFactory.create(any(), any(), any(), any())).thenReturn(trParams);

		final List<ParamsHolder> paramsHolders = Collections.singletonList(new ParamsHolder("${VAR}*.xml", Arrays.asList("url1", "url2")));
		final FilePath workspace = new FilePath(this.temporaryFolder.getRoot());
		final Launcher launcher = mock(Launcher.class);
		when(launcher.getChannel()).thenReturn(channel);

		when(this.globalConfig.isOnlineActivation()).thenReturn(true);
		when(this.globalConfig.isUploadStatistics()).thenReturn(uploadStatistics);
		when(this.globalConfig.isTrial()).thenReturn(trial);
		final List<RailflowUploadJobConfiguration> jobConfigs = paramsHolders.stream().map(h -> h.configuration).collect(Collectors.toList());
		final RailflowPublisher publisher = this.publisher(null, null, true, jobConfigs);

		if (failed) {
			assertThrows(AbortException.class, () -> publisher.perform(this.run, workspace, launcher, this.listener));
		} else {
			publisher.perform(this.run, workspace, launcher, this.listener);
		}
	}

	private class ParamsHolder {
		private final String resultPattern;
		private final RailflowUploadJobConfiguration configuration;
		private final UploadParameters parameters;
		private final List<String> runUrls;
		private final RailflowCallable callable;
		private String error;

		public ParamsHolder(final String resultPattern, final List<String> runUrls) {
			this.resultPattern = resultPattern;
			this.runUrls = runUrls;
			this.configuration = RailflowPublisherTest.this.uploadJobConfiguration(resultPattern);
			this.parameters = new UploadParametersImpl(PROJECT_NAME, UUID.randomUUID().toString(), ReportFormat.JUNIT, SearchMode.PATH);
			this.callable = mock(RailflowCallable.class);
			when(RailflowPublisherTest.this.uploadParamFactory.create(eq(PROJECT_NAME), eq(this.configuration), any())).thenReturn(this.parameters);
		}
	}
}
