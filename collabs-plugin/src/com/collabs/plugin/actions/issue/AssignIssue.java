package com.collabs.plugin.actions.issue;

import com.collabs.common.model.data.Packet;
import com.collabs.common.model.data.issue.Issue;
import com.collabs.common.model.event.issue.IssueUpdateEvent;
import com.collabs.plugin.core.Client;
import com.collabs.plugin.view.CollabsPanel;
import com.collabs.plugin.view.IssuePanel;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.Messages;

import javax.swing.*;

/**
 * @author Aleksey A.
 */
public class AssignIssue extends AnAction {
    public AssignIssue() {
    }

    public AssignIssue(String text, String description, Icon icon) {
        super(text, description, icon);
    }

    public void actionPerformed(AnActionEvent e) {
        if (Client.isInitialized()) {
            Issue issue = IssuePanel.getSelectedIssue();
            if (issue != null) {
                if (issue.getIdClientAssignee() == 0) {
                    issue.setIdClientAssignee(Client.get().getIdClient());
                    IssueUpdateEvent event = new IssueUpdateEvent(issue);
                    Packet packet = new Packet(Client.get().getIdClient(), event);
                    Client.get().send(packet);
                } else {
                    Messages.showErrorDialog("Issue is already assigned", "Error");
                }
            } else {
                Messages.showErrorDialog("No issue is selected", "Error");
            }
        } else {
            Messages.showErrorDialog("Missing connection. Please, connect to server first", "Error");
        }
    }
}
