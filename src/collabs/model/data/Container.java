package collabs.model.data;

/**
 * Author: Aleksey A.
 * Date: 01.04.14
 * Time: 21:36
 */
public interface Container {
    public void addDocument(ServerDocument document);
    public void removeDocument(ServerDocument document);
    public ServerDocument getDocumentById(int id);
    //public ServerDocument getDocumentByName(String name, String path);
}
