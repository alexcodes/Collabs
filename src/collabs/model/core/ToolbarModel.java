package collabs.model.core;

import collabs.model.data.ServerDocument;
import collabs.model.events.EventList;
import collabs.model.events.SimpleEventList;
import com.intellij.openapi.editor.Document;
import com.intellij.ui.components.JBList;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: Aleksey A.
 * Date: 04.04.14
 * Time: 22:53
 */
public class ToolbarModel {
    private static ToolbarModel model = new ToolbarModel();

    private JBList jbList;
    private JTextArea consoleView;
    private ServerDocument[] documents;
    private Map<Integer, Document> documentMap = new HashMap<Integer, Document>();
    private EventList eventList = new SimpleEventList();

    public static ToolbarModel getToolbarModel() {
        return model;
    }

    public EventList getEventList() {
        return eventList;
    }

    public void setDocuments(ServerDocument[] docs) {
        documents = docs;
        updateView();
    }

    public void updateView() {
        if (jbList != null) {
            jbList.setListData(documents);
        }
    }

    public void setJbList(JBList jbList) {
        this.jbList = jbList;
    }

    public void setConsoleView(JTextArea consoleView) {
        this.consoleView = consoleView;
    }

    private void writeConsole(String message) {
        if (consoleView != null) {
            consoleView.append("\n" + message);
        }
    }

    public void addBindDocument(int id, Document document) {
        documentMap.put(id, document);
    }

    public void removeBindDocument(int id) {
        documentMap.remove(id);
    }

    /**
     * Checks whether document was bound.
     * @param document Document in editor
     * @return {@code true} if was bound and
     * {@code false} if was not
     */
    public boolean checkBindDocument(Document document) {
        return documentMap.containsValue(document);
    }

    public Document getDocumentById(int id) {
        return documentMap.get(id);
    }

    public int getSelectedId() {
        try {
            return documents[jbList.getSelectedIndex()].getID();
        } catch (Exception ex) {
            return 0;
        }
    }

    public static synchronized void write(String message) {
        getToolbarModel().writeConsole(message);
    }
}
