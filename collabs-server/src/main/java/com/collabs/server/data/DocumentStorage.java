package com.collabs.server.data;

import com.collabs.common.model.data.document.ServerDocument;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * TODO in method addItem - new object??
 *
 * @author Aleksey A.
 */
@SuppressWarnings("unchecked")
public class DocumentStorage<T extends ServerDocument> implements Storage<T> {
    private static final DocumentStorage<ServerDocument> storage = new DocumentStorage<ServerDocument>();

    private final Map<Integer, T> items;

    private DocumentStorage() {
        items = new ConcurrentHashMap<Integer, T>();
    }

    @Override
    public void addItem(T item) {
        items.put(item.getIdDocument(), (T) new ServerDocument(item));
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

    public static DocumentStorage get() {
        return storage;
    }
}
