package collabs.model.actions;

import collabs.model.core.Manager;
import collabs.model.events.ServerDocumentEvent;
import collabs.model.events.SubscribeDocumentEvent;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.event.DocumentEvent;
import com.intellij.openapi.editor.event.DocumentListener;
import com.intellij.openapi.ui.Messages;

/**
 * Author: Aleksey A.
 * Date: 01.04.14
 * Time: 22:56
 */
public class AddListenerAction extends AnAction {
    @Override
    public void actionPerformed(AnActionEvent e) {
        try {
            //TODO replace by gui choice
            int id = getSelectedId();
            if (id == 0) {
                Messages.showErrorDialog("No document was selected", "Error");
                return;
            }
            Manager.getManager().getConnection().transmit(new SubscribeDocumentEvent(id));

            Document document = e.getData(PlatformDataKeys.EDITOR).getDocument();
            document.addDocumentListener(new ExtendedDocumentListener(id) {
                @Override
                public void beforeDocumentChange(DocumentEvent event) {
                    //nothing
                }

                @Override
                public void documentChanged(DocumentEvent event) {
                    Manager.getManager().getConnection().transmit(new ServerDocumentEvent(
                            getId(),
                            event.getOffset(),
                            event.getOldFragment(),
                            event.getNewFragment())
                    );
                }
            });
            Messages.showInfoMessage("Document was linked to server one", "Success");
        } catch (NullPointerException ex) {
            Messages.showErrorDialog("Try again", "Error");
        }
    }

    private int getSelectedId() {
        //String[] values = new String[]{"one", "two", "three", "four", "five"};
        //Messages.showEditableChooseDialog("Message", "Title", Messages.getInformationIcon(), values, "initialValue", null);
        return Manager.getManager().getSelectedId();
    }

    private abstract class ExtendedDocumentListener implements DocumentListener {
        private int id;

        public ExtendedDocumentListener(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }
    }
}
