package com.collabs.plugin.actions.general;

import com.collabs.common.model.data.Packet;
import com.collabs.common.model.event.general.ClientInfoEvent;
import com.collabs.plugin.core.Client;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.Messages;

/**
 * @author Aleksey A.
 */
public class SetClientInfo extends AnAction {
    public void actionPerformed(AnActionEvent e) {
        if (Client.isInitialized()) {
            String name = Messages.showInputDialog("Enter username", "User Info", null);
            if (name != null && ! name.isEmpty()) {
                ClientInfoEvent event = new ClientInfoEvent(Client.get().getIdClient(), name);
                Packet packet = new Packet(Client.get().getIdClient(), event);
                Client.get().send(packet);
            } else {
                Messages.showErrorDialog("Name cannot be empty", "Error");
            }
        } else {
            Messages.showErrorDialog("Missing connection. Please, connect to server first", "Error");
        }
    }
}
