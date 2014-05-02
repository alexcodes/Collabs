package collabs.model.events;

import collabs.model.data.ServerDocument;
import collabs.model.data.SimpleServerDocument;

/**
 * Author: Aleksey A.
 * Date: 31.03.14
 * Time: 22:57
 */
public class RegisterDocumentEvent extends ServerEvent {
    private String text;
    private String name;
    private String path;

    public RegisterDocumentEvent(String text, String name, String path) {
        this.text = (text == null) ? "" : text;
        this.name = name;
        this.path = path;
    }

    public String getText() {
        return text;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public ServerDocument getServerDocument() {
        return new SimpleServerDocument(text, name, path);
    }

    @Override
    public String toString() {
        return "Register: name=\"" + name + "\"; path=\"" + path + "\"";
    }
}
