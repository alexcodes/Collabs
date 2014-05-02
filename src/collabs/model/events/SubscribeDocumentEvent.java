package collabs.model.events;

/**
 * Author: Aleksey A.
 * Date: 31.03.14
 * Time: 23:23
 */
public class SubscribeDocumentEvent extends ServerEvent {
    private int id;

    public SubscribeDocumentEvent(int id) {
        this.id = id;
    }

    public int getDocumentId() {
        return id;
    }

    @Override
    public String toString() {
        return "Subscribe on id=" + id;
    }
}
