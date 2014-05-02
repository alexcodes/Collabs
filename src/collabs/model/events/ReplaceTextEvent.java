package collabs.model.events;

/**
 * Author: Aleksey A.
 * Date: 22.04.14
 * Time: 10:49
 */
public class ReplaceTextEvent extends ServerEvent {
    private String text;
    private int id;

    public ReplaceTextEvent(int id, String text) {
        this.id = id;
        this.text = text;
    }

    public int getDocumentId() {
        return id;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return "Replace text in id=" + id;
    }
}
