package com.collabs.plugin.actions.document;

import com.collabs.common.model.data.Packet;
import com.collabs.common.model.event.document.BindDocumentEvent;
import com.collabs.plugin.actions.ActionException;
import com.collabs.plugin.core.Client;
import com.collabs.plugin.data.storage.Documents;
import com.collabs.plugin.view.CollabsPanel;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.event.DocumentListener;
import com.intellij.openapi.ui.Messages;

import javax.swing.*;

/**
 * @author Aleksey A.
 */
public class BindDocument extends AnAction {
    public BindDocument() {
        super();
    }

    public BindDocument(String text, String description, Icon icon) {
        super(text, description, icon);
    }

    @SuppressWarnings("ConstantConditions")
    public void actionPerformed(AnActionEvent e) {
        if (Client.isInitialized()) {
            Document document = e.getData(PlatformDataKeys.EDITOR).getDocument();
            if (document == null) {
                Messages.showErrorDialog("No document is selected", "Error");
                return;
            }
            try {
                int idDocument = getSelectedDocumentId();
                bindDocument(document, idDocument);
                Messages.showInfoMessage("Document bind", "Success");
            } catch (ActionException exception) {
                Messages.showErrorDialog(exception.getMessage(), "Error");
            }
        } else {
            Messages.showErrorDialog("Missing connection. Please, connect to server first", "Error");
        }
    }

    /**
     * Binds document and id.
     * Adds listener to the document.
     * Sends info to server
     * @param document document
     * @param id id
     */
    protected void bindDocument(Document document, int id) throws ActionException {
        if (Documents.get().contains(document)) {
            throw new ActionException("Document already bind");
        }
        DocumentListener listener = new ExtendedDocumentListener(id);
        document.addDocumentListener(listener);
        Documents.get().registerDocument(id, document);
        Documents.get().registerDocumentListener(document, listener);
        BindDocumentEvent event = new BindDocumentEvent(id);
        Packet packet = new Packet(Client.get().getIdClient(), event);
        Client.get().send(packet);
    }

    private int getSelectedDocumentId() throws ActionException {
        return CollabsPanel.getSelectedId();
    }
}
