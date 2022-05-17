package com.youhogeon.finance.repository;

import java.util.List;

import com.youhogeon.finance.entity.Balance;
import com.youhogeon.finance.entity.Robot;

public interface RobotRepository {
    public Robot getRobot(String robotID);
    default void setRobot(Robot robot) { }
    default void setRobot(String auth) { }

    public List<Balance> getStockBalance(Robot robot);
    public Balance getStockBalance(Robot robot, String code);
    default void setStockBalance(Robot robot, List<Balance> data) { }
    default void setStockBalance(String auth) { }

    default void orderMarketPrice(Robot robot, String code, int price, int qty) { }
}
