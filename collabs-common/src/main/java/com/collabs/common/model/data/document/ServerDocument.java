package com.collabs.common.model.data.document;

import com.collabs.common.model.event.document.DocumentChangedEvent;
import io.netty.channel.Channel;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Aleksey A.
 */
public class ServerDocument implements Serializable {
    private final int idDocument;
    private String name;
    private String path;
    private final StringBuffer text;
    private final transient Map<Integer, ServerDocumentListener> listeners;

    public ServerDocument(ServerDocument document) {
        this(document.getIdDocument(),
                document.getName(),
                document.getPath(),
                document.getText());
    }

    public ServerDocument(int idDocument, String name, String path, CharSequence text) {
        this.idDocument = idDocument;
        this.name = name;
        this.path = path;
        this.text = new StringBuffer(text);
        listeners = new ConcurrentHashMap<Integer, ServerDocumentListener>();
    }

    public int getIdDocument() {
        return idDocument;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public CharSequence getText() {
        return text;
    }

    public Collection<ServerDocumentListener> getListeners() {
        return listeners.values();
    }

    public void addListener(ServerDocumentListener listener) {
        listeners.put(listener.getIdClient(), listener);
    }

    public void removeListener(int idClient) {
        listeners.remove(idClient);
    }

    public void handleEvent(DocumentChangedEvent event, Channel from) {
        text.replace(
                event.getOffset(),
                event.getOffset() + event.getOldFragment().length(),
                event.getNewFragment());
        redirectEvent(event, from);
    }

    private void redirectEvent(DocumentChangedEvent event, Channel from) {
        for (ServerDocumentListener listener : listeners.values()) {
            if (listener.getChannel() == from) {
                continue;
            }
            listener.invoke(event);
        }
    }

    @Override
    public String toString() {
        return name + " (" + path + ")";
    }
}
