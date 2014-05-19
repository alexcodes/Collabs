package collabs.model.actions;

import collabs.model.core.Manager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.Messages;

import javax.swing.*;

public class DisconnectAction extends AnAction {
    public DisconnectAction() {
        super();
    }

    public DisconnectAction(String text, String description, Icon icon) {
        super(text, description, icon);
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        Manager.getManager().resetConnection();
        Messages.showInfoMessage("Disconnected", "Success");
    }
}
