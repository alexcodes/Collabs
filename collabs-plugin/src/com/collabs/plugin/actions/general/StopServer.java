package com.collabs.plugin.actions.general;

import com.collabs.server.core.Server;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.Messages;

import javax.swing.*;

/**
 * @author Aleksey A.
 */
public class StopServer extends AnAction {
    public StopServer() {
    }

    public StopServer(String text, String description, Icon icon) {
        super(text, description, icon);
    }

    public void actionPerformed(AnActionEvent e) {
        if (isServerStarted()) {
            //stop server
        } else {
            Messages.showErrorDialog("Server is not running", "Error");
        }
    }

    private boolean isServerStarted() {
        return true;
    }
}
