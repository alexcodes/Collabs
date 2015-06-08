package com.collabs.plugin.actions.issue;

import com.collabs.plugin.actions.ActionException;
import com.collabs.plugin.core.Client;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.Messages;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * @author Aleksey A.
 */
public class CreateIssue extends AnAction {
    public CreateIssue() {
    }

    public CreateIssue(@Nullable String text, @Nullable String description, @Nullable Icon icon) {
        super(text, description, icon);
    }

    public void actionPerformed(AnActionEvent e) {
        if (Client.isInitialized()) {
            try {
                new EditIssue().updateIssue(true);
            } catch (ActionException exception) {
                Messages.showErrorDialog(exception.getMessage(), "Error");
            }
        } else {
            Messages.showErrorDialog("Missing connection. Please, connect to server first", "Error");
        }
    }
}