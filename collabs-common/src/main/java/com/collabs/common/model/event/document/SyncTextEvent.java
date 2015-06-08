package com.collabs.common.model.event.document;

import com.collabs.common.model.data.document.ServerDocument;

/**
 * @author Aleksey A.
 */
public class SyncTextEvent implements DocumentEvent {
    private ServerDocument document;

    public SyncTextEvent(ServerDocument document) {
        this.document = document;
    }

    public ServerDocument getDocument() {
        return document;
    }
}
