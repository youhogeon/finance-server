package com.youhogeon.finance.repository.redis;

import com.youhogeon.finance.repository.*;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class ConnectionImpl implements Connection {

    JedisPool pool;
    public static Jedis connection;

    @Override
    public void connect(Authentication[] auth) throws Exception {
        pool = new JedisPool(auth[0].getHost(), auth[0].getPort());
        connection = pool.getResource();
    }

    @Override
    public void close() throws Exception {
        connection.close();
    }

}
