package com.collabs.server.data;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Aleksey A.
 */
public class ClientStorage {
    private static final ClientStorage clients = new ClientStorage();

    private Map<Integer, String> info;

    private ClientStorage() {
        info = new ConcurrentHashMap<Integer, String>();
    }

    public void add(int idClient, String name) {
        info.put(idClient, name);
    }

    public Map<Integer, String> getMap() {
        return info;
    }

    public static ClientStorage get() {
        return clients;
    }
}
