package io.jenkins.plugins.railflow.jenkins.admin;
// package io.railflow.jenkins.admin;

// import static org.junit.Assert.assertEquals;
// import static org.junit.Assert.assertNull;
// import static org.junit.Assert.assertTrue;
// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.ArgumentMatchers.anyString;
// import static org.mockito.ArgumentMatchers.eq;
// import static org.mockito.Mockito.doAnswer;
// import static org.mockito.Mockito.inOrder;
// import static org.mockito.Mockito.mock;
// import static org.mockito.Mockito.spy;
// import static org.mockito.Mockito.times;
// import static org.mockito.Mockito.verify;
// import static org.mockito.Mockito.when;

// import java.io.ByteArrayOutputStream;
// import java.io.File;
// import java.io.PrintWriter;
// import java.nio.charset.StandardCharsets;
// import java.nio.file.Files;
// import java.text.MessageFormat;
// import java.text.ParseException;
// import java.text.SimpleDateFormat;
// import java.util.Arrays;
// import java.util.Date;
// import java.util.Locale;
// import java.util.Optional;

// import javax.annotation.Nonnull;
// import javax.servlet.ServletException;

// import org.acegisecurity.Authentication;
// import org.apache.commons.fileupload.FileItem;
// import org.junit.Before;
// import org.junit.Rule;
// import org.junit.Test;
// import org.junit.rules.TemporaryFolder;
// import org.junit.runner.RunWith;
// import org.kohsuke.stapler.HttpResponse;
// import org.kohsuke.stapler.StaplerRequest;
// import org.kohsuke.stapler.StaplerResponse;
// import org.mockito.InOrder;
// import org.mockito.MockedStatic;
// import org.mockito.Mockito;
// // import org.powermock.api.mockito.PowerMockito;
// // import org.powermock.core.classloader.annotations.PrepareForTest;
// // import org.powermock.modules.junit4.PowerMockRunner;
// import org.mockito.junit.MockitoJUnitRunner;

// import hudson.ProxyConfiguration;
// import hudson.model.Descriptor;
// import hudson.model.FileParameterValue;
// import hudson.security.ACL;
// import hudson.security.Permission;
// import hudson.util.Secret;
// import io.railflow.LicenseInfo;
// import io.railflow.commons.statistics.ServerType;
// import io.railflow.jenkins.cli.RailflowJenkinsCli;
// import io.railflow.jenkins.util.LicenseInfoProvider;
// import io.railflow.jenkins.util.PasswordEncrypter;
// import jenkins.model.Jenkins;
// import net.sf.json.JSONObject;

// /**
//  * Tests for {@link GlobalConfig}
//  *
//  * @author Sergey Oplavin
//  */
// @RunWith(MockitoJUnitRunner.class)
// // @PrepareForTest({ Jenkins.class, Secret.class, ProxyConfiguration.class, RailflowJenkinsCli.class })
// public class GlobalConfigTest {

// 	public static final String PERPETUAL = "Perpetual";
// 	private static final ThreadLocal<SimpleDateFormat> DATE_FORMAT = ThreadLocal
// 			.withInitial(() -> new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss", Locale.ENGLISH));
// 	private final Jenkins jenkins = mock(Jenkins.class);
// 	private final Secret encrypted = mock(Secret.class);
// 	private final PasswordEncrypter passwordEncrypter = mock(PasswordEncrypter.class);
// 	private final LicenseInfoProvider licenseInfoProvider = mock(LicenseInfoProvider.class);

// 	@Rule
// 	public TemporaryFolder temporaryFolder = new TemporaryFolder();
// 	private GlobalConfig globalConfig;
// 	private ProxyConfiguration proxy;
// 	private MockedStatic<RailflowJenkinsCli> mockedCli;

// 	@Before
// 	public void setUp() {
// 		final MockedStatic<Jenkins> staticJenkins = Mockito.mockStatic(Jenkins.class);
// 		// Mockito.mockStatic(ProxyConfiguration.class);
// 		Mockito.when(Jenkins.get()).thenReturn(this.jenkins);
// 		Mockito.when(Jenkins.getAuthentication()).thenCallRealMethod();
// 		when(this.jenkins.getACL()).thenReturn(new ACL() {
// 			@Override
// 			public boolean hasPermission(@Nonnull final Authentication a, @Nonnull final Permission permission) {
// 				return true;
// 			}
// 		});
// 		this.globalConfig = spy(new GlobalConfig(this.passwordEncrypter, this.licenseInfoProvider));
// 		doAnswer((i) -> null).when(this.globalConfig).save();
// 		this.proxy = mock(ProxyConfiguration.class);
// 		this.jenkins.proxy = this.proxy;

