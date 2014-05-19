package collabs.model.actions;

import collabs.model.core.Manager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.Messages;

public class StopServerAction extends AnAction {
    @Override
    public void actionPerformed(AnActionEvent e) {
        Manager.getManager().resetServer();
        Messages.showInfoMessage("Server stopped", "Success");
    }
}
