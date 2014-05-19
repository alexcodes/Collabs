package collabs.model.data;

import collabs.connection.Connection;

/**
 * Document container. Defines some basic operations
 * with collection of documents.
 *
 * Author: Aleksey A.
 */
public interface Container {
    /**
     * Adds document to container. Duplicate will replace older one.
     * @param document - new document
     */
    public void addDocument(ServerDocument document);

    /**
     * Removes document from container if it contains it.
     * @param document - removed document
     */
    public void removeDocument(ServerDocument document);

    /**
     * Removes connection from all document in container.
     * @param connection - connection that abandon subscription
     */
    public void removeConnectionFromDocuments(Connection connection);

    /**
     * Returns array of documents
     * @return document array
     */
    public ServerDocument[] getDocuments();

    /**
     * Returns document by its id
     * @param id of document
     * @return document
     */
    public ServerDocument getDocumentById(int id);
}
