package io.jenkins.plugins.railflow.jenkins.util;
// package io.railflow.jenkins.util;

// import static org.junit.Assert.assertEquals;
// import static org.junit.Assert.assertNull;
// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.Mockito.mock;
// import static org.mockito.Mockito.when;

// import org.junit.Before;
// import org.junit.Test;
// import org.junit.runner.RunWith;
// import org.powermock.api.mockito.PowerMockito;
// import org.powermock.core.classloader.annotations.PrepareForTest;
// import org.powermock.modules.junit4.PowerMockRunner;

// import hudson.ProxyConfiguration;
// import hudson.util.Secret;
// import io.railflow.commons.http.ProxySettings;
// import jenkins.model.Jenkins;

// /**
//  * Tests for {@link RailflowProxySettingsFactory}
//  * 
//  * @author Sergey Oplavin
//  */
// @RunWith(PowerMockRunner.class)
// @PrepareForTest({ Jenkins.class, ProxyConfiguration.class, Secret.class })
// public class RailflowProxySettingsFactoryTest {

// 	private static final String URL = "url";
// 	private static final int PORT = 8080;
// 	private final Secret secret = mock(Secret.class);

// 	@Before
// 	public void setUp() {
// 		PowerMockito.mockStatic(Jenkins.class, ProxyConfiguration.class, Secret.class);
// 		final Jenkins jenkins = mock(Jenkins.class);
// 		PowerMockito.when(Jenkins.get()).thenReturn(jenkins);
// 		when(Secret.fromString(any())).thenReturn(this.secret);
// 	}

// 	@Test
// 	public void create() {
// 		final String user = "user";
// 		final String password = "password";
// 		when(Secret.toString(this.secret)).thenReturn(password);
// 		final ProxyConfiguration proxyConfig = new ProxyConfiguration(URL, PORT, user, password);
// 		final ProxySettings proxySettings = RailflowProxySettingsFactory.THE_INSTANCE.create(proxyConfig);
// 		this.assertProxySettings(proxySettings, user, password);
// 	}

// 	@Test
// 	public void create_noUser() {
// 		final String url = "url";
// 		final int port = 8080;
// 		final ProxyConfiguration proxyConfig = new ProxyConfiguration(url, port);
// 		final ProxySettings proxySettings = RailflowProxySettingsFactory.THE_INSTANCE.create(proxyConfig);
// 		this.assertProxySettings(proxySettings, null, null);
// 	}

// 	@Test
// 	public void create_nullProxy() {
// 		assertNull(RailflowProxySettingsFactory.THE_INSTANCE.create(null));
// 	}

// 	private void assertProxySettings(final ProxySettings settings, final String user, final String password) {
// 		assertEquals("http", settings.getProtocol());
// 		assertEquals(URL, settings.getHost());
// 		assertEquals(PORT, settings.getPort());
// 		assertEquals(user, settings.getUserName());
// 		assertEquals(password, settings.getPassword());
// 	}
// }