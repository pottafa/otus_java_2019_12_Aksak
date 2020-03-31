package ru.otus.homework.cache;

import java.util.*;

public class MyCache<K, V> implements HwCache<K, V> {
    Map<K, V> references = new WeakHashMap<>();
    List<HwListener<K, V>> listeners = new ArrayList<>();

    @Override
    public void put(K key, V value) {
        references.put(key, value);
        listeners.forEach(listener -> listener.notify(key, value, "put"));
    }

    @Override
    public void remove(K key) {
        listeners.forEach(listener -> listener.notify(key, references.get(key), "remove"));
        references.remove(key);
    }

    @Override
    public V get(K key) {
        listeners.forEach(listener -> listener.notify(key, references.get(key), "get"));
        return references.get(key);
    }

    @Override
    public void addListener(HwListener<K, V> listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(HwListener<K, V> listener) {
        listeners.remove(listener);
    }
}
