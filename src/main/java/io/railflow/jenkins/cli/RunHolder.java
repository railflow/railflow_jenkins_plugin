package io.railflow.jenkins.cli;

import java.util.List;

import io.railflow.testrail.client.model.Run;
import io.railflow.testreport.model.RunImpl;

public class RunHolder {

    private int id;
    private String name;
    private int suiteId;
    private String description;
    private int milestoneId;
    private List<Integer> caseIds;
    private String url;
    private boolean isCompleted;
    private List<Integer> configIds;
    private int planId;

    public RunHolder() {
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public int getSuiteId() {
        return suiteId;
    }

    public void setSuiteId(final int suiteId) {
        this.suiteId = suiteId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public int getMilestoneId() {
        return milestoneId;
    }

    public void setMilestoneId(final int milestoneId) {
        this.milestoneId = milestoneId;
    }

    public List<Integer> getCaseIds() {
        return caseIds;
    }

    public void setCaseIds(final List<Integer> caseIds) {
        this.caseIds = caseIds;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(final String url) {
        this.url = url;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(final boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public List<Integer> getConfigIds() {
        return configIds;
    }

    public void setConfigIds(final List<Integer> configIds) {
        this.configIds = configIds;
    }

    public int getPlanId() {
        return planId;
    }

    public void setPlanId(final int planId) {
        this.planId = planId;
    }

    public Run map() {
        final RunImpl result = new RunImpl();
        result.setId(id);
        result.setName(name);
        result.setSuiteId(suiteId);
        result.setDescription(description);
        result.setMilestoneId(milestoneId);
        result.setCaseIds(caseIds);
        result.setUrl(url);
        result.setIsCompleted(isCompleted);
        result.setConfigIds(configIds);
        result.setPlanId(planId);

        return result;
    }
}
