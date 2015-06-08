package com.collabs.server.core;

import com.collabs.common.model.data.Packet;
import com.collabs.common.model.data.document.ServerDocument;
import com.collabs.common.model.data.document.ServerDocumentListener;
import com.collabs.common.model.event.BenchmarkEvent;
import com.collabs.common.model.event.document.*;
import com.collabs.common.model.event.general.*;
import com.collabs.common.model.event.issue.IssueEvent;
import com.collabs.common.model.event.issue.IssueUpdateEvent;
import com.collabs.common.model.event.issue.IssueUpdateException;
import com.collabs.common.model.event.issue.RefreshIssuesEvent;
import com.collabs.server.data.ClientStorage;
import com.collabs.server.data.DocumentStorage;
import com.collabs.server.data.IssueStorage;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * TODO remove document listener on disconnect
 * @author Aleksey A.
 */
@SuppressWarnings("unchecked")
public abstract class AbstractHandler extends ChannelInboundHandlerAdapter {
    private static final ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    private static volatile int count;

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        channels.add(ctx.channel());
        initClient(ctx.channel());
        channelsCountChanged();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        count--;
        channelsCountChanged();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        System.out.println(msg);
        if (msg instanceof Packet) {
            Packet packet = (Packet) msg;
            packet.setChannel(ctx.channel());
            handlePacket(packet);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

    private void channelsCountChanged() {
        System.out.println("Active channels: " + channels.size());
    }

    private void initClient(Channel channel) {
        InitClientEvent event = new InitClientEvent(++count);
        Packet packet = new Packet(event);
        channel.writeAndFlush(packet);
        sendRefreshDocuments(channel);
        sendRefreshIssues(channel);
    }

    private void handlePacket(Packet packet) {
        handlePlugins(packet);
        if (packet.getEvent() instanceof DocumentEvent) {
            handleDocumentEvent(packet);
            return;
        }
        if (packet.getEvent() instanceof IssueEvent) {
            handleIssueEvent(packet);
            return;
        }
        if (packet.getEvent() instanceof GeneralEvent) {
            handleGeneralEvent(packet);
        }
    }

    protected abstract void handlePlugins(Packet packet);

    private void handleDocumentEvent(Packet packet) {
        if (packet.getEvent() instanceof RefreshDocumentsEvent) {
            sendRefreshDocuments(packet.getChannel());
            return;
        }
        if (packet.getEvent() instanceof RegisterDocumentEvent) {
            registerDocument(packet);
            return;
        }
        if (packet.getEvent() instanceof BindDocumentEvent) {
            bindToDocument(packet);
            return;
        }
        if (packet.getEvent() instanceof DocumentChangedEvent) {
            changeDocument(packet);
        }
    }

    private void handleIssueEvent(Packet packet) {
        if (packet.getEvent() instanceof IssueUpdateEvent) {
            updateIssue(packet);
            return;
        }
        if (packet.getEvent() instanceof RefreshIssuesEvent) {
            sendRefreshIssues(packet.getChannel());
        }
    }

    private void handleGeneralEvent(Packet packet) {
        if (packet.getEvent() instanceof ClientInfoEvent) {
            updateClientInfo(packet);
            sendRefreshClientInfo(channels.toArray(new Channel[channels.size()]));
            return;
        }
        if (packet.getEvent() instanceof RefreshClientInfoEvent) {
            sendRefreshClientInfo(packet.getChannel());
        }
    }

    private void sendRefreshClientInfo(Channel... channels) {
        RefreshClientInfoEvent event = new RefreshClientInfoEvent(ClientStorage.get().getMap());
        Packet packet = new Packet(event);
        sendToChannels(packet, channels);
    }

    private void updateClientInfo(Packet packet) {
        ClientInfoEvent event = (ClientInfoEvent) packet.getEvent();
        ClientStorage.get().add(event.getIdClient(), event.getName());
    }

    private void updateIssue(Packet packet) {
        IssueUpdateEvent event = (IssueUpdateEvent) packet.getEvent();
        if (IssueStorage.get().containsId(event.getIdIssue())) {
            try {
                IssueStorage.get().updateIssue(event.getIssue());
            } catch (IssueUpdateException ignored) {
                //empty
            }
        } else {
            IssueStorage.get().addItem(event.getIssue());
        }
        sendRefreshIssues(channels.toArray(new Channel[channels.size()]));
    }

    private void sendRefreshIssues(Channel... channels) {
        RefreshIssuesEvent event = new RefreshIssuesEvent(IssueStorage.get().getAll());
        Packet packet = new Packet(event);
        sendToChannels(packet, channels);
    }

    private void sendRefreshDocuments(Channel... channels) {
        RefreshDocumentsEvent event = new RefreshDocumentsEvent(DocumentStorage.get().getAll());
        Packet packet = new Packet(event);
        sendToChannels(packet, channels);
    }

    private void sendToChannels(Packet packet, Channel... channels) {
        for (Channel channel : channels) {
            channel.writeAndFlush(packet);
        }
    }

    private void registerDocument(Packet packet) {
        RegisterDocumentEvent event = (RegisterDocumentEvent) packet.getEvent();
        DocumentStorage.get().addItem(event.getDocument());
        sendRefreshDocuments(channels.toArray(new Channel[channels.size()]));
    }

    private void bindToDocument(Packet packet) {
        BindDocumentEvent event = (BindDocumentEvent) packet.getEvent();
        if (DocumentStorage.get().containsId(event.getIdDocument())) {
            ServerDocumentListener listener = new ServerDocumentListener(
                    packet.getIdSession(),
                    packet.getIdClient(),
                    packet.getChannel());
            ServerDocument document = DocumentStorage.get().get(event.getIdDocument());
            document.addListener(listener);
            SyncTextEvent eventSync = new SyncTextEvent(document);
            Packet packetSync = new Packet(eventSync);
            packet.getChannel().writeAndFlush(packetSync);
        }
    }

    private void changeDocument(Packet packet) {
        DocumentChangedEvent event = (DocumentChangedEvent) packet.getEvent();
        DocumentStorage.get().get(event.getIdDocument()).handleEvent(event, packet.getChannel());
    }

    private void handleBenchmark(Packet packet) {
        BenchmarkEvent event = (BenchmarkEvent) packet.getEvent();
        BenchmarkEvent eventBenchmark = new BenchmarkEvent(event.getCount());
        Packet packetBenchmark = new Packet(eventBenchmark);
        packet.getChannel().writeAndFlush(packetBenchmark);
    }
}
