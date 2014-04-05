package collabs.model.actions;

import collabs.model.core.Manager;
import collabs.model.core.ToolbarModel;
import collabs.model.events.EventList;
import collabs.model.events.ServerDocumentEvent;
import collabs.model.events.SubscribeDocumentEvent;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.event.DocumentEvent;
import com.intellij.openapi.editor.event.DocumentListener;
import com.intellij.openapi.ui.Messages;

import javax.swing.*;

/**
 * Author: Aleksey A.
 * Date: 01.04.14
 * Time: 22:56
 */
public class BindDocumentAction extends AnAction {
    public BindDocumentAction() {
        super();
    }

    public BindDocumentAction(String text, String description, Icon icon) {
        super(text, description, icon);
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        try {
            //TODO replace by gui choice
            int id = getSelectedId();
            if (id == 0) {
                Messages.showErrorDialog("No document was selected", "Error");
                return;
            }
            Document document = e.getData(PlatformDataKeys.EDITOR).getDocument();
            if (ToolbarModel.getToolbarModel().checkBindDocument(document)) {
                Messages.showErrorDialog("This document was already binded", "Error");
                return;
            }

            document.addDocumentListener(new ExtendedDocumentListener(id) {
                @Override
                public void beforeDocumentChange(DocumentEvent event) {
                    //nothing
                }

                @Override
                public void documentChanged(DocumentEvent event) {
                    ServerDocumentEvent serverDocumentEvent = new ServerDocumentEvent(
                            getId(),
                            event.getOffset(),
                            event.getOldFragment(),
                            event.getNewFragment()
                    );
                    long timestamp = ToolbarModel.getToolbarModel().getEventList().checkDuplicate(serverDocumentEvent);
                    if (timestamp == EventList.NO_DUPLICATE) {
                        Manager.getManager().getConnection().transmit(serverDocumentEvent);
                    } else {
                        ToolbarModel.getToolbarModel().getEventList().removeEvent(timestamp, serverDocumentEvent);
                    }
                }
            });
            Manager.getManager().getConnection().transmit(new SubscribeDocumentEvent(id));
            ToolbarModel.getToolbarModel().addBindDocument(id, document);
            Messages.showInfoMessage("Document was linked to server one", "Success");
        } catch (Exception ex) {
            Messages.showErrorDialog("Try again", "Error");
        }
    }

    private int getSelectedId() {
        //String[] values = new String[]{"one", "two", "three", "four", "five"};
        //Messages.showEditableChooseDialog("Message", "Title", Messages.getInformationIcon(), values, "initialValue", null);
        return ToolbarModel.getToolbarModel().getSelectedId();
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
