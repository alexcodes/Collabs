package com.collabs.server.data;

import com.collabs.common.model.data.issue.Issue;
import com.collabs.common.model.event.issue.IssueUpdateException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Aleksey A.
 */
public class IssueStorage<T extends Issue> implements Storage<T> {
    private static final IssueStorage<Issue> storage = new IssueStorage<Issue>();
    private static int count;

    private final Map<Integer, T> items;

    private IssueStorage() {
        items = new ConcurrentHashMap<Integer, T>();
    }

    @Override
    public synchronized void addItem(T item) {
        item.setIdIssue(++count);
        items.put(item.getIdIssue(), item);
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

    public void updateIssue(Issue issue) throws IssueUpdateException {
        synchronized (items) {
            items.get(issue.getIdIssue()).update(issue);
        }
    }

    public static IssueStorage get() {
        return storage;
    }
}
