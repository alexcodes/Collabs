package com.collabs.common.model.event.document;

import com.collabs.common.model.data.document.ServerDocument;

/**
 * @author Aleksey A.
 */
public class RegisterDocumentEvent implements DocumentEvent {
    private ServerDocument document;

    public RegisterDocumentEvent(ServerDocument document) {
        this.document = document;
    }

    public ServerDocument getDocument() {
        return document;
    }
}
