// package io.railflow.jenkins.testresults;

// import static org.junit.Assert.assertEquals;
// import static org.junit.Assert.assertThrows;
// import static org.mockito.Mockito.mock;
// import static org.mockito.Mockito.when;

// import java.util.Arrays;
// import java.util.Collections;

// import org.junit.Before;
// import org.junit.Test;
// import org.junit.runner.RunWith;
// import org.powermock.api.mockito.PowerMockito;
// import org.powermock.core.classloader.annotations.PrepareForTest;
// import org.powermock.modules.junit4.PowerMockRunner;

// import hudson.ProxyConfiguration;
// import hudson.util.Secret;
// import io.railflow.TestRailParameters;
// import io.railflow.commons.http.ProxySettings;
// import io.railflow.jenkins.admin.GlobalConfig;
// import io.railflow.jenkins.admin.TestRailServerConfig;
// import jenkins.model.Jenkins;

// /**
//  * Tests for {@link TestRailParametersFactoryImpl}
//  *
//  * @author Sergey Oplavin
//  */
// @RunWith(PowerMockRunner.class)
// @PrepareForTest({ Secret.class, Jenkins.class })
// public class TestRailParametersFactoryImplTest {

// 	private static final String OVERRIDDEN_USER = "overriddenUser";
// 	private static final String OVERRIDDEN_PASSWORD = "overriddenPassword";
// 	private static final Secret OVERRIDDEN_PASSWORD_SECRET = secret(OVERRIDDEN_PASSWORD);
// 	private static final String USER = "user";
// 	private static final String PASSWORD_PLAIN = "password";
// 	private static final Secret PASSWORD_SECRET = secret(PASSWORD_PLAIN);
// 	private static final String SERVER_NAME = "s2";
// 	private static final String TESTRAIL_URL = "https://testrail";
// 	private final TestRailParametersFactoryImpl factory = TestRailParametersFactoryImpl.THE_INSTANCE;
// 	private final GlobalConfig globalConfig = mock(GlobalConfig.class);
// 	private Jenkins jenkins;

// 	private static Secret secret(final String plain) {
// 		final Secret secret = PowerMockito.mock(Secret.class);
// 		when(secret.getPlainText()).thenReturn(plain);
// 		when(secret.getEncryptedValue()).thenReturn("encr: " + plain);
// 		return secret;
// 	}

// 	@Before
// 	public void beforeTest() {
// 		PowerMockito.mockStatic(Jenkins.class);
// 		this.jenkins = mock(Jenkins.class);
// 		PowerMockito.when(Jenkins.get()).thenReturn(this.jenkins);
// 	}

// 	@Test
// 	public void create() {
// 		this.jenkins.proxy = new ProxyConfiguration("url", 8080);
// 		final GlobalConfig globalConfig = mock(GlobalConfig.class);

// 		final Secret s1 = secret("smth");
// 		when(globalConfig.getTestRailServers())
// 				.thenReturn(Arrays.asList(this.server("s1", "http://something", "some", s1),
// 						this.server(SERVER_NAME, TESTRAIL_URL, USER, PASSWORD_SECRET)));
// 		final TestRailParameters parameters = this.factory.create(globalConfig, SERVER_NAME, null, null);
// 		this.assertParameters(parameters);
// 		final ProxySettings proxySettings = parameters.getProxySettings().get();
// 		assertEquals("url", proxySettings.getHost());
// 		assertEquals(8080, proxySettings.getPort());
// 	}

// 	@Test
// 	public void create_overridden_credentials() {
// 		final GlobalConfig globalConfig = mock(GlobalConfig.class);
// 		final Secret secret = secret("some pwd");
// 		when(globalConfig.getTestRailServers())
// 				.thenReturn(Arrays.asList(this.server("s1", "http://something", "some user", secret),
// 						this.server(SERVER_NAME, TESTRAIL_URL, USER, PASSWORD_SECRET)));
// 		this.assertOverriddenParameters(this.factory.create(globalConfig, SERVER_NAME, OVERRIDDEN_USER, OVERRIDDEN_PASSWORD_SECRET));
// 	}

// 	@Test
// 	public void create_testRailServerName_null_error() {
// 		final NullPointerException npe = assertThrows(NullPointerException.class, () -> this.factory.create(this.globalConfig, null, null, null));
// 		assertEquals("TestRail server name is null", npe.getMessage());
// 	}

