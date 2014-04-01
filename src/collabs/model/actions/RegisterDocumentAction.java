package collabs.model.actions;

import collabs.model.core.Manager;
import collabs.model.events.RegisterDocumentEvent;
import collabs.model.events.ServerEvent;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;

/**
 * Author: Aleksey A.
 * Date: 28.03.14
 * Time: 20:20
 */
public class RegisterDocumentAction extends AnAction {
    @Override
    public void actionPerformed(AnActionEvent e) {
        try {
            Document document = e.getData(PlatformDataKeys.EDITOR).getDocument();
            String text = document.getText();

            VirtualFile virtualFile = e.getData(PlatformDataKeys.VIRTUAL_FILE);
            String name = virtualFile.getName();
            String path = virtualFile.getPath();

            ServerEvent shareDocumentEvent = new RegisterDocumentEvent(text, name, path);
            Manager.getManager().getConnection().transmit(shareDocumentEvent);

            Messages.showInfoMessage("Document shared", "Success");
        } catch (NullPointerException ex) {
            Messages.showErrorDialog("Try again", "Error");
        }
    }
}
