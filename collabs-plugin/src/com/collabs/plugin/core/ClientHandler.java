package com.collabs.plugin.core;

import com.collabs.common.model.data.Packet;
import com.collabs.common.model.event.document.DocumentChangedEvent;
import com.collabs.common.model.event.document.RefreshDocumentsEvent;
import com.collabs.common.model.event.document.SyncTextEvent;
import com.collabs.common.model.event.general.InitClientEvent;
import com.collabs.common.model.event.general.RefreshClientInfoEvent;
import com.collabs.common.model.event.issue.IssueEvent;
import com.collabs.common.model.event.issue.RefreshIssuesEvent;
import com.collabs.plugin.data.duplicate.DuplicateDetector;
import com.collabs.plugin.data.storage.Clients;
import com.collabs.plugin.data.storage.Documents;
import com.collabs.plugin.view.CollabsPanel;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.CommandProcessor;
import com.intellij.openapi.editor.Document;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.awt.*;

/**
 *
 *
 * @author Aleksey A.
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println("Active");
    }
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        System.out.println("Received: " + msg);
        if (msg instanceof Packet) {
            Packet packet = (Packet) msg;
            handlePacket(packet);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

    private void handlePacket(Packet packet) {
        if (packet.getEvent() instanceof DocumentChangedEvent) {
            changeDocument(packet);
            return;
        }
        if (packet.getEvent() instanceof RefreshDocumentsEvent) {
            refreshDocuments(packet);
            return;
        }
        if (packet.getEvent() instanceof SyncTextEvent) {
            syncText(packet);
            return;
        }
        if (packet.getEvent() instanceof InitClientEvent) {
            initClient(packet);
            return;
        }
        if (packet.getEvent() instanceof IssueEvent) {
            handleIssueEvent(packet);
            return;
        }
        if (packet.getEvent() instanceof RefreshClientInfoEvent) {
            refreshClientInfo(packet);
        }
    }

    private void refreshClientInfo(Packet packet) {
        RefreshClientInfoEvent event = (RefreshClientInfoEvent) packet.getEvent();
        Clients.get().set(event.getInfo());
    }

    private void handleIssueEvent(Packet packet) {
        if (packet.getEvent() instanceof RefreshIssuesEvent) {
            refreshIssues(packet);
        }
    }

    private void refreshIssues(Packet packet) {
        RefreshIssuesEvent event = (RefreshIssuesEvent) packet.getEvent();
        CollabsPanel.setIssues(event.getIssues());
    }

    private void changeDocument(Packet packet) {
        final DocumentChangedEvent event = (DocumentChangedEvent) packet.getEvent();
        final Document document = Documents.get().getDocument(event.getIdDocument());
        DuplicateDetector.get().addEvent(event);
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                CommandProcessor.getInstance().executeCommand(null, new Runnable() {
                    @Override
                    public void run() {
                        ApplicationManager.getApplication().runWriteAction(new Runnable() {
                            @Override
                            public void run() {
                                document.replaceString(
                                        event.getOffset(),
                                        event.getOffset() + event.getOldFragment().length(),
                                        event.getNewFragment());
                            }
                        });
                    }
                }, "modify", null);
            }
        });
    }

    private void initClient(Packet packet) {
        InitClientEvent event = (InitClientEvent) packet.getEvent();
        Client.get().setIdClient(event.getIdClient());
    }

    private void refreshDocuments(Packet packet) {
        RefreshDocumentsEvent event = (RefreshDocumentsEvent) packet.getEvent();
        CollabsPanel.setDocuments(event.getDocuments());
    }

    private void syncText(Packet packet) {
        final SyncTextEvent event = (SyncTextEvent) packet.getEvent();
        final Document document = Documents.get().getDocument(event.getDocument().getIdDocument());
        DocumentChangedEvent eventClear = new DocumentChangedEvent(
                document.getText(),
                "",
                0,
                event.getDocument().getIdDocument());
        Packet packetClear = new Packet(eventClear);
        changeDocument(packetClear);
        DocumentChangedEvent eventText = new DocumentChangedEvent(
                "",
                event.getDocument().getText().toString(),
                0,
                event.getDocument().getIdDocument());
        Packet packetText = new Packet(eventText);
        changeDocument(packetText);
    }
}
