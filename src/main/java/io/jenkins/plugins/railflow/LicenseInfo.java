package io.jenkins.plugins.railflow;

import java.util.Date;

/**
 * Holds some information about the license.
 * 
 * @author Sergey Oplavin
 */
public class LicenseInfo {

	private final Date expirationDate;
	private final boolean isExpired;
	private final byte[] licenseKey;
	private final boolean isPerpetual;
	private final boolean isTrial;
	private final boolean isFree;

	public LicenseInfo(final Date expirationDate, final boolean isExpired, final byte[] licenseKey, final boolean isPerpetual, final boolean isTrial,
			final boolean isFree) {
		this.expirationDate = expirationDate;
		this.isExpired = isExpired;
		this.licenseKey = licenseKey;
		this.isPerpetual = isPerpetual;
		this.isTrial = isTrial;
		this.isFree = isFree;
	}

	public Date getExpirationDate() {
		return this.expirationDate;
	}

	public boolean isExpired() {
		return this.isExpired;
	}

	public byte[] getLicenseKey() {
		return this.licenseKey;
	}

	public boolean isPerpetual() {
		return this.isPerpetual;
	}

	public boolean isTrial() {
		return this.isTrial;
	}

	public boolean isFree() {
		return this.isFree;
	}
}
