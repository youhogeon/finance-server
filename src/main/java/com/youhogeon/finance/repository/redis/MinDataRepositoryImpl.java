package com.youhogeon.finance.repository.redis;

import java.util.List;
import java.util.Map;

import com.youhogeon.finance.entity.Chart;
import com.youhogeon.finance.helper.Serialize;
import com.youhogeon.finance.repository.MinDataRepository;

import redis.clients.jedis.Jedis;

@SuppressWarnings("unchecked")
public class MinDataRepositoryImpl implements MinDataRepository {

    private Jedis connection = ConnectionImpl.connection;

    @Override
    public List<Chart> getDaily(String code, int date) {
        Object obj = Serialize.unserialize(connection.get(makeKeyDaily(code, date)));

        if (obj == null) return null;

        return (List<Chart>) obj;
    }

    @Override
    public void setDaily(String code, int date, List<Chart> obj) {
        connection.set(makeKeyDaily(code, date), Serialize.serialize(obj));
    }

    private byte[] makeKeyDaily(String code, int date) {
        return ("MinData." + code + "." + date).getBytes();
    }

    @Override
    public List<String> getAvailableCode() {
        return null;
    }

    @Override
    public Map<String, Integer> getCurrentPrice() {
        Object obj = Serialize.unserialize(connection.get(makeKeyCurrentPrice()));

        if (obj == null) return null;

        return (Map<String, Integer>) obj;
    }

    public void setCurrentPrice(Map<String, Integer> obj) {
        connection.set(makeKeyCurrentPrice(), Serialize.serialize(obj));
    }

    private byte[] makeKeyCurrentPrice() {
        return ("CurrentPrice." + (int)(System.currentTimeMillis() / 10000)).getBytes();
    }
}
