package io.railflow;

import java.util.Optional;

import io.railflow.commons.http.ProxySettings;

/**
 * Default implementation of {@link TestRailParameters}.
 * 
 * @author Liu Yang
 */
public class TestRailParametersImpl implements TestRailParameters {
	private static final long serialVersionUID = -8350531810453831400L;

	private String url;
	private String username;
	private String password;
	private int timeout;
	private ProxySettings proxySettings;

	public TestRailParametersImpl(final String url, final String username, final String password) {
		this.url = url;
		this.username = username;
		this.password = password;
	}

	@Override
	public String getUrl() {
		return this.url;
	}

	public void setUrl(final String url) {
		this.url = url;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	public void setUsername(final String username) {
		this.username = username;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	@Override
	public int getTimeout() {
		return this.timeout;
	}

	public void setTimeout(final int connectionTimeout) {
		this.timeout = connectionTimeout;
	}

	@Override
	public Optional<ProxySettings> getProxySettings() {
		return Optional.ofNullable(this.proxySettings);
	}

	public void setProxySettings(final ProxySettings proxySettings) {
		this.proxySettings = proxySettings;
	}
}
