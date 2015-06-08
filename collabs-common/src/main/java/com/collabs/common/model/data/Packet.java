package com.collabs.common.model.data;

import com.collabs.common.model.event.Event;
import io.netty.channel.Channel;

import java.io.Serializable;

/**
 * @author Aleksey A.
 */
public class Packet implements Serializable {
    private final int idSession;
    private final int idClient;
    private Event event;
    private transient Channel channel;

    public Packet(Event event) {
        this(0, 0, event, null);
    }

    public Packet(int idClient, Event event) {
        this(0, idClient, event, null);
    }

    public Packet(int idSession, int idClient, Event event) {
        this(idSession, idClient, event, null);
    }

    public Packet(int idSession, int idClient, Event event, Channel channel) {
        this.idSession = idSession;
        this.idClient = idClient;
        this.event = event;
        this.channel = channel;
    }

    public int getIdSession() {
        return idSession;
    }

    public int getIdClient() {
        return idClient;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    @Override
    public String toString() {
        return "Session#" + idSession + "; Client#" + idClient + "; Event[" + event + "]";
    }
}
