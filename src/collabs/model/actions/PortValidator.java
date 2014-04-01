package collabs.model.actions;

import com.intellij.openapi.ui.InputValidator;
import com.intellij.openapi.ui.Messages;

/**
 * Author: Aleksey A.
 * Date: 26.03.14
 * Time: 19:29
 */
class PortValidator implements InputValidator {
    @Override
    public boolean checkInput(String inputString) {
        return !(inputString == null || inputString.isEmpty());
    }

    @Override
    public boolean canClose(String inputString) {
        if (! checkInput(inputString)) {
            return false;
        }
        try {
            int port = Integer.parseInt(inputString);
            if (checkPort(port)) {
                return true;
            }
        } catch (Exception ignored) {}
        Messages.showErrorDialog("Invalid port.\nExample:1234", "Error");
        return false;
    }

    private boolean checkPort(int port) {
        return port > 0 && port < Short.MAX_VALUE * 2 + 2;
    }
}