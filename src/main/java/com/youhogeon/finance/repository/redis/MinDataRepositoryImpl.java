package com.youhogeon.finance.repository.redis;

import java.util.List;

import com.youhogeon.finance.entity.Chart;
import com.youhogeon.finance.helper.Serialize;
import com.youhogeon.finance.repository.MinDataRepository;

import redis.clients.jedis.Jedis;

public class MinDataRepositoryImpl implements MinDataRepository {

    private Jedis connection = ConnectionImpl.connection;

    @Override
    public List<Chart> getDaily(String code, int date) {
        byte[] obj = connection.get(makeKey(code, date));

        if (obj == null) return null;

        return (List<Chart>) Serialize.unserialize(obj);
    }

    public void setDaily(String code, int date, List<Chart> obj) {
        connection.set(makeKey(code, date), Serialize.serialize(obj));
    }

    private byte[] makeKey(String code, int date) {
        return ("MinData/" + code + "/" + date).getBytes();
    }
}
