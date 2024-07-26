package io.railflow.jenkins.util;

import hudson.ProxyConfiguration;
import hudson.Util;
import io.railflow.commons.http.ProxySettings;
import io.railflow.commons.http.ProxySettingsImpl;

/**
 * Factory for producing {@link ProxySettings} from Jenkins proxy config.
 * 
 * @author Sergey Oplavin
 */
public enum RailflowProxySettingsFactory {

	THE_INSTANCE;

	/**
	 * Create new {@link ProxySettings} object from Jenkins
	 * {@link ProxyConfiguration}. Might return <code>null</code>.
	 * 
	 * @param proxyConfig
	 *            Jenkins proxy configuration
	 * @return newly create {@link ProxySettings} object or <code>null</code> if
	 *         proxyConfig parameter is null or does not contain proxy server
	 *         URL.
	 */
	public ProxySettings create(final ProxyConfiguration proxyConfig) {
		if (proxyConfig == null) {
			return null;
		}
		final String proxyUrl = Util.fixEmptyAndTrim(proxyConfig.name);
		if (proxyUrl == null) {
			return null;
		}
		final ProxySettingsImpl proxySettings = new ProxySettingsImpl("http", proxyUrl, proxyConfig.port);
		final String user = Util.fixEmptyAndTrim(proxyConfig.getUserName());
		if (user != null) {
			proxySettings.setUserName(user);
			proxySettings.setPassword(Util.fixEmptyAndTrim(proxyConfig.getPassword()));
		}

		return proxySettings;
	}
}
