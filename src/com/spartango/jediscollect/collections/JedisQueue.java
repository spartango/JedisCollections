package com.spartango.jediscollect.collections;

import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class JedisQueue<T> implements BlockingQueue<T> {

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

    @Override public Iterator<T> iterator() {
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

    @Override public boolean remove(Object o) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override public boolean containsAll(Collection<?> c) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override public boolean addAll(Collection<? extends T> c) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override public boolean removeAll(Collection<?> c) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override public boolean retainAll(Collection<?> c) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override public void clear() {
        // TODO Auto-generated method stub

    }

    @Override public boolean add(T e) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override public boolean offer(T e) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override public T remove() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override public T poll() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override public T element() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override public T peek() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override public void put(T e) throws InterruptedException {
        // TODO Auto-generated method stub

    }

    @Override public boolean
            offer(T e, long timeout, TimeUnit unit) throws InterruptedException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override public T take() throws InterruptedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override public T
            poll(long timeout, TimeUnit unit) throws InterruptedException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override public int remainingCapacity() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override public int drainTo(Collection<? super T> c) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override public int drainTo(Collection<? super T> c, int maxElements) {
        // TODO Auto-generated method stub
        return 0;
    }

}
