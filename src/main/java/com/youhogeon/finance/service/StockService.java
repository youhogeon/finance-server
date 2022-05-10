package com.youhogeon.finance.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.youhogeon.finance.helper.Buffer;
import com.youhogeon.finance.repository.MinDataRepository;
import com.youhogeon.finance.repository.integrated.MinDataRepositoryImpl;

public class StockService {

    public static byte[] available(byte[] body) {
        MinDataRepository minRepo = new MinDataRepositoryImpl();
        List<String> allDatas = minRepo.getAvailableCode();
        List<String> results;
        Buffer req = new Buffer(body);

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

    public static byte[] currentPrice(byte[] body) {
        MinDataRepository minRepo = new MinDataRepositoryImpl();
        List<String> allDatas = minRepo.getAvailableCode();
        Map<String, Integer> currentPrice = minRepo.getCurrentPrice();
        List<String> codes;
        Buffer req = new Buffer(body);

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
    
}
