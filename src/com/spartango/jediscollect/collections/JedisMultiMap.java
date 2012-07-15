package com.spartango.jediscollect.collections;

import java.util.Collection;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import redis.clients.jedis.JedisPool;

import com.hazelcast.core.EntryListener;
import com.hazelcast.core.MultiMap;
import com.spartango.jediscollect.core.JedisBackedObject;

public class JedisMultiMap<K, V> extends JedisBackedObject implements
                                                          MultiMap<K, V> {

    public JedisMultiMap(String key, JedisPool pool) {
        super(key, pool);
        // TODO Auto-generated constructor stub
    }

    public JedisMultiMap(String key) {
        super(key);
        // TODO Auto-generated constructor stub
    }

    @Override public void destroy() {
        // TODO Auto-generated method stub

    }

    @Override public Object getId() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override public InstanceType getInstanceType() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override public void addEntryListener(EntryListener<K, V> arg0,
                                           boolean arg1) {
        // TODO Auto-generated method stub

    }

    @Override public void addEntryListener(EntryListener<K, V> arg0,
                                           K arg1,
                                           boolean arg2) {
        // TODO Auto-generated method stub

    }

    @Override public void addLocalEntryListener(EntryListener<K, V> arg0) {
        // TODO Auto-generated method stub

    }

    @Override public void clear() {
        // TODO Auto-generated method stub

    }

    @Override public boolean containsEntry(K arg0, V arg1) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override public boolean containsKey(K arg0) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override public boolean containsValue(Object arg0) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override public Set<Entry<K, V>> entrySet() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override public Collection<V> get(K arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override public String getName() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override public Set<K> keySet() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override public Set<K> localKeySet() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override public void lock(K arg0) {
        // TODO Auto-generated method stub

    }

    @Override public boolean lockMap(long arg0, TimeUnit arg1) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override public boolean put(K arg0, V arg1) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override public Collection<V> remove(Object arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override public boolean remove(Object arg0, Object arg1) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override public void removeEntryListener(EntryListener<K, V> arg0) {
        // TODO Auto-generated method stub

    }

    @Override public void removeEntryListener(EntryListener<K, V> arg0, K arg1) {
        // TODO Auto-generated method stub

    }

    @Override public int size() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override public boolean tryLock(K arg0) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override public boolean tryLock(K arg0, long arg1, TimeUnit arg2) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override public void unlock(K arg0) {
        // TODO Auto-generated method stub

    }

    @Override public void unlockMap() {
        // TODO Auto-generated method stub

    }

    @Override public int valueCount(K arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override public Collection<V> values() {
        // TODO Auto-generated method stub
        return null;
    }

}
