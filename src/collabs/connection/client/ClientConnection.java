package collabs.connection.client;

import collabs.model.core.ToolbarModel;
import collabs.model.events.RefreshListEvent;
import collabs.model.events.ReplaceTextEvent;
import collabs.model.events.ServerDocumentEvent;
import collabs.output.Output;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.Document;

import java.awt.*;
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
    void handleEvent(final Object event) {
        Output.print("Received: " + event);
        Output.console("Received: " + event);
        if (event == null) {
            this.interrupt();
        } else {
            if (event instanceof RefreshListEvent) {
                refreshList((RefreshListEvent) event);
            }
            if (event instanceof ServerDocumentEvent) {
                EventQueue.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        changeDocument((ServerDocumentEvent) event);
                    }
                });
            }
            if (event instanceof ReplaceTextEvent) {
                EventQueue.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        replaceText((ReplaceTextEvent) event);
                    }
                });
            }
        }
    }

    private void refreshList(RefreshListEvent event) {
        ToolbarModel.getToolbarModel().setDocuments(event.getDocuments());
    }

    private void replaceText(final ReplaceTextEvent event) {
//        ApplicationManager.getApplication().runWriteAction(new Runnable() {
//            @Override
//            public void run() {
//                ToolbarModel.getToolbarModel().getDocumentById(event.getDocumentId()).setText(event.getText());
//            }
//        });
        Document doc = ToolbarModel.getToolbarModel().getDocumentById(event.getDocumentId());
        ServerDocumentEvent clearDocumentEvent = new ServerDocumentEvent(event.getDocumentId(), 0, doc.getText(), "");
        changeDocument(clearDocumentEvent);
        ServerDocumentEvent replaceTextEvent = new ServerDocumentEvent(event.getDocumentId(), 0, "", event.getText());
        changeDocument(replaceTextEvent);
    }

    private void changeDocument(final ServerDocumentEvent event) {
        final Document document = ToolbarModel.getToolbarModel().getDocumentById(event.getId());
        if (document == null) {
            Output.printBoth("Document is null!");
            return;
        }
        if (event.getOldFragment().isEmpty()) {
            ApplicationManager.getApplication().runWriteAction(new Runnable() {
                @Override
                public void run() {
                    insert(document, event);
                }
            });
        } else if (event.getNewFragment().isEmpty()) {
            ApplicationManager.getApplication().runWriteAction(new Runnable() {
                @Override
                public void run() {
                    backspace(document, event);
                }
            });
        }
    }

    private void insert(Document document, ServerDocumentEvent event) {
        ToolbarModel.getToolbarModel().getEventList().addEvent(event);
        document.insertString(event.getOffset(), event.getNewFragment());
    }

    private void backspace(Document document, ServerDocumentEvent event) {
        ToolbarModel.getToolbarModel().getEventList().addEvent(event);
        document.deleteString(event.getOffset(), event.getOffset() + event.getOldFragment().length());
    }
}
