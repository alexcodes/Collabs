package com.collabs.server.core.service;

import com.collabs.common.model.data.Packet;
import com.collabs.common.model.event.Event;
import com.collabs.common.pluggable.PluginInfo;
import com.collabs.server.data.PluginStorage;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * No need in this class on the plugin side
 *
 * @author Aleksey A.
 */
public class Service implements Runnable {
    public static final int DEFAULT_POOL_SIZE = 1;
    public static boolean isStopped = false;

    private ExecutorService threadPool;
    private BlockingQueue<Packet> queue;
    private final int poolSize;

    public Service() {
        this(DEFAULT_POOL_SIZE);
    }

    public Service(int poolSize) {
        this.threadPool = Executors.newFixedThreadPool(poolSize);
        this.queue = new LinkedBlockingQueue<Packet>();
        this.poolSize = poolSize;

        initThreadPool();
    }

    public synchronized void addPacket(Packet packet) {
        if (packet != null) {
            queue.add(packet);
        }
    }

    public void stop() {
        isStopped = true;
    }

    private void initThreadPool() {
        for (int i = 0; i < poolSize; i++) {
            threadPool.execute(this);
        }
    }

    @Override
    public void run() {
        try {
            while (! isStopped) {
                Packet packet = queue.take();
                handlePacket(packet);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }
    }

    @SuppressWarnings("unchecked")
    private void handlePacket(Packet packet) {
        List<PluginInfo> list = PluginStorage.get().getAll();
        for (PluginInfo pluginInfo : list) {
            pluginInfo.getPlugin().invoke(packet);
        }
    }
}
