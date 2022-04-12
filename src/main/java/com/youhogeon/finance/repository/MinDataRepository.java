package com.youhogeon.finance.repository;

import java.util.List;

import com.youhogeon.finance.entity.Chart;

public interface MinDataRepository {
    
    public List<Chart> getDaily(String code, int date);
    default void setDaily(String code, int date, List<Chart> obj){
        
    }
    
}
