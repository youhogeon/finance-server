package com.youhogeon.finance.repository.integrated;

import java.util.List;

import com.youhogeon.finance.entity.Balance;
import com.youhogeon.finance.entity.Robot;
import com.youhogeon.finance.repository.RobotRepository;

public class RobotRepositoryImpl implements RobotRepository {
    RobotRepository rdbRepo;
    RobotRepository cacheRepo;
    
    public RobotRepositoryImpl() {
        cacheRepo = new com.youhogeon.finance.repository.redis.RobotRepositoryImpl();
        rdbRepo = new com.youhogeon.finance.repository.mysql.RobotRepositoryImpl();
    }

    @Override
    public Robot getRobot(String robotID) {
        Robot result = cacheRepo.getRobot(robotID);

        if (result != null) return result;

        result = rdbRepo.getRobot(robotID);
        if (result != null) cacheRepo.setRobot(result);
        else cacheRepo.setRobot(robotID);

        return result;
    }

    @Override
    public void orderMarketPrice(Robot robot, String code, int price, int qty) {
        cacheRepo.setRobot(robot.getAuth());
        cacheRepo.setStockBalance(robot.getAuth());
        rdbRepo.orderMarketPrice(robot, code, price, qty);
    }

    @Override
    public List<Balance> getStockBalance(Robot robot) {
        List<Balance> result = cacheRepo.getStockBalance(robot);

        if (result != null) return result;

        result = rdbRepo.getStockBalance(robot);
        cacheRepo.setStockBalance(robot, result);

        return result;
    }

    @Override
    public Balance getStockBalance(Robot robot, String code) {
        return rdbRepo.getStockBalance(robot, code);
    }

}
