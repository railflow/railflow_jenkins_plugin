package io.railflow.testrail.client.model;

import java.util.List;

/**
 * Represents a single run in TestRail.
 *
 * @author Sergey Oplavin
 *
 */
public interface Run extends HasName {

	/**
	 * @return suite_id.
	 */
	int getSuiteId();

	/**
	 * Set suite_id
	 *
	 * @param suiteId
	 *            value.
	 */
	void setSuiteId(int suiteId);

	/**
	 * @return description.
	 */
	String getDescription();

	/**
	 * Set description.
	 *
	 * @param description
	 *            description.
	 */
	void setDescription(String description);

	/**
	 * @return milestone id.
	 */
	int getMilestoneId();

	/**
	 * @param milestoneId
	 *            milestone id.
	 */
	void setMilestoneId(int milestoneId);

	/**
	 * @return list of case ids.
	 */
	List<Integer> getCaseIds();

	/**
	 * Set list of case ids.
	 *
	 * @param caseIds
	 *            case ids.
	 */
	void setCaseIds(List<Integer> caseIds);

	/**
	 * @return url of this run.
	 */
	String getUrl();

	/**
	 * @param url
	 *            run URL.
	 */
	void setUrl(String url);

	/**
	 * @return <code>true</code> if the test run was closed and
	 *         <code>false</code> otherwise.
	 */
	boolean isCompleted();

	/**
	 * @param isCompleted
	 *            if is completed.
	 */
	void setIsCompleted(boolean isCompleted);

	/**
	 * @return a list of configuration IDs.
	 */
	List<Integer> getConfigIds();

	/**
	 * @param configIds
	 *            a list of configuration IDs.
	 */
	void setConfigIds(List<Integer> configIds);

	/**
	 * @return plan id.
	 */
	int getPlanId();

	/**
	 * @param planId
	 *            plan id.
	 */
	void setPlanId(int planId);

	@Override
	default ObjectType getType() {
		return ObjectType.RUN;
	}

}
