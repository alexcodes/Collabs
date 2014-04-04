package collabs.model.data;

import collabs.connection.Connection;

/**
 * Author: Aleksey A.
 * Date: 01.04.14
 * Time: 21:36
 */
public interface Container {
    public void addDocument(ServerDocument document);
    public void removeDocument(ServerDocument document);
    public void removeConnectionFromDocuments(Connection connection);
    public ServerDocument[] getDocuments();
    public ServerDocument getDocumentById(int id);
    //public ServerDocument getDocumentByName(String name, String path);
}
