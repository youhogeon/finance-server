package com.youhogeon.finance.repository.redis;

import java.util.List;

import com.youhogeon.finance.entity.Balance;
import com.youhogeon.finance.entity.Robot;
import com.youhogeon.finance.helper.Serialize;
import com.youhogeon.finance.repository.RobotRepository;

import redis.clients.jedis.Jedis;

@SuppressWarnings("unchecked")
public class RobotRepositoryImpl implements RobotRepository {

    private Jedis connection = ConnectionImpl.connection;

    @Override
    public Robot getRobot(String robotID) {
        Object obj = Serialize.unserialize(connection.get(makeKeyRobot(robotID)));

        if (obj == null) return null;
    
        return (Robot) obj;
    }

    @Override
    public void setRobot(Robot robot) {
        connection.set(makeKeyRobot(robot.getAuth()), Serialize.serialize(robot));
    }

    public void setRobot(String auth) {
        connection.del(makeKeyRobot(auth));
    }

    private byte[] makeKeyRobot(String auth) {
        return ("Robot." + auth).getBytes();
    }

    @Override
    public List<Balance> getStockBalance(Robot robot) {
        Object obj = Serialize.unserialize(connection.get(makeKeyBalance(robot.getAuth())));

        if (obj == null) return null;
    
        return (List<Balance>) obj;
    }

    @Override
    public Balance getStockBalance(Robot robot, String code) {
        return null;
    }

    @Override
    public void setStockBalance(Robot robot, List<Balance> data) {
        connection.set(makeKeyBalance(robot.getAuth()), Serialize.serialize(data));
    }

    public void setStockBalance(String auth) {
        connection.del(makeKeyBalance(auth));
    }

    private byte[] makeKeyBalance(String auth) {
        return ("Robot.Balance." + auth).getBytes();
    }
    
}
