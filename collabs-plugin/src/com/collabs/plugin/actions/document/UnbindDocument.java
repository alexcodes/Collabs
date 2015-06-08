package com.collabs.plugin.actions.document;

import com.collabs.plugin.core.Client;
import com.collabs.plugin.data.storage.Documents;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.ui.Messages;

import javax.swing.*;

/**
 * @author Aleksey A.
 */
public class UnbindDocument extends AnAction {

    public UnbindDocument() {
    }

    public UnbindDocument(String text, String description, Icon icon) {
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
            if (Documents.get().contains(document)) {
                Documents.get().removeDocumentListener(document);
            } else {
                Messages.showErrorDialog("Document was not bind", "Error");
            }
        } else {
            Messages.showErrorDialog("Missing connection. Please, connect to server first", "Error");
        }
    }
}
