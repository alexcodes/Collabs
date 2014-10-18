package collabs.model.data;

import collabs.connection.Connection;
import collabs.model.events.ServerDocumentEvent;

import java.io.Serializable;

/**
 * Represents documents on server side
 *
 * Author: Aleksey A.
 */
public interface ServerDocument extends Serializable {
    public int getID();
    public String getText();
    public String getName();
    public String getPath();

    public void setText(String text);
    public void insert(String text, int index);
    public void backspace(int index, int count);
    public void handleEvent(ServerDocumentEvent event, Connection from);

    public void addListener(ServerDocumentListener listener);
    public void removeListener(Connection connection);
    public void removeListener(ServerDocumentListener listener);
}
