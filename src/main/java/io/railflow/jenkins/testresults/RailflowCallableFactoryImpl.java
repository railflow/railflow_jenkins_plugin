package io.railflow.jenkins.testresults;

import hudson.FilePath;
import hudson.model.TaskListener;
import io.railflow.TestRailParameters;
import io.railflow.UploadParameters;

/**
 * Default implementation of {@link RailflowCallableFactory}.
 *
 * @author Sergey Oplavin
 * @author Liu Yang
 */
public class RailflowCallableFactoryImpl implements RailflowCallableFactory {
	private static final long serialVersionUID = -1562039708426837291L;

	@Override
	public RailflowCallable create(final String licenseContent, final TestRailParameters testRailParameters, final UploadParameters uploadParameters,
			final String resultPattern, final String runId, final TaskListener listener, final FilePath workspace,
			final boolean debugLogEnabled) {
		return new RailflowCallable(licenseContent, testRailParameters, uploadParameters, resultPattern, null, listener, runId, workspace,
				debugLogEnabled);
	}
}
