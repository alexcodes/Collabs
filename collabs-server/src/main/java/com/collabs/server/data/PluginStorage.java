package com.collabs.server.data;

import com.collabs.common.pluggable.PluginInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Aleksey A.
 */
public class PluginStorage<T extends PluginInfo> implements Storage<T> {
    private static final PluginStorage<PluginInfo> storage = new PluginStorage<PluginInfo>();

    private final Map<Integer, T> items;

    private PluginStorage() {
        items = new ConcurrentHashMap<Integer, T>();
    }

    @Override
    public synchronized void addItem(T item) {
        items.put(item.getName().hashCode(), item);
    }

    @Override
    public void removeItem(int id) {
        items.remove(id);
    }

    @Override
    public T get(int id) {
        return items.get(id);
    }

    @Override
    public List<T> getAll() {
        return new ArrayList<T>(items.values());
    }

    @Override
    public boolean containsId(int id) {
        return items.containsKey(id);
    }

    public static PluginStorage get() {
        return storage;
    }
}
