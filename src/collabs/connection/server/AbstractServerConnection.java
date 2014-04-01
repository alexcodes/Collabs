package collabs.connection.server;

import collabs.connection.Connection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Author: Aleksey A.
 * Date: 07.03.14
 * Time: 22:46
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
        serverCore.removeConnection(this);
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

    Server getServerCore() {
        return serverCore;
    }

    abstract void handleEvent(Object object);

    public String toString() {
        return socket.toString();
    }
}
