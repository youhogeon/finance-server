package com.youhogeon.finance;

import static org.assertj.core.api.Assertions.*;

import com.youhogeon.finance.repository.MinDataRepository;
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
    public void 데이터_가져오기() {
        MinDataRepository test = new MinDataRepositoryImpl();
        for (int i = 0; i < 2; i++){
            test.getDaily("999001", 20220410);
        }
    }
}
