package collabs.model.events;

/**
 * Container of received events. Is needed to exclude
 * duplicates of events from sending on.
 *
 * Author: Aleksey A.
 */
public interface EventList {
    public static final long NO_DUPLICATE = 0;

    /**
     * Adds event to container. Timestamp when event is come is saved
     * @param event added
     */
    public void addEvent(ServerDocumentEvent event);

    /**
     * Removes exact copy of event
     * @param timestamp time ms
     * @param event event to be removed
     */
    public void removeEvent(long timestamp, ServerDocumentEvent event);

    /**
     * Checks whether there is duplicate in container
     * @param event event to be checked
     * @return {@code true} if there is duplicate and {@code false} otherwise
     */
    public long checkDuplicate(ServerDocumentEvent event);

    /**
     * Clean up unnecessary or outdated events
     */
    public void clearExtra();
}
