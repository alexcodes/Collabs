package collabs.model.actions;

import collabs.connection.Connection;
import collabs.connection.client.ClientConnection;
import collabs.model.core.Manager;
import collabs.output.Output;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.Messages;

import javax.swing.*;
import java.io.IOException;

/**
 * Author: Aleksey A.
 * Date: 16.03.14
 * Time: 0:16
 */
public class ConnectAction extends AnAction {
    public ConnectAction() {
        super();
    }

    public ConnectAction(String text, String description, Icon icon) {
        super(text, description, icon);
    }

    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        String ip = Messages.showInputDialog(
                "Enter ip address:",
                "Connection",
                Messages.getQuestionIcon(),
                "localhost",
                new IPValidator()
        );
        if (checkEmpty(ip)) {
            return;
        }
        String port = Messages.showInputDialog(
                "Enter port:",
                "Connection",
                Messages.getQuestionIcon(),
                "1234",
                new PortValidator()
        );
        if (checkEmpty(port)) {
            return;
        }

        Connection connection = connect(ip, port);
        if (connection != null) {
            onConnected(connection);
            Output.console("Connected to server");
            Messages.showInfoMessage("Connected to " + ip + ":" + port, "Success");
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
            connection.connect();
        } catch (IOException ignored) {}
        return connection;
    }

    private void onConnected(Connection connection) {
        Manager.getManager().setConnection(connection);
        Manager.refresh();
    }

    private boolean checkEmpty(String s) {
        return s == null || s.isEmpty();
    }
}
