package io.jenkins.plugins.railflow.commons.http;

import java.io.Serializable;

/**
 * Proxy settings.
 *
 * @author Sergey Oplavin
 *
 */
public interface ProxySettings extends Serializable {
	/**
	 * @return proxy type
	 */
	String getProtocol();

	/**
	 * @return host
	 */
	String getHost();

	/**
	 * @return port.
	 */
	int getPort();

	/**
	 * @return user name or <code>null</code> if proxy does not require
	 *         authorization.
	 */
	String getUserName();

	/**
	 * @return password or <code>null</code> if proxy does not require
	 *         authorization.
	 */
	String getPassword();
}
