package collabs.model.actions;

import collabs.model.core.Manager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.Messages;

/**
 * Author: Aleksey A.
 * Date: 27.03.14
 * Time: 17:16
 */
public class StopServerAction extends AnAction {
    @Override
    public void actionPerformed(AnActionEvent e) {
        Manager.getManager().resetServer();
        Messages.showInfoMessage("Server stopped", "Success");
    }
}
