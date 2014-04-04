package collabs.model.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.ui.Messages;

import javax.swing.*;

/**
 * Author: Aleksey A.
 * Date: 03.04.14
 * Time: 15:14
 */
public class UnbindDocumentAction extends AnAction {
    public UnbindDocumentAction() {
        super();
    }

    public UnbindDocumentAction(String text, String description, Icon icon) {
        super(text, description, icon);
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        try {
            Document document = e.getData(PlatformDataKeys.EDITOR).getDocument();
            //document.removeDocumentListener();

            Messages.showInfoMessage("Document was unlinked from server one", "Success");
        } catch (Exception ex) {
            Messages.showErrorDialog("Cannot unbind this document. Try again!", "Error");
        }
    }
}
