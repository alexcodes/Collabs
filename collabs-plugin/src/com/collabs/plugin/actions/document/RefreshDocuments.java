package com.collabs.plugin.actions.document;

import com.collabs.common.model.data.Packet;
import com.collabs.common.model.event.Event;
import com.collabs.common.model.event.document.RefreshDocumentsEvent;
import com.collabs.plugin.core.Client;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.Messages;

import javax.swing.*;

/**
 * @author Aleksey A.
 */
public class RefreshDocuments extends AnAction {
    public RefreshDocuments() {
        super();
    }

    public RefreshDocuments(String text, String description, Icon icon) {
        super(text, description, icon);
    }

    public void actionPerformed(AnActionEvent e) {
        if (Client.isInitialized()) {
            Event event = new RefreshDocumentsEvent();
            Packet packet = new Packet(1, 1, event, null);
            Client.get().send(packet);
        } else {
            Messages.showErrorDialog("Missing connection. Please, connect to server first", "Error");
        }
    }
}