// 		mockedCli = Mockito.mockStatic(RailflowJenkinsCli.class);
// 	}

// 	@Test
// 	public void configure() throws Descriptor.FormException {
// 		final StaplerRequest request = mock(StaplerRequest.class);
// 		final JSONObject json = new JSONObject();
// 		this.globalConfig.configure(request, json);
// 		final InOrder order = inOrder(this.jenkins, this.globalConfig, request);
// 		order.verify(this.jenkins).checkPermission(Jenkins.ADMINISTER);
// 		order.verify(request).bindJSON(this.globalConfig, json);
// 		order.verify(this.globalConfig).save();

// 	}

// 	@Test
// 	public void configure_with_collectConfigEvent() throws Descriptor.FormException {
// 		final StaplerRequest request = mock(StaplerRequest.class);
// 		final JSONObject json = new JSONObject();
// 		this.globalConfig.setUploadStatistics(true);
// 		this.globalConfig.setLicenseContent(mock(Secret.class));
// 		this.globalConfig.configure(request, json);
// 		mockedCli.verify(() -> times(1));
// 		RailflowJenkinsCli.collectConfigEvent(eq(ServerType.Jenkins), anyString(), anyString(), any(), any());
// 	}

// 	@Test
// 	public void configure_without_collectConfigEvent() throws Descriptor.FormException {
// 		final StaplerRequest request = mock(StaplerRequest.class);
// 		final JSONObject json = new JSONObject();
// 		this.globalConfig.setUploadStatistics(false);
// 		this.globalConfig.setTrial(false);
// 		this.globalConfig.setLicenseContent(mock(Secret.class));
// 		this.globalConfig.configure(request, json);
// 		mockedCli.verify(() -> times(0));
// 		RailflowJenkinsCli.collectConfigEvent(eq(ServerType.Jenkins), anyString(), anyString(), any(), any());
// 	}

// 	@Test
// 	public void configure_trail() throws Descriptor.FormException {
// 		final StaplerRequest request = mock(StaplerRequest.class);
// 		final JSONObject json = new JSONObject();
// 		this.globalConfig.setUploadStatistics(false);
// 		this.globalConfig.setTrial(true);
// 		this.globalConfig.setLicenseContent(mock(Secret.class));
// 		this.globalConfig.configure(request, json);
// 		mockedCli.verify(() -> times(1));
// 		RailflowJenkinsCli.collectConfigEvent(eq(ServerType.Jenkins), anyString(), anyString(), any(), any());
// 	}

// 	@Test
// 	public void configure_should_make_testrail_servers_empty() throws Descriptor.FormException, ServletException {
// 		final StaplerRequest request = mock(StaplerRequest.class);
// 		final JSONObject json = new JSONObject();
// 		when(request.getSubmittedForm()).thenReturn(json);
// 		this.globalConfig.setLicenseContent(mock(Secret.class));
// 		this.globalConfig.setTestRailServers(Arrays.asList(new TestRailServerConfig()));
// 		this.globalConfig.configure(request, json);
// 		assertEquals(0, this.globalConfig.getTestRailServers().size());
// 	}

// 	@Test
// 	public void doUploadLicenseFile_licenseValid() throws Exception {
// 		final Date expDate = new Date(System.currentTimeMillis() + 100000);
// 		final LicenseInfo license = new LicenseInfo(expDate, false, "license content".getBytes(StandardCharsets.UTF_8), false, false, false);
// 		this.callDoUploadLicenseAndVerify(license);
// 	}

// 	@Test
// 	public void doUploadLicenseFile_licenseExpired() throws Exception {
// 		final Date expDate = new Date(System.currentTimeMillis() - 100000);
// 		final LicenseInfo license = new LicenseInfo(expDate, true, "license content".getBytes(StandardCharsets.UTF_8), false, false, false);
// 		this.callDoUploadLicenseAndVerify(license);
// 	}

