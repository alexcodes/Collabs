package collabs.model.core;

import javax.swing.*;

/**
 * Author: Aleksey A.
 * Date: 04.04.14
 * Time: 22:53
 */
public class ToolbarModel {
    private static ToolbarModel model = new ToolbarModel();

    private JTextArea consoleView;

    public static ToolbarModel getToolbarModel() {
        return model;
    }

    public void setConsoleView(JTextArea consoleView) {
        this.consoleView = consoleView;
    }

    private void writeConsole(String message) {
        if (consoleView != null) {
            consoleView.append("\n" + message);
        }
    }

    public static synchronized void write(String message) {
        getToolbarModel().writeConsole(message);
    }
}
