package com.collabs.plugin.data.duplicate;


import com.collabs.common.model.event.document.DocumentChangedEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Aleksey A.
 */
public class DuplicateDetector {
    private static final long MAX_INTERVAL = 200;
    private static final int MAX_LIST_SIZE = 10;
    private static final DuplicateDetector detector = new DuplicateDetector();

    private List<Pair> events;

    private DuplicateDetector() {
        events = Collections.synchronizedList(new ArrayList<Pair>());
    }

    public void addEvent(DocumentChangedEvent event) {
        if (events.size() > MAX_LIST_SIZE) {
            clearOutdated();
        }
        events.add(new Pair(System.currentTimeMillis(), event));
    }

    /**
     * Checks that output event is original,
     * not received from outside
     *
     * @param event to check for duplicate
     * @return {@code true} if it is a duplicate
     */
    public boolean isDuplicate(DocumentChangedEvent event) {
        for (int i = events.size() - 1; i >= 0; i--) {
            if (event.equals(events.get(i).getEvent())
                    && System.currentTimeMillis() - events.get(i).getTime() < MAX_INTERVAL) {
                return true;
            }
        }
        return false;
    }

    private void clearOutdated() {
        int index = 0;
        for (int i = events.size() - 1; i >=0; i--) {
            if (System.currentTimeMillis() - events.get(i).getTime() > MAX_INTERVAL) {
                index = i;
            }
        }
        events = events.subList(index, events.size());
    }

    private class Pair {
        private long time;
        private DocumentChangedEvent event;

        public Pair(long time, DocumentChangedEvent event) {
            this.time = time;
            this.event = event;
        }

        public long getTime() {
            return time;
        }

        public DocumentChangedEvent getEvent() {
            return event;
        }
    }

    public static DuplicateDetector get() {
        return detector;
    }
}
