package com.youhogeon.finance;

import static org.assertj.core.api.Assertions.*;

import com.youhogeon.finance.repository.integrated.MinDataRepositoryImpl;
import com.youhogeon.finance.service.AppService;

import org.junit.BeforeClass;
import org.junit.Test;

public class TestSample {
    
    @Test
    public void simpleTest(){
        assertThat("Hello, World!")
            .isNotNull()
            .startsWith("H")
            .contains("o, W")
            .endsWith("!");
    }

    @BeforeClass
    public static void 초기화() {
        AppService appService = new AppService();
        appService.run();
    }

    @Test
    public void RDB에서_1000회_가져오기() {
        MinDataRepositoryImpl test = new MinDataRepositoryImpl();
        for (int i = 0; i < 1000; i++){
            test.getDailyWithoutCache("999001", 20220410);
        }
    }

    @Test
    public void REDIS에서_1000회_가져오기() {
        MinDataRepositoryImpl test = new MinDataRepositoryImpl();
        for (int i = 0; i < 1000; i++){
            test.getDaily("999001", 20220410);
        }
    }
}
