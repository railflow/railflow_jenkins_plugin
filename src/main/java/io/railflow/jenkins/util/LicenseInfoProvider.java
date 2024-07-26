package io.railflow.jenkins.util;

import java.util.Optional;

import hudson.ProxyConfiguration;
import io.railflow.LicenseInfo;

/**
 * Provider for {@link LicenseInfo} objects.
 *
 * @author Sergey Oplavin
 */
@FunctionalInterface
public interface LicenseInfoProvider {
	/**
	 * Provides license information.
	 * 
	 * @param licenseKeyOrContent
	 *            license key or offline license content.
	 * @param proxy
	 *            proxy settings which are needed to connect to remote license
	 *            server in case if license key is provided. If no proxy
	 *            settings are required, pass <code>null</code>.
	 * @return license info object or empty optional.
	 * @throws Exception
	 *             if something went wrong.
	 */
	Optional<LicenseInfo> getLicenseInfo(String licenseKeyOrContent, ProxyConfiguration proxy) throws Exception;
}
