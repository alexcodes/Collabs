package collabs.connection.server;

import collabs.connection.Connection;
import collabs.model.data.Container;
import collabs.model.data.DocumentContainer;
import collabs.output.Output;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class ServerCore implements Server {
    private static final int DEFAULT_PORT = 1348;

    private ServerSocket serverSocket;
    private Set<Connection> connections;
    private Container documents;
    private int port;
    private boolean isRun;

    public ServerCore() {
        this(DEFAULT_PORT);
    }

    public ServerCore(int port) {
        this.port = port;
        connections = new HashSet<Connection>();
        documents = new DocumentContainer();
    }

    @Override
    public synchronized void addConnection(Connection connection) {
        if (connections.add(connection)) {
            Output.print("New connection: " + connection);
            Output.print("Total connections: " + connections.size());
        }
    }

    @Override
    public synchronized void removeConnection(Connection connection) {
        if (connections.remove(connection)) {
            Output.print("Removed connection: " + connection);
            Output.print("Total connections: " + connections.size());
        }
    }

    @Override
    public synchronized void sendOn(Connection from, Object object) {
        for (Connection connection : connections) {
            if (connection != from) {
                connection.transmit(object);
            }
        }
    }

    //@Override
    public void start() {
        if (serverSocket != null || isRun) {
            return;
        }
        try {
            serverSocket = new ServerSocket(port);
            isRun = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        if (serverSocket == null || ! isRun) {
            return;
        }
        isRun = false;
        Thread.currentThread().interrupt();
        try {
            Socket socket = new Socket(serverSocket.getInetAddress(), serverSocket.getLocalPort());
            ObjectOutputStream stream = new ObjectOutputStream(socket.getOutputStream());
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Container getDocuments() {
        return documents;
    }

    @Override
    public void run() {
        start();
        Output.print("Server started at port=" + port);
        while (isRun) {
            receiveConnection(serverSocket);
        }
        Output.print("Server stopped");
    }

    private void receiveConnection(ServerSocket serverSocket) {
        try {
            Socket socket = serverSocket.accept();
            Connection connection = new SimpleServerConnection(this, socket);
            connection.connect();
            addConnection(connection);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
