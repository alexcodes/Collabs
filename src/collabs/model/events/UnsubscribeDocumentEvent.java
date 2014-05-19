package collabs.model.events;

public class UnsubscribeDocumentEvent extends ServerEvent {
    private int id;

    public UnsubscribeDocumentEvent(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
