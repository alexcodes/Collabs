package collabs.model.events;

import collabs.model.data.ServerDocument;

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
        sb.append("Refresh request: ")
                .append(documents.length)
                .append(" item(s)").append(documents.length > 0 ? "\n" : "");
        for (ServerDocument doc : documents) {
            sb.append(doc.toString()).append("\n");
        }
        return sb.toString();
    }
}
