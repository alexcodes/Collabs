package collabs.connection;

import collabs.connection.client.ClientConnection;
import collabs.output.Output;

import java.io.IOException;

/**
 * Author: Aleksey A.
 * Date: 08.03.14
 * Time: 0:02
 */
public class ClientMain {
    public static void main(String[] args) throws IOException {
        Connection client = new ClientConnection("localhost", 1348);
        client.connect();
        client.transmit("Message");
        client.transmit(123);
    }
}
