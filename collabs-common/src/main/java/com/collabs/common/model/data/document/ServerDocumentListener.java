package com.collabs.common.model.data.document;

import com.collabs.common.model.data.Packet;
import com.collabs.common.model.event.document.DocumentChangedEvent;
import io.netty.channel.Channel;

/**
 * @author Aleksey A.
 */
public class ServerDocumentListener {
    private int idSession;
    private int idClient;
    private Channel channel;

    public ServerDocumentListener(int idSession, int idClient, Channel channel) {
        this.idSession = idSession;
        this.idClient = idClient;
        this.channel = channel;
    }

    public int getIdSession() {
        return idSession;
    }

    public int getIdClient() {
        return idClient;
    }

    public Channel getChannel() {
        return channel;
    }

    public void invoke(DocumentChangedEvent event) {
        Packet packet = new Packet(idSession, idClient, event, null);
        channel.writeAndFlush(packet);
        if (channel.isActive()) {
        }
    }
}
