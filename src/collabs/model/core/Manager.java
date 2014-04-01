package collabs.model.core;

import collabs.connection.Connection;
import collabs.connection.server.Server;

/**
 * Author: Aleksey A.
 * Date: 26.03.14
 * Time: 20:12
 */
public class Manager {
    private static final Manager manager = new Manager();

    private Server server;
    private Connection connection;

    private Manager() {}

    public static Manager getManager() {
        return manager;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection() {
        return connection;
    }

    public void resetConnection() {
        if (connection != null) {
            connection.disconnect();
            connection = null;
        }
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public Server getServer() {
        return server;
    }

    public void resetServer() {
        if (server != null) {
            server.stop();
            server = null;
        }
    }

    public int getSelectedId() {
        return 1;
    }
}
