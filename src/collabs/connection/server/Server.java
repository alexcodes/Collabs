package collabs.connection.server;

import collabs.connection.Connection;
import collabs.model.data.Container;

/**
 * Author: Aleksey A.
 * Date: 07.03.14
 * Time: 17:24
 */
public interface Server extends Runnable {
    public void addConnection(Connection connection);
    public void removeConnection(Connection connection);
    public void sendOn(Connection from, Object object);
    //public void start();
    public void stop();

    public Container getDocuments();
}
