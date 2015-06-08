package com.collabs.server.core;

import com.collabs.common.model.data.Packet;
import com.collabs.server.core.service.Service;

/**
 * @author Aleksey A.
 */
public class ServerHandler extends AbstractHandler {
    private static final Service service = new Service();

    static {
        Thread thread = new Thread(service);
        thread.start();
    }

    @Override
    protected void handlePlugins(Packet packet) {
        service.addPacket(packet);
    }
}
