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
    void handleEvent(Object event) {
        Output.print("Received: " + event + "; from: " + this);
        if (event == null) {
            this.interrupt();
        } else {
            Container container = getServer().getDocuments();
            //register new document
            if (event instanceof RegisterDocumentEvent) {
                registerDocument((RegisterDocumentEvent) event, container);
            }
            //subscribe on document events
            if (event instanceof SubscribeDocumentEvent) {
                subscribeDocument((SubscribeDocumentEvent) event, container);
            }
            //unsubscribe from document events
            if (event instanceof UnsubscribeDocumentEvent) {
                unsubscribeDocument((UnsubscribeDocumentEvent) event, container);
            }
            //change document
            if (event instanceof ServerDocumentEvent) {
                changeDocument((ServerDocumentEvent) event, container);
            }
            //refresh document list
            if (event instanceof RefreshListEvent) {
                refreshDocumentList(container);
            }
        }
    }

    private void registerDocument(RegisterDocumentEvent event, Container container) {
        container.addDocument(event.getServerDocument());
        RefreshListEvent listEvent = refreshDocumentList(container);
        getServer().sendOn(this, listEvent);
        Output.print(container.toString());
    }

    private void subscribeDocument(SubscribeDocumentEvent event, Container container) {
        ServerDocument doc = container.getDocumentById(event.getDocumentId());
        ReplaceTextEvent replaceTextEvent = new ReplaceTextEvent(doc.getID(), doc.getText());
        transmit(replaceTextEvent);
        doc.addListener(new ServerDocumentListener(this) {
            @Override
            public void documentChanged(ServerDocumentEvent event) {
                transmit(event);
            }
        });
    }

    private void unsubscribeDocument(UnsubscribeDocumentEvent event, Container container) {
        ServerDocument doc = container.getDocumentById(event.getId());
        doc.removeListener(this);
    }

    private void changeDocument(ServerDocumentEvent event, Container container) {
        ServerDocument doc = container.getDocumentById(event.getId());
        doc.handleEvent(event, this);
    }

    private RefreshListEvent refreshDocumentList(Container container) {
        RefreshListEvent listEvent = new RefreshListEvent(container.getDocuments());
        transmit(listEvent);
        return listEvent;
    }
}
