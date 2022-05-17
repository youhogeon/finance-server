package com.youhogeon.finance;

import com.youhogeon.finance.service.AppService;

public class App {
    public static void main(String[] args) {
        AppService appService = new AppService();
        appService.run();
        appService.close();
    }

}