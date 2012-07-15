package com.spartango.jediscollect.collections;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import com.spartango.jediscollect.core.JedisBackedObject;

import redis.clients.jedis.JedisPool;

public class JedisSet<E> extends JedisBackedObject implements Set<E> {

    public JedisSet(String key, JedisPool pool) {
        super(key, pool);
        // TODO Auto-generated constructor stub
    }

    public JedisSet(String key) {
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

    @Override public boolean contains(Object o) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override public Iterator<E> iterator() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override public Object[] toArray() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override public <T> T[] toArray(T[] a) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override public boolean add(E e) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override public boolean remove(Object o) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override public boolean containsAll(Collection<?> c) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override public boolean addAll(Collection<? extends E> c) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override public boolean retainAll(Collection<?> c) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override public boolean removeAll(Collection<?> c) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override public void clear() {
        // TODO Auto-generated method stub

    }

}
