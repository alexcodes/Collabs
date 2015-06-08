package com.collabs.common.model.event.document;

import com.collabs.common.model.data.document.ServerDocument;

import java.util.Collection;

/**
 * @author Aleksey A.
 */
public class RefreshDocumentsEvent implements DocumentEvent {
    private Collection<ServerDocument> documents;

    public RefreshDocumentsEvent() {
        this(null);
    }

    public RefreshDocumentsEvent(Collection<ServerDocument> documents) {
        this.documents = documents;
    }

    public Collection<ServerDocument> getDocuments() {
        return documents;
    }
}
