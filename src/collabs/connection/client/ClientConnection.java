package collabs.connection.client;

import collabs.output.Output;

import java.io.IOException;
import java.net.Socket;

/**
 * Author: Aleksey A.
 * Date: 07.03.14
 * Time: 22:44
 */
public class ClientConnection extends AbstractClientConnection {

    public ClientConnection(String host, int port) throws IOException {
        super(new Socket(host, port));
    }

    @Override
    void handleEvent(Object event) {
        Output.print("Received: " + event);
        Output.console("Received: " + event);
    }
}
