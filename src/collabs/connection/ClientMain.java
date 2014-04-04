package collabs.connection;

import collabs.connection.client.ClientConnection;
import collabs.model.events.RefreshListEvent;

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
        client.transmit(new RefreshListEvent());
        //new RegisterDocumentEvent("text", "name", "path");
        //client.transmit(new SubscribeDocumentEvent(Manager.getManager().getSelectedId()));
        //client.transmit(new ServerDocumentEvent(1, 5, "", "a"));

        //client.transmit("Message");
        //client.transmit(123);
    }
}
