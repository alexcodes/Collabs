package com.collabs.plugin.actions.general.validator;

import com.intellij.openapi.ui.InputValidator;
import com.intellij.openapi.ui.Messages;

/**
 * @author Aleksey A.
 */
public class ValidatorPort implements InputValidator {

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
        } catch (Exception exception) {
            Messages.showErrorDialog("Invalid port.\nExample:1234", "Error");
        }
        return false;
    }

    /**
     * Checks whether port number is valid or not
     * @param port port number
     * @return {@code true} if {@code 0 < port < 65536}
     */
    private boolean checkPort(int port) {
        return port > 0 && port < Short.MAX_VALUE * 2 + 2;
    }
}
