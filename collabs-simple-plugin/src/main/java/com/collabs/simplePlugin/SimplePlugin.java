package com.collabs.simplePlugin;

import com.collabs.common.model.data.Packet;
import com.collabs.common.model.event.document.DocumentChangedEvent;
import com.collabs.common.pluggable.HttpPlugin;

/**
 * @author Aleksey A.
 */
public class SimplePlugin implements HttpPlugin {
    private static final String NAME = "simple";
    private String request;
    private int count;

    public synchronized void setRequest(String s) {
        this.request = s;
    }

    public synchronized String getResponse() {
        return "Total: " + count + " times document was changed";
    }

    public synchronized int getId() {
        return NAME.hashCode();
    }

    public synchronized String getName() {
        return NAME;
    }

    public synchronized void init() {
        count = 0;
    }

    public synchronized void invoke(Packet packet) {
        if (packet.getEvent() instanceof DocumentChangedEvent) {
            count++;
        }
    }
}
