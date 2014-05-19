package collabs.model.actions;

import collabs.model.core.Manager;
import collabs.model.events.RefreshListEvent;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.Messages;

import javax.swing.*;

public class RefreshListAction extends AnAction {
    public RefreshListAction() {
        super();
    }

    public RefreshListAction(String text, String description, Icon icon) {
        super(text, description, icon);
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        try {
            Manager.getManager().getConnection().transmit(new RefreshListEvent());
        } catch (Exception ex) {
            Messages.showErrorDialog("Refresh request was not sent", "Error");
        }
    }
}
