package com.collabs.common.model.data.issue;

import com.collabs.common.model.event.issue.IssueUpdateException;

import java.io.Serializable;

/**
 * @author Aleksey A.
 */
public class Issue implements Serializable {
    private int idIssue;
    private String name;
    private String description;
    private IssuePriority priority;
    private IssueProgress progress;
    private int idClientAssignee;
    private long lastModificationTime;

    public Issue(long timestamp) {
        this.lastModificationTime = timestamp;
        progress = IssueProgress.OPEN;
        priority = IssuePriority.NORMAL;
    }

    public int getIdIssue() {
        return idIssue;
    }

    public void setIdIssue(int idIssue) {
        this.idIssue = idIssue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public IssuePriority getPriority() {
        return priority;
    }

    public void setPriority(IssuePriority priority) {
        this.priority = priority;
    }

    public IssueProgress getProgress() {
        return progress;
    }

    public void setProgress(IssueProgress progress) {
        this.progress = progress;
    }

    public int getIdClientAssignee() {
        return idClientAssignee;
    }

    public void setIdClientAssignee(int idClientAssignee) {
        this.idClientAssignee = idClientAssignee;
    }

    public long getLastModificationTime() {
        return lastModificationTime;
    }

    public void setLastModificationTime(long lastModificationTime) {
        this.lastModificationTime = lastModificationTime;
    }

    public void update(Issue issue) throws IssueUpdateException {
        if (issue.getLastModificationTime() < lastModificationTime) {
            throw new IssueUpdateException("Server issue is newer than proposed issue");
        }
        name = issue.getName();
        description = issue.getDescription();
        priority = issue.getPriority();
        progress = issue.getProgress();
        idClientAssignee = issue.getIdClientAssignee();
        lastModificationTime = issue.getLastModificationTime();
    }

    @Override
    public String toString() {
        return "#" + idIssue + " " + name;
    }
}
