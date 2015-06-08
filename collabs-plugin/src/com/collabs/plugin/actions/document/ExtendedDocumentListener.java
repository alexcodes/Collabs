package com.collabs.plugin.actions.document;

import com.collabs.common.model.event.document.DocumentChangedEvent;
import com.collabs.plugin.core.Client;
import com.collabs.plugin.data.duplicate.DuplicateDetector;
import com.intellij.openapi.editor.event.DocumentEvent;
import com.intellij.openapi.editor.event.DocumentListener;

class ExtendedDocumentListener implements DocumentListener {
    private int idDocument;

    public ExtendedDocumentListener(int idDocument) {
        this.idDocument = idDocument;
    }

    @Override
    public void beforeDocumentChange(DocumentEvent event) {
        //nothing
    }

    @Override
    public void documentChanged(DocumentEvent event) {
        DocumentChangedEvent dEvent = new DocumentChangedEvent(
                event.getOldFragment().toString(),
                event.getNewFragment().toString(),
                event.getOffset(),
                idDocument);
        if (! DuplicateDetector.get().isDuplicate(dEvent) && Client.isInitialized()) {
            Client.get().send(dEvent);
        }
    }
}