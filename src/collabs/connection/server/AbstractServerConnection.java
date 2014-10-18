package collabs.connection.server;

import collabs.connection.Connection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Abstract server connection that defines
 * some standard behaviour of transmitting
 * and receiving data.
 *
 * Author: Aleksey A.
 */
abstract class AbstractServerConnection extends Thread implements Connection {
    private Server serverCore;
    private Socket socket;

    public AbstractServerConnection(Server server, Socket socket) {
        if (server == null || socket == null) {
            throw new IllegalArgumentException("Arguments of connection cannot be null");
        }
        serverCore = server;
        this.socket = socket;
    }

    @Override
    public void connect() {
        start();  //Thread method
    }

    @Override
    public void disconnect() {
        try {
            ObjectOutputStream stream = new ObjectOutputStream(socket.getOutputStream());
            stream.close();
        } catch (IOException e) {
            this.interrupt();
        }
    }

    @Override
    public void run() {
        while (! isInterrupted()) {
            handleEvent(receive());
        }
        clearConnection();
    }

    @Override
    public Object receive() {
        Object object = null;
        try {
            ObjectInputStream stream = new ObjectInputStream(socket.getInputStream());
            object = stream.readObject();
        } catch (IOException e) {
            this.interrupt();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return object;
    }

    @Override
    public synchronized void transmit(Object object) {
        try {
            ObjectOutputStream stream = new ObjectOutputStream(socket.getOutputStream());
            stream.writeObject(object);
            stream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    Server getServer() {
        return serverCore;
    }

    /**
     * Reset all tracks of this connection
     */
    void clearConnection() {
        serverCore.removeConnection(this);
        serverCore.getDocuments().removeConnectionFromDocuments(this);
    }

    /**
     * Defines what to do with received event
     * @param object received data
     */
    abstract void handleEvent(Object object);

    @Override
    public String toString() {
        return socket.toString();
    }
}
