package com.collabs.common.model.event;

/**
 * @author Aleksey A.
 */
public class BenchmarkEvent implements Event {
    private int count;

    public BenchmarkEvent(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }
}
