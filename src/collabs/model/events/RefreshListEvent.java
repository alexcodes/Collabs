package collabs.model.events;

import collabs.model.data.ServerDocument;

/**
 * Author: Aleksey A.
 * Date: 04.04.14
 * Time: 21:37
 */
public class RefreshListEvent extends ServerEvent {
    private ServerDocument[] documents;

    public RefreshListEvent() {
        this(new ServerDocument[0]);
    }

    public RefreshListEvent(ServerDocument[] docs) {
        if (docs == null) {
            throw new IllegalArgumentException("Document array cannot be null!");
        }
        documents = docs;
    }

    public ServerDocument[] getDocuments() {
        return documents;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Refresh request").append("\n");
        for (ServerDocument doc : documents) {
            sb.append(doc.toString()).append("\n");
        }
        return sb.toString();
    }
}
