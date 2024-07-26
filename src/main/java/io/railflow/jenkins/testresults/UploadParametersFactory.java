package io.railflow.jenkins.testresults;

import hudson.EnvVars;
import io.railflow.UploadParameters;

/**
 * Produces {@link UploadParameters} instances. If parameter values contain
 * Jenkins variables, they are resolved.
 *
 * @author Sergey Oplavin
 */
public interface UploadParametersFactory {
	/**
	 * Create new {@link UploadParameters}
	 *
	 * @param projectName
	 *            the name of the TestRail project.
	 * @param jobConfiguration
	 *            job configuration.
	 * @param envVars
	 *            Jenkins environment variables.
	 * @return a new {@link UploadParameters} instance, implementations must not
	 *         return null.
	 */
	UploadParameters create(String projectName, RailflowUploadJobConfiguration jobConfiguration, EnvVars envVars);
}
