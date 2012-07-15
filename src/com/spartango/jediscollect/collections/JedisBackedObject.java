package com.spartango.jediscollect.collections;

import redis.clients.jedis.JedisPool;

public abstract class JedisBackedObject {

    private static JedisPool defaultPool;

    // Key under which we can find this object
    protected final String   key;

    // Pool of connections on which we can perform operations
    protected JedisPool      pool;

    public JedisBackedObject(String key) {
        // Use the default pool
        this(key, getDefaultPool());
    }

    public JedisBackedObject(String key, JedisPool pool) {
        this.key = key;
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

}
