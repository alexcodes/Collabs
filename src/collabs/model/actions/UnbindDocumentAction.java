package collabs.model.actions;

import collabs.model.core.Manager;
import collabs.model.core.ToolbarModel;
import collabs.model.events.UnsubscribeDocumentEvent;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.ui.Messages;

import javax.swing.*;

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
            int id = ToolbarModel.getToolbarModel().getIdByDocument(document);
            if (id != 0) {
                Manager.getManager().getConnection().transmit(new UnsubscribeDocumentEvent(id));
                ToolbarModel.getToolbarModel().removeDocumentListener(document);
                ToolbarModel.getToolbarModel().removeBindDocument(id);
            }

            Messages.showInfoMessage("Document was unbound from server one", "Success");
        } catch (Exception ex) {
            Messages.showErrorDialog("Cannot unbind this document. Try again!", "Error");
        }
    }
}
