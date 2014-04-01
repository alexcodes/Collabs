package collabs.model.events;

/**
 * Author: Aleksey A.
 * Date: 31.03.14
 * Time: 23:35
 */
public class UnsubscribeDocumentEvent extends ServerEvent {
    private int id;

    public UnsubscribeDocumentEvent(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
