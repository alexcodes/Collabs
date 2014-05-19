package collabs.model.data;

import collabs.connection.Connection;
import collabs.model.events.ServerDocumentEvent;

/**
 * Class that listen to document changes
 */
public abstract class ServerDocumentListener {
    private Connection connection;

    public ServerDocumentListener(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection() {
        return connection;
    }

    /**
     * Defines what to do if document was changed
     * @param event occurred
     */
    public abstract void documentChanged(ServerDocumentEvent event);
}
