package com.collabs.common.model.event.issue;

import com.collabs.common.model.data.issue.Issue;

import java.util.Collection;

/**
 * @author Aleksey A.
 */
public class RefreshIssuesEvent implements IssueEvent {
    private Collection<Issue> issues;

    public RefreshIssuesEvent(Collection<Issue> issues) {
        this.issues = issues;
    }

    public Collection<Issue> getIssues() {
        return issues;
    }

    @Override
    public int getIdIssue() {
        return 0;
    }

    @Override
    public Issue getIssue() {
        return null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Issues:").append("\n");
        for (Issue issue : issues) {
            sb.append(issue.toString()).append("\n");
        }
        return sb.toString();
    }
}
