package com.youhogeon.finance.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.youhogeon.finance.entity.Chart;
import com.youhogeon.finance.helper.Buffer;
import com.youhogeon.finance.repository.MinDataRepository;
import com.youhogeon.finance.repository.integrated.MinDataRepositoryImpl;

public class StockService {

    public static byte[] available(Buffer req) {
        MinDataRepository minRepo = new MinDataRepositoryImpl();
        List<String> allDatas = minRepo.getAvailableCode();
        List<String> results;

        int cnt = req.getSize() / 6;

        if (cnt == 0) results = allDatas;
        else{
            results = new ArrayList<String>();
            Set<String> cache = new HashSet<String>(allDatas);

            for (int i = 0; i < req.getSize(); i += 6){
                String code = req.getString(i, 6);
                if (cache.contains(code)) results.add(code);
            }
        }

        Buffer buf = new Buffer(results.size() * 6);
        int i = 0;
        for (String code : results){
            buf.add(i, code);
            i += 6;
        }

        return buf.getBuffer();
    }

    public static byte[] currentPrice(Buffer req) {
        MinDataRepository minRepo = new MinDataRepositoryImpl();
        List<String> allDatas = minRepo.getAvailableCode();
        Map<String, Integer> currentPrice = minRepo.getCurrentPrice();
        List<String> codes;

        int cnt = req.getSize() / 6;

        if (cnt == 0) codes = allDatas;
        else{
            codes = new ArrayList<String>();

            for (int i = 0; i < req.getSize(); i += 6){
                String code = req.getString(i, 6);
                codes.add(code);
            }
        }
        //make code lists

        Buffer buf = new Buffer(codes.size() * 4);
        int i = 0;
        for (String code : codes){
            buf.add(i, currentPrice.containsKey(code) ? currentPrice.get(code) : 0);
            i += 4;
        }

        return buf.getBuffer();
    }

    public static byte[] stockData(Buffer req) {
        MinDataRepository minRepo = new MinDataRepositoryImpl();
        String code = req.getString(1, 6);
        int date = req.getInt(7);

        List<Chart> query = minRepo.getDaily(code, date);
        Buffer result = new Buffer(query.size() * 26);

        int idx = 0;
        for (Chart c : query) {
            result.add(idx, c.getDate());
            result.add(idx + 4, (short)c.getTime());
            result.add(idx + 6, c.getOpen());
            result.add(idx + 10, c.getHigh());
            result.add(idx + 14, c.getLow());
            result.add(idx + 18, c.getClose());
            result.add(idx + 22, c.getVolume());
            idx += 26;
        }

        return result.getBuffer();
    }
    
}
