package collabs.model.data;

import collabs.connection.Connection;
import collabs.model.events.ServerDocumentEvent;

import java.io.Serializable;

/**
 * Author: Aleksey A.
 * Date: 28.03.14
 * Time: 21:32
 */
public interface ServerDocument extends Serializable {
    public int getID();
    public String getText();
    public String getName();
    public String getPath();

    public void setText(String text);
    public void insert(String text, int index);
    public void backspace(int index, int count);
    public void handleEvent(ServerDocumentEvent event);

    public void addListener(ServerDocumentListener listener);
    public void removeListener(Connection connection);
    public void removeListener(ServerDocumentListener listener);
}
