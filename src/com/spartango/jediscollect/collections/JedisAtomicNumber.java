package com.spartango.jediscollect.collections;

import com.hazelcast.core.AtomicNumber;
import com.hazelcast.monitor.LocalAtomicNumberStats;

public class JedisAtomicNumber implements AtomicNumber {

    @Override public void destroy() {
        // TODO Auto-generated method stub

    }

    @Override public Object getId() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override public InstanceType getInstanceType() {
        return InstanceType.ATOMIC_NUMBER;
    }

    @Override public long addAndGet(long arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override public boolean compareAndSet(long arg0, long arg1) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override public long decrementAndGet() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override public long get() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override public long getAndAdd(long arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override public long getAndSet(long arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override public LocalAtomicNumberStats getLocalAtomicNumberStats() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override public String getName() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override public long incrementAndGet() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override @Deprecated public void lazySet(long arg0) {
        // TODO Auto-generated method stub

    }

    @Override public void set(long arg0) {
        // TODO Auto-generated method stub

    }

    @Override @Deprecated public boolean
            weakCompareAndSet(long arg0, long arg1) {
        // TODO Auto-generated method stub
        return false;
    }

}
