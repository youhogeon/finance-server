package com.youhogeon.finance.service;

import java.util.List;
import java.util.Map;

import com.youhogeon.finance.entity.Balance;
import com.youhogeon.finance.entity.Robot;
import com.youhogeon.finance.helper.Buffer;
import com.youhogeon.finance.repository.MinDataRepository;
import com.youhogeon.finance.repository.RobotRepository;
import com.youhogeon.finance.repository.integrated.MinDataRepositoryImpl;
import com.youhogeon.finance.repository.integrated.RobotRepositoryImpl;

public class RobotService {

    public static Robot getRobot(String auth) throws Exception {
        RobotRepository robotRepository = new RobotRepositoryImpl();
        Robot robot = robotRepository.getRobot(auth);

        if (robot == null) throw new Exception("141");

        return robot;
    }

    public static byte[] getBalance(Robot robot) {
        long balance = robot.getBalance();

        Buffer res = new Buffer(8);
        res.add(0, balance);

        return res.getBuffer();
    }

    public static byte[] getStockBalance(Robot robot, Buffer req) {
        RobotRepository robotRepository = new RobotRepositoryImpl();
        List<Balance> balance = robotRepository.getStockBalance(robot);
        int size = balance.size();

        Buffer res = new Buffer(14 * size);

        int idx = 0;
        for (Balance b : balance) {
            res.add(idx, b.getCode());
            res.add(idx + 6, b.getPrice());
            res.add(idx + 10, b.getQty());
            idx += 14;
        }

        return res.getBuffer();
    }

    public static byte[] orderMarketPrice(Robot robot, Buffer req) throws Exception {
        RobotRepository robotRepository = new RobotRepositoryImpl();
        MinDataRepository minRepository = new MinDataRepositoryImpl();

        String code = req.getString(0, 6);
        int qty = req.getInt(6);
        
        if (qty == 0) throw new Exception("140");

        Map<String, Integer> currentPrice = minRepository.getCurrentPrice();
        if (!currentPrice.containsKey(code)) throw new Exception("102");

        int price = currentPrice.get(code);
        Balance balance = robotRepository.getStockBalance(robot, code);

        if (!StatusService.marketOpen()) throw new Exception("100");
        if (qty > 0 && robot.getBalance() < price * qty) throw new Exception("101");
        if (qty < 0 && (balance == null || balance.getQty() < qty * -1)) throw new Exception("101");

        robotRepository.orderMarketPrice(robot, code, price, qty);

        return new byte[10];
    }

}
