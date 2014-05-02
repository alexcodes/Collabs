package collabs.model.core;

import collabs.connection.Connection;
import collabs.connection.server.Server;
import collabs.model.events.RefreshListEvent;

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

    public static void refresh() {
        manager.refreshAll();
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection() {
        return connection;
    }

    public void resetAll() {
        resetConnection();
    }

    public void resetConnection() {
        if (connection != null) {
            connection.disconnect();
            connection = null;
        }
    }

    public boolean isConnected() {
        return connection != null;
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

    public void refreshAll() {
        connection.transmit(new RefreshListEvent());
    }

    public int getSelectedId() {
        return 1;
    }
}
