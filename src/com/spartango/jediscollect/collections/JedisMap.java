package com.spartango.jediscollect.collections;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Response;
import redis.clients.jedis.Transaction;

import com.spartango.jediscollect.core.JedisBackedObject;

public class JedisMap<K extends Serializable, V extends Serializable> extends
                                                                      JedisBackedObject implements
                                                                                       Map<K, V> {

    public JedisMap(String key, JedisPool pool) {
        super(key, pool);
    }

    public JedisMap(String key) {
        super(key);
    }

    @Override public int size() {
        Jedis jedis = pool.getResource();
        long value = 0;
        try {
            value = jedis.hlen(key);
        } finally {
            pool.returnResource(jedis);
        }
        return (int) value;
    }

    @Override public boolean isEmpty() {
        return size() == 0;
    }

    @Override public boolean containsKey(Object tkey) {

        Jedis jedis = pool.getResource();
        boolean value = false;
        try {
            value = jedis.hexists(byteKey, serializeObject(tkey));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            pool.returnResource(jedis);
        }
        return value;
    }

    @Override public boolean containsValue(Object value) {
        // Redis doesnt have an easy way to do this
        throw new UnsupportedOperationException();
    }

    @Override public V get(Object tkey) {
        Jedis jedis = pool.getResource();
        V value = null;
        try {
            byte[] object = jedis.hget(byteKey, serializeObject(tkey));
            value = (V) deserialize(object);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            pool.returnResource(jedis);
        }
        return value;
    }

    @Override public V put(K tkey, V value) {
        Jedis jedis = pool.getResource();
        try {
            jedis.hset(byteKey, serializeObject(tkey), serializeObject(value));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            pool.returnResource(jedis);
        }
        return value;
    }

    @Override public V remove(Object tkey) {
        Jedis jedis = pool.getResource();
        V value = null;
        try {
            Transaction transaction = jedis.multi();
            Response<byte[]> object = transaction.hget(byteKey,
                                                       serializeObject(tkey));
            transaction.hdel(byteKey, serializeObject(tkey));
            transaction.exec();

            value = (V) deserialize(object.get());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            pool.returnResource(jedis);
        }
        return value;
    }

    @Override public void putAll(Map<? extends K, ? extends V> m) {
        // For each item in the map
        // Prep each by serializing
        // Put all of them
        HashMap<byte[], byte[]> serialMap = new HashMap<>();
        for (K tkey : m.keySet()) {
            try {
                serialMap.put(serializeObject(tkey),
                              serializeObject(m.get(tkey)));
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }

        // hmset
        Jedis jedis = pool.getResource();
        try {
            jedis.hmset(byteKey, serialMap);
        } finally {
            pool.returnResource(jedis);
        }
    }

    @Override public void clear() {
        Jedis jedis = pool.getResource();
        try {
            jedis.del(byteKey);
        } finally {
            pool.returnResource(jedis);
        }

    }

    @Override public Set<K> keySet() {
        Jedis jedis = pool.getResource();
        Set<K> keys = new HashSet<>();
        try {
            Set<byte[]> keyBytes = jedis.hkeys(byteKey);
            // For each item
            // Deserialize it
            for (byte[] object : keyBytes) {
                keys.add((K) deserialize(object));
            }

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            pool.returnResource(jedis);
        }
        return keys;
    }

    @Override public Collection<V> values() {
        // hvals
        Jedis jedis = pool.getResource();
        LinkedList<V> values = new LinkedList<>();
        try {
            List<byte[]> valueBytes = jedis.hvals(byteKey);
            // For each item
            // Deserialize it
            for (byte[] object : valueBytes) {
                values.add((V) deserialize(object));
            }

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            pool.returnResource(jedis);
        }
        return values;
    }

    @Override public Set<Map.Entry<K, V>> entrySet() {
        // hvals
        Jedis jedis = pool.getResource();
        HashMap<K, V> localMap = new HashMap<>();
        try {
            Map<byte[], byte[]> keyValueBytes = jedis.hgetAll(byteKey);
            // For each item
            // Deserialize it
            for (byte[] tkeyBytes : keyValueBytes.keySet()) {
                // Get the value
                byte[] tvalueBytes = keyValueBytes.get(tkeyBytes);
                // Deserialize both key and value
                K tkey = (K) deserialize(tkeyBytes);
                V tvalue = (V) deserialize(tvalueBytes);
                localMap.put(tkey, tvalue);
            }

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            pool.returnResource(jedis);
        }
        return localMap.entrySet();
    }

}
