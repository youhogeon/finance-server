package com.youhogeon.finance.repository.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.youhogeon.finance.entity.Balance;
import com.youhogeon.finance.entity.Robot;
import com.youhogeon.finance.repository.RobotRepository;

public class RobotRepositoryImpl implements RobotRepository{

    private java.sql.Connection connection = ConnectionImpl.connection;

    @Override
    public Robot getRobot(String robotID) {
        Robot robot = null;

        try (PreparedStatement stmt = connection.prepareStatement("SELECT `id`, `balance` FROM robots WHERE `auth`=? LIMIT 0, 1");){
            stmt.setString(1, robotID);
    
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                robot = new Robot(robotID);
                robot.setId(rs.getInt(1)); 
                robot.setBalance(rs.getLong(2)); 
            }

            rs.close();
        } catch (Exception e) {

        }

        return robot;
    }

    @Override
    public void orderMarketPrice(Robot robot, String code, int price, int qty) {
        try (
            PreparedStatement stmtInsert = connection.prepareStatement("INSERT INTO `balances` (`robot_id`, `code`, `price`, `qty`) VALUES (?, ?, ?, ?)");
            PreparedStatement stmtUpdate = connection.prepareStatement("UPDATE `robots` SET `balance`=`balance` - ? WHERE  `id`=?");
        ){
            connection.setAutoCommit(false);

            stmtInsert.setInt(1, robot.getId());
            stmtInsert.setString(2, code);
            stmtInsert.setInt(3, price);
            stmtInsert.setInt(4, qty);

            stmtUpdate.setInt(1, price * qty);
            stmtUpdate.setInt(2, robot.getId());
            
            stmtInsert.executeUpdate();
            stmtUpdate.executeUpdate();

            connection.commit();
            connection.setAutoCommit(true);
        } catch (Exception e) {

        }
    }

    @Override
    public List<Balance> getStockBalance(Robot robot) {
        List<Balance> result = new ArrayList<Balance>();
        
        try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM (SELECT `code`, SUM(`qty`) as `qty`, ROUND(SUM(IF(`qty`>0, `qty` * `price`, 0)) / SUM(IF(`qty`>0, `qty`, 0))) AS `balance` FROM balances WHERE `robot_id` = ? GROUP BY `code`) AS q WHERE `balance` > 0");){
            stmt.setInt(1, robot.getId());
    
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                result.add(new Balance(rs.getString(1), rs.getInt(3), rs.getInt(2)));
            }

            rs.close();
        } catch (Exception e) {

        }

        return result;
    }

    @Override
    public Balance getStockBalance(Robot robot, String code) {
        Balance balance = null;

        try (PreparedStatement stmt = connection.prepareStatement("SELECT * FROM (SELECT `code`, SUM(`qty`) as `qty`, ROUND(SUM(IF(`qty`>0, `qty` * `price`, 0)) / SUM(IF(`qty`>0, `qty`, 0))) AS `balance` FROM balances WHERE `robot_id` = ? AND `code` = ? GROUP BY `code`) AS q WHERE `balance` > 0");){
            stmt.setInt(1, robot.getId());
            stmt.setString(2, code);
    
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                balance = new Balance(rs.getString(1), rs.getInt(3), rs.getInt(2));
            }

            rs.close();
        } catch (Exception e) {

        }

        return balance;
    }
    
}
