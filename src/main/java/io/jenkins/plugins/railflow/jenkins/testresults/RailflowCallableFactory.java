package io.jenkins.plugins.railflow.jenkins.testresults;

import hudson.FilePath;
import hudson.model.TaskListener;
import io.jenkins.plugins.railflow.TestRailParameters;
import io.jenkins.plugins.railflow.UploadParameters;

import java.io.Serializable;

/**
 * Producing {@link RailflowCallable} objects.
 *
 * @author Sergey Oplavin
 * @author LiuYang
 */
public interface RailflowCallableFactory extends Serializable {

	/**
	 * Create new callable.
	 *
	 * @param licenseContent
	 *            license key or content of a license file.
	 * @param testRailParameters
	 *            TR connection parameters.
	 * @param uploadParameters
	 *            upload parameters.
	 * @param resultPattern
	 *            result file pattern to be used for searching of report files.
	 * @param runId
	 *            Jenkins run ID.
	 * @param listener
	 *            Jenkins task listener.
	 * @param workspace
	 *            workspace
	 * @param debugLogEnabled
	 *            Debug log enabled
	 * @return new {@link RailflowCallable}, implementations must not return
	 *         null.
	 */
	RailflowCallable create(String licenseContent, TestRailParameters testRailParameters, UploadParameters uploadParameters, String resultPattern,
			String runId, TaskListener listener, FilePath workspace, boolean debugLogEnabled);
}