// 	@Test
// 	public void doUploadLicenseFile_licensePerpetual() throws Exception {
// 		final Date expDate = new Date(System.currentTimeMillis() - 100000);
// 		final LicenseInfo license = new LicenseInfo(expDate, true, "license content".getBytes(StandardCharsets.UTF_8), true, false, false);
// 		this.callDoUploadLicenseAndVerify(license);
// 	}

// 	@Test
// 	public void doUploadLicenseFile_noLicense() throws Exception {
// 		final StaplerRequest request = mock(StaplerRequest.class);
// 		this.assertError(this.globalConfig.doUploadLicenseFile(request), "License file is not selected");
// 	}

// 	@Test
// 	public void doActivateLicenseKey_licenseValid() throws Exception {
// 		final String licenseKey = "aaa";
// 		final LicenseInfo info = new LicenseInfo(new Date(System.currentTimeMillis() + 10000), false, licenseKey.getBytes(StandardCharsets.UTF_8),
// 				false, false, false);
// 		this.callDoActivateLicenseKey(info);
// 	}

// 	@Test
// 	public void doActivateLicenseKey_licenseExpired() throws Exception {
// 		final String licenseKey = "aaa";
// 		final LicenseInfo info = new LicenseInfo(new Date(), true, licenseKey.getBytes(StandardCharsets.UTF_8), false, false, false);
// 		this.callDoActivateLicenseKey(info);
// 	}

// 	@Test
// 	public void doActivateLicenseKey_licensePerpetual() throws Exception {
// 		final String licenseKey = "aaa";
// 		final LicenseInfo info = new LicenseInfo(new Date(), true, licenseKey.getBytes(StandardCharsets.UTF_8), true, false, false);
// 		this.callDoActivateLicenseKey(info);
// 	}

// 	@Test
// 	public void doActivateLicenseKey_licenseNull() throws Exception {
// 		final HttpResponse httpResponse = this.globalConfig.doActivateLicenseKey(null);
// 		this.assertError(httpResponse, "License key is empty");
// 	}

// 	@Test
// 	public void doActivateLicenseKey_licenseEmpty() throws Exception {
// 		final HttpResponse httpResponse = this.globalConfig.doActivateLicenseKey(" ");
// 		this.assertError(httpResponse, "License key is empty");
// 	}

// 	@Test
// 	public void doActivateLicenseKey_freeLicense() throws Exception {
// 		final String licenseKey = "aaa";
// 		final LicenseInfo info = new LicenseInfo(new Date(), true, licenseKey.getBytes(StandardCharsets.UTF_8), false, false, true);
// 		this.callDoActivateLicenseKey(info);
// 	}

// 	@Test
// 	public void getLicenseExpirationDate_dateIsNull_licenseContentSet() throws Exception {
// 		final Date expirationDate = new Date();
// 		this.setExpirationDate(expirationDate, false);
// 		assertEquals(getFormattedDate(expirationDate), this.globalConfig.getLicenseExpirationDate());
// 	}

// 	@Test
// 	public void getLicenseExpirationDate_dateIsNull_licenseContentIsNull() {
// 		this.globalConfig.setLicenseContent(null);
// 		assertNull(this.globalConfig.getLicenseExpirationDate());
// 	}

// 	@Test
// 	public void getLicenseExpirationDateString() throws Exception {
// 		final Date expirationDate = new Date();
// 		this.setExpirationDate(expirationDate, false);
// 		assertEquals(DATE_FORMAT.get().format(expirationDate), this.globalConfig.getLicenseExpirationDateString());
// 	}

// 	@Test
// 	public void getLicenseExpirationDateString_expDateIsNull() {
// 		final Date expirationDate = new Date();
// 		this.globalConfig.setLicenseContent(null);
// 		assertEquals(MessageFormat.format("N/A", expirationDate), this.globalConfig.getLicenseExpirationDateString());
// 	}

// 	@Test
// 	public void getLicenseExpirationDateString_perpetual() throws Exception {
// 		final Date expirationDate = new Date();
// 		this.setExpirationDate(expirationDate, true);
// 		assertEquals(PERPETUAL, this.globalConfig.getLicenseExpirationDate());
// 	}

