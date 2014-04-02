package collabs.model.data;

import collabs.connection.Connection;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: Aleksey A.
 * Date: 01.04.14
 * Time: 21:39
 */
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
    public synchronized ServerDocument getDocumentById(int id) {
        return documents.get(id);
    }
}
