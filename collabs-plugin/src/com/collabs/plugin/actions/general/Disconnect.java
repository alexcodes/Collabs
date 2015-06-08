package com.collabs.plugin.actions.general;

import com.collabs.plugin.core.Client;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.Messages;

import javax.swing.*;

/**
 * @author Aleksey A.
 */
public class Disconnect extends AnAction {
    public Disconnect() {
        super();
    }

    public Disconnect(String text, String description, Icon icon) {
        super(text, description, icon);
    }

    public void actionPerformed(AnActionEvent e) {
        if (Client.isInitialized()) {
            Client.get().stop();
            Messages.showInfoMessage("Disconnected", "Success");
        }
    }
}
