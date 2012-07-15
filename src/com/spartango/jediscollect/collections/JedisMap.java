package com.spartango.jediscollect.collections;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.spartango.jediscollect.core.JedisBackedObject;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Response;
import redis.clients.jedis.Transaction;

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
            value = jedis.hexists(key.getBytes(), serializeObject(tkey));
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
            byte[] object = jedis.hget(key.getBytes(), serializeObject(tkey));
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
            jedis.hset(key.getBytes(),
                       serializeObject(tkey),
                       serializeObject(value));
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
            Response<byte[]> object = transaction.hget(key.getBytes(),
                                                       serializeObject(tkey));
            transaction.hdel(key.getBytes(), serializeObject(tkey));
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
            jedis.hmset(key.getBytes(), serialMap);
        } finally {
            pool.returnResource(jedis);
        }
    }

    @Override public void clear() {
        Jedis jedis = pool.getResource();
        try {
            Set<byte[]> keyBytes = jedis.hkeys(key.getBytes());

            Transaction deleteTransaction = jedis.multi();
            // For each item
            // Delete it
            for (byte[] byteKey : keyBytes) {
                deleteTransaction.hdel(key.getBytes(), byteKey);
            }
            deleteTransaction.exec();
        } finally {
            pool.returnResource(jedis);
        }

    }

    @Override public Set<K> keySet() {
        Jedis jedis = pool.getResource();
        Set<K> keys = new HashSet<>();
        try {
            Set<byte[]> keyBytes = jedis.hkeys(key.getBytes());
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
            List<byte[]> valueBytes = jedis.hvals(key.getBytes());
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
            Map<byte[], byte[]> keyValueBytes = jedis.hgetAll(key.getBytes());
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

    private static byte[] serializeObject(Object tkey) throws IOException {
        // Serialize the key
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = new ObjectOutputStream(bos);
        out.writeObject(tkey);
        byte[] byteKey = bos.toByteArray();
        out.close();
        bos.close();
        return byteKey;
    }

    private static Object
            deserialize(InputStream inputStream) throws IOException {
        if (inputStream == null) {
            ObjectInputStream in = null;
            try {
                in = new ObjectInputStream(inputStream);
                return in.readObject();

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                throw new IOException();
            } finally {
                try {
                    if (in != null) {
                        in.close();
                    }
                } catch (IOException e) {
                    // Don't really care if the close failed
                }
            }
        } else {
            return null;
        }
    }

    private static Object deserialize(byte[] objectData) throws IOException {
        if (objectData == null) {
            throw new IllegalArgumentException("The byte[] must not be null");
        }
        ByteArrayInputStream bais = new ByteArrayInputStream(objectData);
        return deserialize(bais);
    }

}
