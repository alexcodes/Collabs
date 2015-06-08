package com.collabs.plugin.actions.issue;

import com.collabs.common.model.data.Packet;
import com.collabs.common.model.data.issue.Issue;
import com.collabs.common.model.event.issue.IssueUpdateEvent;
import com.collabs.plugin.actions.ActionException;
import com.collabs.plugin.core.Client;
import com.collabs.plugin.view.CollabsPanel;
import com.collabs.plugin.view.IssueEditor;
import com.collabs.plugin.view.IssuePanel;
import com.intellij.ide.projectWizard.NewProjectWizard;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.roots.ui.configuration.ModulesProvider;
import com.intellij.openapi.ui.DialogBuilder;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.Messages;
import org.jetbrains.annotations.Nullable;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import javax.swing.*;

/**
 * @author Aleksey A.
 */
public class EditIssue extends AnAction {
    public EditIssue() {
    }

    public EditIssue(@Nullable String text, @Nullable String description, @Nullable Icon icon) {
        super(text, description, icon);
    }

    public void actionPerformed(AnActionEvent e) {
        if (Client.isInitialized()) {
            try {
                updateIssue(false);
            } catch (ActionException exception) {
                Messages.showErrorDialog(exception.getMessage(), "Error");
            }
        } else {
            Messages.showErrorDialog("Missing connection. Please, connect to server first", "Error");
        }
    }

    protected void updateIssue(boolean createAction) throws ActionException {
        DialogBuilder builder = new DialogBuilder();
        IssueEditor editor;
        if (createAction) {
            builder.setTitle("Create Issue");
            editor = new IssueEditor();
        } else {
            builder.setTitle("Issue Edit");
            editor = new IssueEditor(getSelectedIssue());
        }
        builder.setCenterPanel(editor.getPanel());
        int exitCode = builder.show();
        if (exitCode == DialogWrapper.OK_EXIT_CODE) {
            if (editor.isValidIssue()) {
                Issue issue = editor.getIssue();
                IssueUpdateEvent event = new IssueUpdateEvent(issue);
                Packet packet = new Packet(Client.get().getIdClient(), event);
                Client.get().send(packet);
            } else {
                Messages.showErrorDialog("Issue name cannot be empty", "Error");
            }
        }
    }

    private Issue getSelectedIssue() {
        return IssuePanel.getSelectedIssue();
    }
}
