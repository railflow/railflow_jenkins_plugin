package io.jenkins.plugins.railflow.testreport.model;

import java.util.List;

import io.jenkins.plugins.railflow.testrail.client.model.Run;

/**
 * Implementation of {@link Run}.
 *
 * @author Ayman BEN AMOR
 *
 */
public class RunImpl extends AbstractModelBase implements Run {
	private int suiteId;
	private String description;
	private List<Integer> caseIds;
	private int milestoneId;
	private String url;
	private boolean isCompleted;
	private List<Integer> configIds;

	private int planId;

	@Override
	public List<Integer> getCaseIds() {
		return this.caseIds;
	}

	@Override
	public void setCaseIds(final List<Integer> caseIds) {
		this.caseIds = caseIds;
	}

	@Override
	public String getDescription() {
		return this.description;
	}

	@Override
	public void setDescription(final String description) {
		this.description = description;
	}

	@Override
	public int getMilestoneId() {
		return this.milestoneId;
	}

	@Override
	public void setMilestoneId(final int milestoneId) {
		this.milestoneId = milestoneId;
	}

	@Override
	public int getSuiteId() {
		return this.suiteId;
	}

	@Override
	public void setSuiteId(final int suiteId) {
		this.suiteId = suiteId;
	}

	@Override
	public String getUrl() {
		return this.url;
	}

	@Override
	public void setUrl(final String url) {
		this.url = url;
	}

	@Override
	public boolean isCompleted() {
		return this.isCompleted;
	}

	@Override
	public void setIsCompleted(final boolean isCompleted) {
		this.isCompleted = isCompleted;
	}

	@Override
	public List<Integer> getConfigIds() {
		return this.configIds;
	}

	@Override
	public void setConfigIds(final List<Integer> configIds) {
		this.configIds = configIds;
	}

	@Override
	public int getPlanId() {
		return this.planId;
	}

	@Override
	public void setPlanId(final int planId) {
		this.planId = planId;
	}
}
