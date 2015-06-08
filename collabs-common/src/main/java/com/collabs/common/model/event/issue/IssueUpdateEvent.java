package com.collabs.common.model.event.issue;

import com.collabs.common.model.data.issue.Issue;

/**
 * @author Aleksey A.
 */
public class IssueUpdateEvent implements IssueEvent {
    private Issue issue;

    public IssueUpdateEvent(Issue issue) {
        this.issue = issue;
    }

    @Override
    public int getIdIssue() {
        return issue.getIdIssue();
    }

    public Issue getIssue() {
        return issue;
    }

    @Override
    public String toString() {
        return "IssueUpdateEvent[" + issue.toString() + "]";
    }
}
