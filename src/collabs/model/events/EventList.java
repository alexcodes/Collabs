package collabs.model.events;

/**
 * Author: Aleksey A.
 * Date: 06.04.14
 * Time: 0:17
 */
public interface EventList {
    public static final long NO_DUPLICATE = 0;

    public void addEvent(ServerDocumentEvent event);
    public void removeEvent(long timestamp, ServerDocumentEvent event);
    public long checkDuplicate(ServerDocumentEvent event);
    public void clearExtra();
}
