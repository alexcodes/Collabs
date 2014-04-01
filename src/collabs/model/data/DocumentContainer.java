package collabs.model.data;

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
    public synchronized ServerDocument getDocumentById(int id) {
        return documents.get(id);
    }
}
