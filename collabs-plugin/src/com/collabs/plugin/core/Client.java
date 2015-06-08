package com.collabs.plugin.core;

import com.collabs.common.model.data.Packet;
import com.collabs.common.model.event.Event;
import com.collabs.common.model.event.document.RefreshDocumentsEvent;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

import java.util.Timer;
import java.util.TimerTask;

/**
 *
 *
 * @author Aleksey A.
 */
public class Client implements Runnable {
    private static final String DEFAULT_HOST = "localhost";
    private static final int DEFAULT_PORT = 8081;

    private static Client instance;

    private String host;
    private int port;
    private Channel channel;
    private int idSession;
    private int idClient;

    private Client() {
        this(DEFAULT_HOST, DEFAULT_PORT);
    }

    private Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public void run() {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline p = ch.pipeline();
                            p.addLast(
                                    new ObjectEncoder(),
                                    new ObjectDecoder(ClassResolvers.weakCachingResolver(Packet.class.getClassLoader())),
                                    new ClientHandler());
                        }
                    });

            channel = b.connect(host, port).sync().channel();

            //delete start
//            addTimer();
            //delete end

            channel.closeFuture().sync();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }

    /**
     * Just for testing
     */
    private void addTimer() {
        Timer timer = new Timer(true);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (Client.isInitialized()) {
                    RefreshDocumentsEvent event = new RefreshDocumentsEvent();
                    Client.get().send(new Packet(1, 1, event, null));
                }
            }
        }, 0, 1500);
    }

    public int getIdSession() {
        return idSession;
    }

    public void setIdSession(int idSession) {
        this.idSession = idSession;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public void stop() {
        channel.close();
        instance = null;
    }

    public void send(Event event) {
        Packet packet = new Packet(idSession, idClient, event, null);
        send(packet);
    }

    public void send(Packet packet) {
        channel.writeAndFlush(packet);
    }

    ////////////////////
    // Static methods //
    ////////////////////

    public static Client initClient() {
        return initClient(DEFAULT_HOST, DEFAULT_PORT);
    }

    public static Client initClient(String host, int port) {
        instance = new Client(host, port);
        return instance;
    }

    public static Client get() {
        return instance;
    }

    public static boolean isInitialized() {
        return instance != null;
    }

    /**
     * For starting from console
     */
    public static void main(String[] args) {
        runConnections(100);
    }

    private static void runConnections(int n) {
        for (int i = 0; i < n; i++) {
            Client.initClient();
            Thread clientThread = new Thread(Client.get());
            clientThread.start();
        }
    }
}
