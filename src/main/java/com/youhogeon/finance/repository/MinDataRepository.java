package com.youhogeon.finance.repository;

import java.util.List;
import java.util.Map;

import com.youhogeon.finance.entity.Chart;

public interface MinDataRepository {
    
    public List<Chart> getDaily(String code, int date);
    default void setDaily(String code, int date, List<Chart> obj){
        
    }

    public List<String> getAvailableCode();

    public Map<String, Integer> getCurrentPrice();
    default void setCurrentPrice(Map<String, Integer> obj){
        
    }
    
}
