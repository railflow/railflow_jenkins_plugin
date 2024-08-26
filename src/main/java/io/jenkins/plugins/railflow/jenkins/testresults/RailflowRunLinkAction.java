package io.jenkins.plugins.railflow.jenkins.testresults;

import java.io.Serializable;
import java.util.Objects;

import javax.annotation.CheckForNull;

import hudson.model.Action;
import jenkins.model.Jenkins;

/**
 * Display link to a TestRail run.
 *
 * @author Sergey Oplavin
 */
public class RailflowRunLinkAction implements Action, Serializable {

	private static final String RAILFLOW_ICON_PATH = Jenkins.RESOURCE_PATH + "/plugin/railflow-jenkins-plugin/icons/railflow-48.png";

	private final String testRailRunUrl;

	public RailflowRunLinkAction(String testRailRunUrl) {
		this.testRailRunUrl = testRailRunUrl;
	}

	@CheckForNull
	@Override
	public String getIconFileName() {
		return RAILFLOW_ICON_PATH;
	}

	@CheckForNull
	@Override
	public String getDisplayName() {
		return null;
	}

	@CheckForNull
	@Override
	public String getUrlName() {
		return testRailRunUrl;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		RailflowRunLinkAction that = (RailflowRunLinkAction) o;
		return Objects.equals(testRailRunUrl, that.testRailRunUrl);
	}

	@Override
	public int hashCode() {
		return Objects.hash(testRailRunUrl);
	}
}
