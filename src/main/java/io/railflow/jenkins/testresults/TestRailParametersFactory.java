package io.railflow.jenkins.testresults;

import hudson.util.Secret;
import io.railflow.TestRailParameters;
import io.railflow.jenkins.admin.GlobalConfig;

/**
 * Produces {@link TestRailParameters} objects.
 *
 * @author Sergey Oplavin
 */
public interface TestRailParametersFactory {
	/**
	 * Create new {@link TestRailParameters} object.
	 *
	 * @param globalConfig
	 *            global configuration.
	 * @param testRailServerName
	 *            name of a TR server.
	 * 
	 * @param userName
	 *            username
	 * @param password
	 *            password
	 * @return an instance of {@link TestRailParameters}, implementations must
	 *         never return null.
	 */
	TestRailParameters create(GlobalConfig globalConfig, String testRailServerName, String userName, Secret password);
}
