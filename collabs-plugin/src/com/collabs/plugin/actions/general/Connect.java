package com.collabs.plugin.actions.general;

import com.collabs.plugin.actions.general.validator.ValidatorHost;
import com.collabs.plugin.actions.general.validator.ValidatorPort;
import com.collabs.plugin.core.Client;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.Messages;

import javax.swing.*;

/**
 * @author Aleksey A.
 */
public class Connect extends AnAction {

    public Connect() {
        super();
    }

    public Connect(String text, String description, Icon icon) {
        super(text, description, icon);
    }

    public void actionPerformed(AnActionEvent e) {
        String host = getHost();
        String port = getPort();
        //if cancelled
        if (checkEmpty(host) || checkEmpty(port)) {
            return;
        }
        Client.initClient(host, Integer.parseInt(port));
        Thread clientThread = new Thread(Client.get());
        clientThread.start();
        Messages.showInfoMessage("Connected!", "Success");
    }

    /**
     * Get host name from user interface
     * through standard IDEA dialog
     */
    private String getHost() {
        return Messages.showInputDialog(
                "Enter ip address:",
                "Connection",
                Messages.getQuestionIcon(),
                "localhost",
                new ValidatorHost());
    }

    /**
     * Get port number from user interface
     * through standard IDEA dialog
     */
    private String getPort() {
        return Messages.showInputDialog(
                "Enter port:",
                "Connection",
                Messages.getQuestionIcon(),
                "8081",
                new ValidatorPort());
    }

    private boolean checkEmpty(String s) {
        return s == null || s.isEmpty();
    }
}
