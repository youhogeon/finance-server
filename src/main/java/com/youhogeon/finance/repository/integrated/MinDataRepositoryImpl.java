package com.youhogeon.finance.repository.integrated;

import java.util.List;

import com.youhogeon.finance.entity.Chart;
import com.youhogeon.finance.repository.MinDataRepository;

public class MinDataRepositoryImpl implements MinDataRepository {
    MinDataRepository cacheRepo;
    MinDataRepository rdbRepo;
    
    public MinDataRepositoryImpl() {
        cacheRepo = new com.youhogeon.finance.repository.redis.MinDataRepositoryImpl();
        rdbRepo = new com.youhogeon.finance.repository.mysql.MinDataRepositoryImpl();
    }

    @Override
    public List<Chart> getDaily(String code, int date) {
        List<Chart> result = cacheRepo.getDaily(code, date);

        if (result != null) return result;

        result = rdbRepo.getDaily(code, date);
        cacheRepo.setDaily(code, date, result);

        return result;
    }

    public List<Chart> getDailyWithoutCache(String code, int date) {
        return rdbRepo.getDaily(code, date);
    }
}
