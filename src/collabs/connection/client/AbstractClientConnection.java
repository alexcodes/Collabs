package collabs.connection.client;

import collabs.connection.Connection;
import collabs.model.core.Manager;
import collabs.output.Output;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Abstract client connection that defines
 * some standard behaviour of transmitting
 * and receiving data.
 *
 * Author: Aleksey A.
 */
abstract class AbstractClientConnection extends Thread implements Connection {
    private Socket socket;

    public AbstractClientConnection(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void connect() {
        start(); //Thread method
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
        Manager.getManager().resetAll();
        Output.print("Client finished");
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

    /**
     * Defines what to do with received event
     * @param object - received object of {@code Event}
     */
    abstract void handleEvent(Object object);
}
