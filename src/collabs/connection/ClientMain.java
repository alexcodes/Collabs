package collabs.connection;

import collabs.connection.client.ClientConnection;
import collabs.model.core.Manager;
import collabs.model.events.ServerDocumentEvent;
import collabs.model.events.SubscribeDocumentEvent;

import java.io.IOException;

/*
 *  This class is used only as client example
 */
public class ClientMain {
    public static void main(String[] args) throws IOException, InterruptedException {
        Connection client = new ClientConnection("localhost", 1348);
        client.connect();
        //client.transmit(new RefreshListEvent());
        //client.transmit(new RegisterDocumentEvent("text", "name", "path"));
        client.transmit(new SubscribeDocumentEvent(Manager.getManager().getSelectedId()));
        for (int i = 0; i < 10; i++) {
            client.transmit(new ServerDocumentEvent(1, 0, "", "NEW "));

        }
    }
}
