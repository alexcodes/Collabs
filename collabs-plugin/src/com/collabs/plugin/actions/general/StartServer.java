package com.collabs.plugin.actions.general;

import com.collabs.server.core.Server;
import com.collabs.server.core.SimpleHandler;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.Messages;

import javax.swing.*;

/**
 * @author Aleksey A.
 */
public class StartServer extends AnAction {
    public StartServer() {
    }

    public StartServer(String text, String description, Icon icon) {
        super(text, description, icon);
    }

    public void actionPerformed(AnActionEvent e) {
        Server server = new Server(SimpleHandler.class);
        Thread thread = new Thread(server);
        thread.start();
        Messages.showInfoMessage("Server started", "Success");
    }
}