// 	@Test
// 	public void create_testRailServers_null_error() {
// 		when(this.globalConfig.getTestRailServers()).thenReturn(null);
// 		final NullPointerException npe = assertThrows(NullPointerException.class, () -> this.factory.create(this.globalConfig, "server", null, null));
// 		assertEquals("TestRail Server list is null, please add at least one TestRail server on Jenkins Global Configuration page", npe.getMessage());
// 	}

// 	@Test
// 	public void create_testRailServer_does_not_exist_error() {
// 		final String testRailServerName = "server";
// 		when(this.globalConfig.getTestRailServers())
// 				.thenReturn(Collections.singletonList(this.server("someName", "url", OVERRIDDEN_USER, OVERRIDDEN_PASSWORD_SECRET)));
// 		final RuntimeException e = assertThrows(RuntimeException.class, () -> this.factory.create(this.globalConfig, testRailServerName, "", null));
// 		assertEquals("TestRail server with name '" + testRailServerName + "' does not exist in the global configuration", e.getMessage());
// 	}

// 	@Test
// 	public void create_testRailServer_credentials_empty_error() {
// 		final Secret secret = secret("");
// 		when(this.globalConfig.getTestRailServers()).thenReturn(Collections.singletonList(this.server(SERVER_NAME, "url", "", secret)));
// 		final RuntimeException e = assertThrows(RuntimeException.class, () -> this.factory.create(this.globalConfig, SERVER_NAME, "", null));
// 		assertEquals("TestRail credentials are not set for the TestRail server: '" + SERVER_NAME + "'", e.getMessage());
// 	}

// 	@Test
// 	public void create_testRailServer_user_empty_overridden_user_set() {
// 		when(this.globalConfig.getTestRailServers()).thenReturn(Collections.singletonList(this.server(SERVER_NAME, TESTRAIL_URL, "", null)));
// 		final TestRailParameters parameters = this.factory.create(this.globalConfig, SERVER_NAME, OVERRIDDEN_USER, OVERRIDDEN_PASSWORD_SECRET);
// 		this.assertOverriddenParameters(parameters);
// 	}

// 	@Test
// 	public void create_testRailServer_user_empty_overridden_user_empty_error() {
// 		final Secret secret = secret("");
// 		when(this.globalConfig.getTestRailServers()).thenReturn(Collections.singletonList(this.server(SERVER_NAME, "url", "", secret)));
// 		final RuntimeException e = assertThrows(RuntimeException.class, () -> this.factory.create(this.globalConfig, SERVER_NAME, "", secret));
// 		assertEquals("TestRail credentials are not set for the TestRail server: '" + SERVER_NAME + "'", e.getMessage());
// 	}

// 	@Test
// 	public void create_testRailServer_url_null_error() {
// 		when(this.globalConfig.getTestRailServers())
// 				.thenReturn(Collections.singletonList(this.server(SERVER_NAME, "", OVERRIDDEN_USER, OVERRIDDEN_PASSWORD_SECRET)));
// 		final NullPointerException e = assertThrows(NullPointerException.class,
// 				() -> this.factory.create(this.globalConfig, SERVER_NAME, "", secret("")));
// 		assertEquals("TestRail server with name: '" + SERVER_NAME + "' has empty TestRail URL", e.getMessage());
// 	}

// 	private TestRailServerConfig server(final String name, final String url, final String userName, final Secret password) {
// 		final TestRailServerConfig config = new TestRailServerConfig();
// 		config.setName(name);
// 		config.setTestRailUrl(url);
// 		config.setTestRailUserName(userName);
// 		config.setTestRailPassword(password);
// 		return config;
// 	}

// 	private void assertParameters(final TestRailParameters parameters) {
// 		assertEquals(TESTRAIL_URL, parameters.getUrl());
// 		assertEquals(USER, parameters.getUsername());
// 		assertEquals(PASSWORD_PLAIN, parameters.getPassword());
// 	}

// 	private void assertOverriddenParameters(final TestRailParameters parameters) {
// 		assertEquals(TESTRAIL_URL, parameters.getUrl());
// 		assertEquals(OVERRIDDEN_USER, parameters.getUsername());
// 		assertEquals(OVERRIDDEN_PASSWORD, parameters.getPassword());
// 	}

// }