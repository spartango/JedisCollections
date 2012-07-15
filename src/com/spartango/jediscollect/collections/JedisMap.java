package com.spartango.jediscollect.collections;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.JedisPool;

public class JedisMap<K, V> extends JedisBackedObject implements Map<K, V> {

    public JedisMap(String key, JedisPool pool) {
        super(key, pool);
        // TODO Auto-generated constructor stub
    }

    public JedisMap(String key) {
        super(key);
        // TODO Auto-generated constructor stub
    }

    @Override public int size() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override public boolean isEmpty() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override public boolean containsKey(Object key) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override public boolean containsValue(Object value) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override public V get(Object key) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override public V put(K key, V value) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override public V remove(Object key) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override public void putAll(Map<? extends K, ? extends V> m) {
        // TODO Auto-generated method stub

    }

    @Override public void clear() {
        // TODO Auto-generated method stub

    }

    @Override public Set<K> keySet() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override public Collection<V> values() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override public Set<java.util.Map.Entry<K, V>> entrySet() {
        // TODO Auto-generated method stub
        return null;
    }

}
