package collabs.model.actions;

import collabs.connection.server.Server;
import collabs.connection.server.ServerCore;
import collabs.model.core.Manager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.Messages;

public class StartServerAction extends AnAction {
    @Override
    public void actionPerformed(AnActionEvent e) {
        String port = Messages.showInputDialog(
                "Enter port:",
                "Connection",
                Messages.getQuestionIcon(),
                "1234",
                new PortValidator()
        );

        Server server = startServer(port);
        if (server != null) {
            Manager.getManager().setServer(server);
        }
    }

    private Server startServer(String port) {
        return startServer(Integer.parseInt(port));
    }

    private Server startServer(int port) {
        Server server = new ServerCore(port);
        new Thread(server).start();
        return server;
    }
}
