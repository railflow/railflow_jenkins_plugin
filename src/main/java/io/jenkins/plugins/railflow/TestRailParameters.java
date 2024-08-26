package io.jenkins.plugins.railflow;

import java.io.Serializable;
import java.util.Optional;

import edu.umd.cs.findbugs.annotations.NonNull;
import io.jenkins.plugins.railflow.commons.http.ProxySettings;

/**
 * Holds the parameters for connecting to TestRail.
 *
 * @author Liu Yang
 */
public interface TestRailParameters extends Serializable {
	/**
	 * New builder.
	 *
	 * @param testRailUrl
	 *            the TestRail url.
	 * @param testRailUsername
	 *            the TestRail username.
	 * @param testRailPassword
	 *            the password of the TestRail user.
	 * @return builder.
	 */
	static TestRailParameters.Builder builder(final String testRailUrl, final String testRailUsername, final String testRailPassword) {
		return new TestRailParameters.Builder(testRailUrl, testRailUsername, testRailPassword);
	}

	/**
	 * Gets the TestRail url.
	 *
	 * @return the TestRail URL
	 */
	@NonNull
	String getUrl();

	/**
	 * Gets the TestRail username.
	 *
	 * @return the TestRail username
	 */
	@NonNull
	String getUsername();

	/**
	 * Gets the password of the TestRail user.
	 *
	 * @return the password of the TestRail user
	 */
	@NonNull
	String getPassword();

	/**
	 * Gets the connection timeout to the TestRail service.
	 *
	 * @return the connection timeout in seconds
	 */
	int getTimeout();

	/**
	 * Gets the proxy settings.
	 *
	 * @return the proxy settings
	 */
	Optional<ProxySettings> getProxySettings();

	class Builder {
		private final TestRailParametersImpl testRailParameters;

		private Builder(final String url, final String username, final String password) {
			this.testRailParameters = new TestRailParametersImpl(url, username, password);
		}

		public Builder timeout(final int timeout) {
			this.testRailParameters.setTimeout(timeout);
			return this;
		}

		public Builder proxySettings(final ProxySettings proxySettings) {
			this.testRailParameters.setProxySettings(proxySettings);
			return this;
		}

		public TestRailParameters build() {
			return this.testRailParameters;
		}
	}
}
