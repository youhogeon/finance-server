package com.youhogeon.finance.repository.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.youhogeon.finance.entity.Chart;
import com.youhogeon.finance.repository.MinDataRepository;

public class MinDataRepositoryImpl implements MinDataRepository {

    private java.sql.Connection connection = ConnectionImpl.connection;

    @Override
    public List<Chart> getDaily(String code, int date) {
        List<Chart> result = new ArrayList<Chart>();
        
        try (PreparedStatement stmt = connection.prepareStatement("SELECT `time`, `open`, `high`, `low`, `close`, `volume` FROM min_datas WHERE `code` = ? AND `date` = ? ORDER BY time");){
            stmt.setString(1, code);
            stmt.setInt(2, date);
    
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                result.add(new Chart(date, rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getInt(5), rs.getInt(6)));
            }

            rs.close();
        } catch (Exception e) {

        }

        return result;
    }

    @Override
    public List<String> getAvailableCode() {
        List<String> result = new ArrayList<String>();
        
        try (PreparedStatement stmt = connection.prepareStatement("SELECT `code` FROM stocks");){
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                result.add(rs.getString(1));
            }

            rs.close();
        } catch (Exception e) {

        }

        return result;
    }

    @Override
    public Map<String, Integer> getCurrentPrice() {
        Map<String, Integer> result = new HashMap<String, Integer>();
        
        try (PreparedStatement stmt = connection.prepareStatement("SELECT A.code, A.close FROM `min_datas` AS `A` INNER JOIN (SELECT `date`, `time` FROM `min_datas` WHERE `code`='999001' ORDER BY `date` DESC, `time` DESC LIMIT 0, 1) AS `B` ON `A`.`date` = `B`.`date` AND `A`.`time` = `B`.`time`");){
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                result.put(rs.getString(1), rs.getInt(2));
            }

            rs.close();
        } catch (Exception e) {

        }

        return result;
    }
    
}
