package collabs.model.actions;

import collabs.model.core.Manager;
import collabs.model.events.RefreshListEvent;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.Messages;

import javax.swing.*;

/**
 * Author: Aleksey A.
 * Date: 04.04.14
 * Time: 21:59
 */
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
            Messages.showInfoMessage("Refresh request was sent", "Success");
        } catch (Exception ex) {
            Messages.showErrorDialog("Refresh request was not sent", "Error");
        }
    }
}
