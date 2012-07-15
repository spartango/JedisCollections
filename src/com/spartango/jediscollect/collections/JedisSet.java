package com.spartango.jediscollect.collections;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;

import com.spartango.jediscollect.core.JedisBackedObject;

public class JedisSet<E> extends JedisBackedObject implements Set<E> {

    public JedisSet(String key, JedisPool pool) {
        super(key, pool);
    }

    public JedisSet(String key) {
        super(key);
    }

    @Override public int size() {
        Jedis jedis = pool.getResource();
        long value = 0;
        try {
            value = jedis.scard(key);
        } finally {
            pool.returnResource(jedis);
        }
        return (int) value;
    }

    @Override public boolean isEmpty() {
        return size() == 0;
    }

    @Override public boolean contains(Object o) {
        // sismember
        Jedis jedis = pool.getResource();
        boolean value = false;
        try {
            value = jedis.sismember(byteKey, serializeObject(o));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            pool.returnResource(jedis);
        }
        return value;
    }

    @Override public Iterator<E> iterator() {
        return new Iterator<E>() {

            @Override public boolean hasNext() {
                // TODO Auto-generated method stub
                return false;
            }

            @Override public E next() {
                // TODO Auto-generated method stub
                return null;
            }

            @Override public void remove() {
                // TODO Auto-generated method stub

            }
        };
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
        // sadd
        Jedis jedis = pool.getResource();
        long value = 0;
        try {
            value = jedis.sadd(byteKey, serializeObject(e));
        } catch (IOException e1) {
            e1.printStackTrace();
        } finally {
            pool.returnResource(jedis);
        }
        return value > 0;
    }

    @Override public boolean remove(Object o) {
        // srem
        Jedis jedis = pool.getResource();
        long value = 0;
        try {
            value = jedis.srem(byteKey, serializeObject(o));
        } catch (IOException e1) {
            e1.printStackTrace();
        } finally {
            pool.returnResource(jedis);
        }
        return value > 0;
    }

    @Override public boolean containsAll(Collection<?> c) {
        Jedis jedis = pool.getResource();
        boolean value = true;
        try {
            Transaction transaction = jedis.multi();
            for (Object o : c) {
                transaction.sismember(byteKey, serializeObject(o));
            }
            List<Object> results = transaction.exec();
            for (Object result : results) {
                if (!(Boolean) result) {
                    value = false;
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            pool.returnResource(jedis);
        }
        return value;
    }

    @Override public boolean addAll(Collection<? extends E> c) {
        // Serialize all the objects
        Vector<byte[]> objects = new Vector<>(c.size());
        for (Object o : c) {
            try {
                objects.add(serializeObject(o));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // sadd
        Jedis jedis = pool.getResource();
        long value = 0;
        try {
            value = jedis.sadd(byteKey, (byte[][]) objects.toArray());
        } finally {
            pool.returnResource(jedis);
        }
        return value > 0;
    }

    @Override public boolean retainAll(Collection<?> c) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override public boolean removeAll(Collection<?> c) {
        // Serialize all the objects
        Vector<byte[]> objects = new Vector<>(c.size());
        for (Object o : c) {
            try {
                objects.add(serializeObject(o));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // srem
        Jedis jedis = pool.getResource();
        long value = 0;
        try {
            value = jedis.srem(byteKey, (byte[][]) objects.toArray());
        } finally {
            pool.returnResource(jedis);
        }
        return value > 0;
    }

    @Override public void clear() {
        Jedis jedis = pool.getResource();
        try {
            jedis.del(byteKey);
        } finally {
            pool.returnResource(jedis);
        }
    }
}
