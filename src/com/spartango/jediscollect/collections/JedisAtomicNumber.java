package com.spartango.jediscollect.collections;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Response;
import redis.clients.jedis.Transaction;

import com.hazelcast.core.AtomicNumber;
import com.hazelcast.monitor.LocalAtomicNumberStats;
import com.spartango.jediscollect.core.JedisBackedObject;

public class JedisAtomicNumber extends JedisBackedObject implements
                                                        AtomicNumber {

    public JedisAtomicNumber(String key, JedisPool pool) {
        super(key, pool);
    }

    public JedisAtomicNumber(String key) {
        super(key);
    }

    @Override public void destroy() {
        throw new UnsupportedOperationException();
    }

    @Override public Object getId() {
        return key;
    }

    @Override public InstanceType getInstanceType() {
        return InstanceType.ATOMIC_NUMBER;
    }

    @Override public long addAndGet(long arg0) {
        Jedis jedis = pool.getResource();
        long value = 0;
        try {
            value = jedis.incrBy(key, arg0);
        } finally {
            pool.returnResource(jedis);
        }
        return value;
    }

    @Override public boolean compareAndSet(long arg0, long arg1) {
        // Redis does not support this type of operation
        throw new UnsupportedOperationException();
    }

    @Override public long decrementAndGet() {
        Jedis jedis = pool.getResource();
        long value = 0;
        try {
            value = jedis.decr(key);
        } finally {
            pool.returnResource(jedis);
        }
        return value;
    }

    @Override public long get() {
        Jedis jedis = pool.getResource();
        long value = 0;
        try {
            value = Long.parseLong(jedis.get(key));
        } finally {
            pool.returnResource(jedis);
        }
        return value;
    }

    @Override public long getAndAdd(long arg0) {
        Jedis jedis = pool.getResource();
        long value = 0;
        try {
            Transaction transaction = jedis.multi();
            Response<String> previous = transaction.get(key);
            transaction.incrBy(key, arg0);
            transaction.exec();

            value = Long.parseLong(previous.get());
        } finally {
            pool.returnResource(jedis);
        }
        return value;
    }

    @Override public long getAndSet(long arg0) {
        Jedis jedis = pool.getResource();
        long value = 0;
        try {
            String previous = jedis.getSet(key, "" + arg0);
            value = Long.parseLong(previous);
        } finally {
            pool.returnResource(jedis);
        }
        return value;
    }

    @Override public LocalAtomicNumberStats getLocalAtomicNumberStats() {
        throw new UnsupportedOperationException();
    }

    @Override public String getName() {
        return key;
    }

    @Override public long incrementAndGet() {
        Jedis jedis = pool.getResource();
        long value = 0;
        try {
            value = jedis.incr(key);
        } finally {
            pool.returnResource(jedis);
        }
        return value;
    }

    @Override @Deprecated public void lazySet(long arg0) {
        // This is deprecated, so we won't support it right away
        throw new UnsupportedOperationException();
    }

    @Override public void set(long arg0) {
        Jedis jedis = pool.getResource();
        try {
            jedis.set(key, "" + arg0);
        } finally {
            pool.returnResource(jedis);
        }
    }

    @Override @Deprecated public boolean
            weakCompareAndSet(long arg0, long arg1) {
        throw new UnsupportedOperationException();
    }

}
