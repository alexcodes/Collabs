package com.collabs.plugin.data.storage;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Aleksey A.
 */
public class Clients {
    private static final Clients clients = new Clients();

    private Map<Integer, String> info;

    private Clients() {
        info = new ConcurrentHashMap<Integer, String>();
    }

    public String getName(int idClient) {
        return info.get(idClient);
    }

    public void set(Map<Integer, String> map) {
        info = map;
    }

    public boolean contains(int idClient) {
        return info.containsKey(idClient);
    }

    public static Clients get() {
        return clients;
    }
}