// 	@Test
// 	public void doUploadLicenseFile_freeLicense() throws Exception {
// 		final Date expDate = new Date(System.currentTimeMillis() - 100000);
// 		final LicenseInfo license = new LicenseInfo(expDate, true, "license content".getBytes(StandardCharsets.UTF_8), false, false, true);
// 		this.callDoUploadLicenseAndVerify(license);
// 	}

// 	private void setExpirationDate(final Date expirationDate, final boolean isPerpetual) throws Exception {
// 		final String licenseContent = "test";
// 		final LicenseInfo licenseInfo = new LicenseInfo(expirationDate, false, licenseContent.getBytes(StandardCharsets.UTF_8), isPerpetual, false, false);
// 		when(this.licenseInfoProvider.getLicenseInfo(licenseContent, this.proxy)).thenReturn(Optional.of(licenseInfo));
// 		when(this.passwordEncrypter.decrypt(this.encrypted)).thenReturn(licenseContent);
// 		this.globalConfig.setLicenseContent(this.encrypted);
// 	}

// 	private void assertError(final HttpResponse httpResponse, final String expectedError) throws Exception {
// 		assertTrue((httpResponse instanceof GlobalConfig.ErrorResponse));
// 		final StaplerResponse response = mock(StaplerResponse.class);
// 		final ByteArrayOutputStream out = new ByteArrayOutputStream();
// 		when(response.getWriter()).thenReturn(new PrintWriter(out, true));
// 		httpResponse.generateResponse(null, response, null);

// 		verify(response).setStatus(500);
// 		verify(response).setContentType("text/plain;charset=UTF-8");
// 		assertEquals(expectedError, out.toString());
// 	}

// 	private void callDoUploadLicenseAndVerify(final LicenseInfo licenseInfo) throws Exception {
// 		final File file = this.temporaryFolder.newFile();
// 		final String licenseContent = "license content";
// 		Files.write(file.toPath(), licenseContent.getBytes(StandardCharsets.UTF_8));
// 		when(this.licenseInfoProvider.getLicenseInfo(licenseContent, this.proxy)).thenReturn(Optional.ofNullable(licenseInfo));
// 		when(this.passwordEncrypter.encrypt(licenseContent)).thenReturn(this.encrypted);
// 		final FileItem fileItem = new FileParameterValue.FileItemImpl(file);
// 		final StaplerRequest request = mock(StaplerRequest.class);
// 		when(request.getFileItem("licenseFile")).thenReturn(fileItem);
// 		this.verifyLicenseInfo(licenseInfo, this.globalConfig.doUploadLicenseFile(request));
// 	}

// 	private void callDoActivateLicenseKey(final LicenseInfo info) throws Exception {
// 		final String licenseKey = "aaa";
// 		when(this.licenseInfoProvider.getLicenseInfo(licenseKey, this.proxy)).thenReturn(Optional.ofNullable(info));
// 		when(this.passwordEncrypter.encrypt(licenseKey)).thenReturn(this.encrypted);
// 		this.verifyLicenseInfo(info, this.globalConfig.doActivateLicenseKey(licenseKey));
// 		verify(this.licenseInfoProvider).getLicenseInfo(licenseKey, this.proxy);
// 	}

// 	private void verifyLicenseInfo(final LicenseInfo licenseInfo, final HttpResponse httpResponse) throws ParseException {
// 		if (licenseInfo.isFree()) {
// 			assertTrue(httpResponse instanceof GlobalConfig.ErrorResponse);
// 		}
// 		if (httpResponse instanceof GlobalConfig.ErrorResponse) {
// 			assertNull(this.globalConfig.getLicenseContent());
// 			assertNull(this.globalConfig.getLicenseExpirationDate());
// 		} else {
// 			assertEquals(this.encrypted, this.globalConfig.getLicenseContent());
// 			if (!licenseInfo.isPerpetual()) {
// 				assertEquals(getFormattedDate(licenseInfo.getExpirationDate()), this.globalConfig.getLicenseExpirationDate());
// 			} else {
// 				assertEquals(PERPETUAL, this.globalConfig.getLicenseExpirationDate());

// 			}
// 		}
// 		verify(this.jenkins).checkPermission(Jenkins.ADMINISTER);
// 	}

// 	private String getFormattedDate(final Date date) throws ParseException {
// 		return date != null ? DATE_FORMAT.get().format(date) : null;
// 	}

// }
