package collabs.model.data;

import collabs.connection.Connection;
import collabs.model.events.ServerDocumentEvent;

/**
 * Author: Aleksey A.
 * Date: 28.03.14
 * Time: 21:55
 */
public abstract class ServerDocumentListener {
    private Connection connection;

    public ServerDocumentListener(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection() {
        return connection;
    }

    public abstract void documentChanged(ServerDocumentEvent event);
}
