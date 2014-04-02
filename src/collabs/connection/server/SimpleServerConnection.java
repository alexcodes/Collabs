package collabs.connection.server;

import collabs.model.data.Container;
import collabs.model.data.ServerDocument;
import collabs.model.data.ServerDocumentListener;
import collabs.model.events.*;
import collabs.output.Output;

import java.net.Socket;

/**
 * Author: Aleksey A.
 * Date: 07.03.14
 * Time: 22:54
 */
public class SimpleServerConnection extends AbstractServerConnection {

    public SimpleServerConnection(Server server, Socket socket) {
        super(server, socket);
    }

    @Override
    void handleEvent(Object object) {
        Output.print("Received: " + object + "; from: " + this);
        if (object == null) {
            this.interrupt();
        } else {
            Container container = getServer().getDocuments();
            //register new document
            if (object instanceof RegisterDocumentEvent) {
                container.addDocument(((RegisterDocumentEvent) object).getServerDocument());
            }
            //subscribe on document events
            if (object instanceof SubscribeDocumentEvent) {
                ServerDocument doc = container.getDocumentById(((SubscribeDocumentEvent) object).getId());
                doc.addListener(new ServerDocumentListener(this) {
                    @Override
                    public void documentChanged(ServerDocumentEvent event) {
                        transmit(event);
                    }
                });
            }
            //unsubscribe from document events
            if (object instanceof UnsubscribeDocumentEvent) {
                ServerDocument doc = container.getDocumentById(((UnsubscribeDocumentEvent) object).getId());
                doc.removeListener(this);
            }
            //change document
            if (object instanceof ServerDocumentEvent) {
                ServerDocument doc = container.getDocumentById(((ServerDocumentEvent) object).getId());
                doc.handleEvent((ServerDocumentEvent) object, this);
            }
        }
    }
}
