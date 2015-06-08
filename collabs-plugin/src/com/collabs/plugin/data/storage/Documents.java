package com.collabs.plugin.data.storage;

import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.event.DocumentListener;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Aleksey A.
 */
public class Documents {
    private static final Documents instance = new Documents();

    private Map<Integer, Document> documents;
    private Map<Document, DocumentListener> listeners;

    private Documents() {
        documents = new ConcurrentHashMap<Integer, Document>();
        listeners = new ConcurrentHashMap<Document, DocumentListener>();
    }

    public void registerDocument(int id, Document document) {
        if (! documents.containsKey(id) && ! documents.containsValue(document)) {
            documents.put(id, document);
        }
    }

    public void removeDocument(int id) {
        documents.remove(id);
    }

    public Document getDocument(int id) {
        return documents.get(id);
    }

    public boolean contains(Document document) {
        return documents.containsValue(document);
    }

    public void registerDocumentListener(Document document, DocumentListener listener) {
        listeners.put(document, listener);
    }

    /**
     * Removes listener from document
     * Removes listener from map
     * Removes document from map
     * @param document target document
     */
    public void removeDocumentListener(Document document) {
        if (listeners.containsKey(document)) {
            DocumentListener listener = listeners.get(document);
            document.removeDocumentListener(listener);
            listeners.remove(document);
            int key = getKey(document);
            if (key != 0) {
                documents.remove(key);
            }
        }
    }

    private int getKey(Document document) {
        for (Integer x : documents.keySet()) {
            if (document == documents.get(x)) {
                return x;
            }
        }
        return 0;
    }

    public static Documents get() {
        return instance;
    }
}
