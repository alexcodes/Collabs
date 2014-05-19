package collabs.model.events;

public class ServerDocumentEvent extends ServerEvent {
    private int id;
    private String oldFragment;
    private String newFragment;
    private int offset;

    public ServerDocumentEvent(int id, int offset, CharSequence oldFragment, CharSequence newFragment) {
        this.id = id;
        this.offset = offset;
        this.oldFragment = (String) oldFragment;
        this.newFragment = (String) newFragment;
    }

    public int getId() {
        return id;
    }

    public String getOldFragment() {
        return oldFragment;
    }

    public String getNewFragment() {
        return newFragment;
    }

    public int getOffset() {
        return offset;
    }

    public boolean equals(ServerDocumentEvent event) {
        return id == event.getId()
                && oldFragment.equals(event.getOldFragment())
                && newFragment.equals(event.getNewFragment());
    }

    /**
     * Example: id=12[offset=3; old="f"; new=""]
     * @return info
     */
    @Override
    public String toString() {
        return "id=" + id + "[\"offset=" + offset + "; old=\"" + oldFragment + "\"; new=\"" + newFragment + "\"]";
    }
}
