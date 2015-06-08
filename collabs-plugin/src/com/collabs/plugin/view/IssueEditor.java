package com.collabs.plugin.view;

import com.collabs.common.model.data.issue.Issue;
import com.collabs.common.model.data.issue.IssueBuilder;
import com.collabs.common.model.data.issue.IssuePriority;
import com.collabs.common.model.data.issue.IssueProgress;
import com.collabs.plugin.actions.ActionException;
import com.collabs.plugin.data.storage.Clients;

import javax.swing.*;

/**
 * @author Aleksey A.
 */
@SuppressWarnings("unchecked")
public class IssueEditor {
    private JPanel panel;
    private JTextField textName;
    private JTextField textDescription;
    private JComboBox comboPriority;
    private JComboBox comboProgress;
    private JTextField textAssignee;
    private Issue issue;

    public IssueEditor() {
        issue = new IssueBuilder().build();
        init();
    }

    public IssueEditor(Issue issue) throws ActionException {
        if (issue == null) {
            throw new ActionException("No issue selected");
        }
        this.issue = issue;
        init();
        bindToForm();
    }

    private void init() {
        comboPriority.addItem(IssuePriority.MINOR);
        comboPriority.addItem(IssuePriority.NORMAL);
        comboPriority.addItem(IssuePriority.CRITICAL);
        comboPriority.setSelectedItem(IssuePriority.NORMAL);

        comboProgress.addItem(IssueProgress.OPEN);
        comboProgress.addItem(IssueProgress.IN_PROGRESS);
        comboProgress.addItem(IssueProgress.CLOSED);
        comboProgress.setSelectedItem(IssueProgress.OPEN);
    }

    private void bindToForm() {
        textName.setText(issue.getName());
        textDescription.setText(issue.getDescription());
        comboPriority.setSelectedItem(issue.getPriority());
        comboProgress.setSelectedItem(issue.getProgress());
        textAssignee.setText(convertAssigneeToText());
    }

    private void bindToIssue() {
        issue.setName(textName.getText());
        issue.setDescription(textDescription.getText());
        issue.setPriority((IssuePriority) comboPriority.getSelectedItem());
        issue.setProgress((IssueProgress) comboProgress.getSelectedItem());
        issue.setIdClientAssignee(convertAssigneeFromText());
    }

    private int convertAssigneeFromText() {
        return issue.getIdClientAssignee();
    }

    private String convertAssigneeToText() {
        if (issue.getIdClientAssignee() == 0) {
            return  "none";
        } else if (Clients.get().contains(issue.getIdClientAssignee())) {
            return Clients.get().getName(issue.getIdClientAssignee());
        }
        return Integer.toString(issue.getIdClientAssignee());
    }

    public boolean isValidIssue() {
        return !(textName.getText() == null || textName.getText().isEmpty());
    }

    public Issue getIssue() {
        bindToIssue();
        return issue;
    }

    public JPanel getPanel() {
        return panel;
    }
}
