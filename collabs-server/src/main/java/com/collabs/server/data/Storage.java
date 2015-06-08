package com.collabs.server.data;

import java.util.List;

/**
 * @author Aleksey A.
 */
public interface Storage<T> {
    void addItem(T item);
    void removeItem(int id);
    T get(int id);
    List<T> getAll();
    boolean containsId(int id);
}
