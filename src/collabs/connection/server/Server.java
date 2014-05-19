package collabs.connection.server;

import collabs.connection.Connection;
import collabs.model.data.Container;

/**
 * Represents server that might be wrapped by separate thread.
 *
 * Author: Aleksey A.
 */
public interface Server extends Runnable {
    /**
     * Add connection to server. Duplicate connections
     * will be ignored
     * @param connection - added connection
     */
    public void addConnection(Connection connection);

    /**
     * Remove connection from server if server has it
     * @param connection - removed connection
     */
    public void removeConnection(Connection connection);

    /**
     * Send on data to other connections in server
     * @param from - {@code Connection} source
     * @param object - data to send
     */
    public void sendOn(Connection from, Object object);

    /**
     * Stops the server
     */
    public void stop();

    /**
     * Returns collection of documents that server holds.
     * @return - container with documents
     */
    public Container getDocuments();
}
