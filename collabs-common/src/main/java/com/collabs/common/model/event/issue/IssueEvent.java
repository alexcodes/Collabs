package com.collabs.common.model.event.issue;

import com.collabs.common.model.data.issue.Issue;
import com.collabs.common.model.event.Event;

/**
 * @author Aleksey A.
 */
public interface IssueEvent extends Event {
    public int getIdIssue();
    public Issue getIssue();
}
