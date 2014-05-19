package collabs.model.data;

import collabs.connection.Connection;

import java.util.HashMap;
import java.util.Map;

public class DocumentContainer implements Container {
    private Map<Integer, ServerDocument> documents = new HashMap<Integer, ServerDocument>();

    @Override
    public synchronized void addDocument(ServerDocument document) {
        documents.put(document.getID(), document);
    }

    @Override
    public synchronized void removeDocument(ServerDocument document) {
        documents.remove(document.getID());
    }

    @Override
    public void removeConnectionFromDocuments(Connection connection) {
        for (ServerDocument doc : documents.values()) {
            doc.removeListener(connection);
        }
    }

    @Override
    public ServerDocument[] getDocuments() {
        return documents.values().toArray(new ServerDocument[documents.size()]);
    }

    @Override
    public synchronized ServerDocument getDocumentById(int id) {
        return documents.get(id);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Registered documents: ").append(documents.size()).append("\n");
        for (ServerDocument sd : documents.values()) {
            sb.append(sd).append("\n");
        }
        return sb.toString();
    }
}
