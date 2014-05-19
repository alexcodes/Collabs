package collabs.model.events;

import java.util.ArrayList;
import java.util.List;

/**
 * Simple realization of {@code EventList}.
 * Recognise duplicates if there is exact copy
 * of {@code ServerDocumentEvent} occurred defined
 * milliseconds ago.
 *
 * Author: Aleksey A.
 */
public class SimpleEventList implements EventList {
    /**
     * Time elapsed before event is recognised unique
     */
    private static final long TIME_ELAPSED_MS = 200;

    private List<Pair> events;

    public SimpleEventList() {
        events = new ArrayList<Pair>(16);
    }

    @Override
    public synchronized void addEvent(ServerDocumentEvent event) {
        if (events.size() >= 10) {
            clearExtra();
        }
        events.add(new Pair(System.currentTimeMillis(), event));
    }

    @Override
    public synchronized void removeEvent(long timestamp, ServerDocumentEvent event) {
        for (int i = events.size() - 1; i >=0; i--) {
            if (events.get(i).event.equals(event)
                    && events.get(i).timestamp == timestamp) {
                events.remove(events.get(i));
                break;
            }
        }
    }

    @Override
    public synchronized long checkDuplicate(ServerDocumentEvent event) {
        for (int i = events.size() - 1; i >=0; i--) {
            if (events.get(i).event.equals(event)
                    && System.currentTimeMillis() - events.get(i).timestamp < TIME_ELAPSED_MS) {
                return events.get(i).timestamp;
            }
        }
        return 0;
    }

    @Override
    public synchronized void clearExtra() {
        int index = 0;
        for (int i = events.size() - 1; i >=0; i--) {
            if (System.currentTimeMillis() - events.get(i).timestamp > TIME_ELAPSED_MS) {
                index = i;
            }
        }
        events = events.subList(index, events.size());
    }

    /**
     * Helper class to hold info about both event and timestamp
     */
    private class Pair {
        private long timestamp;
        private ServerDocumentEvent event;

        public Pair(long timestamp, ServerDocumentEvent event) {
            this.timestamp = timestamp;
            this.event = event;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public ServerDocumentEvent getEvent() {
            return event;
        }
    }
}
