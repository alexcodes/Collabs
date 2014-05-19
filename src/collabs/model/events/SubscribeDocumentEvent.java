package collabs.model.events;

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
