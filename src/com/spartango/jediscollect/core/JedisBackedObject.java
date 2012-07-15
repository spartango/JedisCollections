package com.spartango.jediscollect.core;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

import redis.clients.jedis.JedisPool;

public abstract class JedisBackedObject {

    private static JedisPool defaultPool;

    // Key under which we can find this object
    protected final String   key;
    protected final byte[]   byteKey;

    // Pool of connections on which we can perform operations
    protected JedisPool      pool;

    public JedisBackedObject(String key) {
        // Use the default pool
        this(key, getDefaultPool());
    }

    public JedisBackedObject(String key, JedisPool pool) {
        this.key = key;
        this.byteKey = key.getBytes();
        this.pool = pool;
    }

    public String getKey() {
        return key;
    }

    public void setPool(JedisPool pool) {
        this.pool = pool;
    }

    // Default pool management

    public static JedisPool getDefaultPool() {
        if (defaultPool == null) {
            // Initialize a pool
            defaultPool = new JedisPool("localhost");
        }
        return defaultPool;
    }

    public static void setDefaultPool(JedisPool defaultPool) {
        JedisBackedObject.defaultPool = defaultPool;
    }

    protected static byte[] serializeObject(Object tkey) throws IOException {
        // Serialize the key
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = new ObjectOutputStream(bos);
        out.writeObject(tkey);
        byte[] byteKey = bos.toByteArray();
        out.close();
        bos.close();
        return byteKey;
    }

    protected static Object
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

    protected static Object deserialize(byte[] objectData) throws IOException {
        if (objectData == null) {
            throw new IllegalArgumentException("The byte[] must not be null");
        }
        ByteArrayInputStream bais = new ByteArrayInputStream(objectData);
        return deserialize(bais);
    }

}
