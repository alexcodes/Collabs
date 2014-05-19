package collabs.model.data;

import collabs.connection.Connection;
import collabs.model.events.ServerDocumentEvent;
import collabs.output.Output;

import java.util.ArrayList;
import java.util.List;

public class SimpleServerDocument implements ServerDocument {
    /**
     * Total number of documents, is used as ID.
     */
    private static volatile int count;

    private final int id;
    private String text;
    private String name;
    private String path;
    private transient List<ServerDocumentListener> listeners = new ArrayList<ServerDocumentListener>();

    public SimpleServerDocument() {
        this("");
    }

    public SimpleServerDocument(String text) {
        this(text, "", "");
    }

    public SimpleServerDocument(String text, String name, String path) {
        if (text == null || name == null || path == null) {
            throw new IllegalArgumentException("Arguments cannot be null in Document");
        }
        this.text = text;
        this.name = name;
        this.path = path;
        id = ++count;

        Output.print("New doc: " + this + "\n" + text + "\n");
    }

    @Override
    public synchronized void setText(String newText) {
        text = newText;
        //few
    }

    @Override
    public int getID() {
        return id;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public synchronized void insert(String textInsert, int index) {
        text = text.substring(0, index)
                + textInsert
                + text.substring(index, text.length());
    }

    @Override
    public synchronized void backspace(int index, int count) {
        text = text.substring(0, index)
                + text.substring(index + count, text.length());
    }

    @Override
    public synchronized void handleEvent(ServerDocumentEvent event, Connection from) {
        if (event.getOldFragment().isEmpty()) {
            insert(event.getNewFragment(), event.getOffset());
        } else if (event.getNewFragment().isEmpty()) {
            backspace(event.getOffset(), event.getOldFragment().length());
        }
        sendToListeners(event, from);
    }

    @Override
    public synchronized void addListener(ServerDocumentListener listener) {
        listeners.add(listener);
    }

    @Override
    public synchronized void removeListener(Connection connection) {
        ServerDocumentListener serverDocumentListener = null;
        for (ServerDocumentListener listener : listeners) {
            if (listener.getConnection() == connection) {
                serverDocumentListener = listener;
                break;
            }
        }
        if (serverDocumentListener != null) {
            removeListener(serverDocumentListener);
        }
    }

    @Override
    public synchronized void removeListener(ServerDocumentListener listener) {
        listeners.remove(listener);
    }

    /**
     * Simply sends on events to listeners
     * @param event Event
     */
    private void sendToListeners(ServerDocumentEvent event, Connection from) {
        for (ServerDocumentListener listener : listeners) {
            if (listener.getConnection() != from) {
                listener.documentChanged(event);
            }
        }
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return id + "." + name + "(" + path + ")";
    }
}
