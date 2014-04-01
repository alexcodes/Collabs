package collabs.model.actions;

import collabs.connection.Connection;
import collabs.connection.client.ClientConnection;
import collabs.model.core.Manager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.Messages;

import java.io.IOException;

/**
 * Author: Aleksey A.
 * Date: 16.03.14
 * Time: 0:16
 */
public class ConnectAction extends AnAction {
    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        String ip = Messages.showInputDialog(
                "Enter ip address:",
                "Connection",
                Messages.getQuestionIcon(),
                "localhost",
                new IPValidator()
        );
        String port = Messages.showInputDialog(
                "Enter port:",
                "Connection",
                Messages.getQuestionIcon(),
                "1234",
                new PortValidator()
        );

        Connection connection = connect(ip, port);
        if (connection != null) {
            Messages.showInfoMessage("Connected to " + ip + ":" + port, "Success");
            Manager.getManager().setConnection(connection);
        } else {
            Messages.showErrorDialog("Cannot connect to " + ip + ":" + port, "Error");
        }

        //anActionEvent.getActionManager().
        //ApplicationManager.getApplication().getMessageBus()
        //ProjectManager.getInstance().getOpenProjects()[0].getBaseDir().getFileSystem().findFileByPath("").move();
        //ProjectManager.getInstance().getOpenProjects()[0]
        //VirtualFileManager.getInstance().addVirtualFileListener(new SimpleVirtualFileListener());
        //ProjectManager.getInstance().getOpenProjects()[0].getBaseDir().getFileSystem().addVirtualFileListener(new SimpleVirtualFileListener());
    }

    private Connection connect(String ip, String port) {
        return connect(ip, Integer.parseInt(port));
    }

    private Connection connect(String ip, int port) {
        Connection connection = null;
        try {
            connection = new ClientConnection(ip, port);
        } catch (IOException ignored) {}
        return connection;
    }
}
