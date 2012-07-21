package com.spartango.jediscollect.core;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import redis.clients.jedis.JedisPool;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public abstract class JedisBackedObject {

    private static JedisPool defaultPool;

    // Key under which we can find this object
    protected final String   key;
    protected final byte[]   byteKey;

    // Serializer
    protected final Kryo     kryo;

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
        this.kryo = new Kryo();
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

    protected byte[] serializeObject(Object tkey) throws IOException {
        // Serialize the key
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             Output out = new Output()) {

            kryo.writeClassAndObject(out, tkey);

            byte[] byteKey = bos.toByteArray();
            return byteKey;
        }
    }

    protected Object deserialize(InputStream inputStream) throws IOException {
        try (Input input = new Input(inputStream)) {
            Object object = kryo.readClassAndObject(input);
            return object;
        }
    }

    protected Object deserialize(byte[] objectData) throws IOException {
        if (objectData == null) {
            throw new IllegalArgumentException("The byte[] must not be null");
        }
        ByteArrayInputStream bais = new ByteArrayInputStream(objectData);
        return deserialize(bais);
    }

}
