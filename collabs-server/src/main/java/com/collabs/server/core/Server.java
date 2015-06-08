package com.collabs.server.core;

import com.collabs.common.model.data.Packet;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

/**
 * @author Aleksey A.
 */
public class Server implements Runnable {
    public static final int DEFAULT_PORT = 8081;
    public static final int DEFAULT_BOSS_THREAD_COUNT = 1;
    public static final int DEFAULT_WORK_THREAD_COUNT = 2;

    private final int port;
    private final int bossCount;
    private final int workerCount;
    private final Class<? extends ChannelHandler> handlerClass;

    public Server(Class<? extends ChannelHandler> handlerClass) {
        this(DEFAULT_PORT, handlerClass);
    }

    public Server(int port, Class<? extends ChannelHandler> handlerClass) {
        this(port, DEFAULT_BOSS_THREAD_COUNT, DEFAULT_WORK_THREAD_COUNT, handlerClass);
    }

    public Server(int port, int bossCount, int workerCount, Class<? extends ChannelHandler> handlerClass) {
        this.port = port;
        this.bossCount = bossCount;
        this.workerCount = workerCount;
        this.handlerClass = handlerClass;
    }

    @Override
    public void run() {
        EventLoopGroup bossGroup = new NioEventLoopGroup(bossCount);
        EventLoopGroup workerGroup = new NioEventLoopGroup(workerCount);
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        public void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(
                                    new ObjectEncoder(),
                                    new ObjectDecoder(ClassResolvers.weakCachingResolver(Packet.class.getClassLoader())),
                                    handlerClass.newInstance()
                            );
                        }
                    });
            bootstrap.bind(port).sync().channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
