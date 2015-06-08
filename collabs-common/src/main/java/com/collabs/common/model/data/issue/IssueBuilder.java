package com.collabs.common.model.data.issue;

/**
 * @author Aleksey A.
 */
public class IssueBuilder {
    private Issue issue;

    public IssueBuilder() {
        issue = new Issue(System.currentTimeMillis());
    }

    public IssueBuilder name(String name) {
        issue.setName(name);
        return this;
    }

    public IssueBuilder description(String description) {
        issue.setDescription(description);
        return this;
    }

    public IssueBuilder priority(IssuePriority priority) {
        issue.setPriority(priority);
        return this;
    }

    public IssueBuilder progress(IssueProgress progress) {
        issue.setProgress(progress);
        return this;
    }

    public IssueBuilder assignee(int idClient) {
        issue.setIdClientAssignee(idClient);
        return this;
    }

    public Issue build() {
        return issue;
    }
}
