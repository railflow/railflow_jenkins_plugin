package io.railflow.jenkins.util;

import java.util.Optional;

import hudson.ProxyConfiguration;
import io.railflow.LicenseInfo;
import io.railflow.jenkins.cli.RailflowJenkinsCli;

/**
 * Default implementation for {@link LicenseInfoProvider} which uses
 * {@link Railflow#getLicense(String, io.railflow.commons.http.ProxySettings)}.
 *
 * @author Sergey Oplavin
 */
public enum RailflowLicenseInfoProvider implements LicenseInfoProvider {
	THE_INSTANCE;

	@Override
	public Optional<LicenseInfo> getLicenseInfo(final String licenseKeyOrContent, final ProxyConfiguration proxy) throws Exception {
		return RailflowJenkinsCli.getLicense(licenseKeyOrContent, RailflowProxySettingsFactory.THE_INSTANCE.create(proxy));
	}
}
