package com.collabs.common.model.event.document;

/**
 * @author Aleksey A.
 */
public class BindDocumentEvent implements DocumentEvent {
    private int idDocument;

    public BindDocumentEvent(int idDocument) {
        this.idDocument = idDocument;
    }

    public int getIdDocument() {
        return idDocument;
    }
}
