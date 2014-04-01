package collabs.model.actions;

import com.intellij.openapi.ui.InputValidator;
import com.intellij.openapi.ui.Messages;

/**
 * Author: Aleksey A.
 * Date: 26.03.14
 * Time: 19:28
 */
class IPValidator implements InputValidator {

    @Override
    public boolean checkInput(String inputString) {
        return !(inputString == null || inputString.isEmpty());
    }

    @Override
    public boolean canClose(String inputString) {
        if (! checkInput(inputString)) {
            return false;
        }
        if (checkIP(inputString)) {
            return true;
        } else {
            Messages.showErrorDialog("Invalid IP address.\nExample: 192.168.0.1", "Error");
            return false;
        }
    }

    private boolean checkIP(String host) {
        if (host.equals("localhost")) {
            return true;
        }
        String[] numbers = host.split("\\.");
        if (numbers.length != 4) {
            return false;
        }
        for (String n : numbers) {
            try {
                int x = Integer.parseInt(n);
                if (x < 0 && x > 255) {
                    return false;
                }
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }
}