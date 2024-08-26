package io.jenkins.plugins.railflow.testreport.model;

/**
 * Available testreport formats to upload to TestRail.
 *
 * @author Sergey Oplavin
 * @author Liu Yang
 */
public enum ReportFormat {

	ALLURE,
	CUCUMBER,
	JUNIT,
	NUNIT,
	NUNIT_SPECFLOW,
	PYTEST_RAILFLOW,
	ROBOT,
	TESTNG,
	TESTNG_STEPS,
	TRX,
	XUNIT,
	PLAYWRIGHT;

	public static ReportFormat fromString(final String reportFormat) {
		for (final ReportFormat format : values()) {
			if (format.toString().equalsIgnoreCase(reportFormat)) {
				return format;
			}
		}
		throw new IllegalArgumentException("Could not find ReportFormat for String: " + reportFormat);
	}
}
