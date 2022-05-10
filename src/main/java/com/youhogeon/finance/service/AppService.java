package com.youhogeon.finance.service;

import java.io.BufferedReader;
import java.io.FileReader;

import com.youhogeon.finance.repository.*;
import com.youhogeon.finance.repository.integrated.*;
import com.youhogeon.finance.service.server.*;
import com.youhogeon.finance.service.server.tcp.*;

public class AppService {

    Connection connection;
    Server s;
    
    public AppService() {
        readEnv();
        connectDB();
    }

    public void run() {
        runServer();
    }

    public void close() {
        closeDB();
        closeServer();
    }

    public void readEnv() {
        try{
            BufferedReader reader = new BufferedReader(new FileReader(".env"));

            String str;
            while ((str = reader.readLine()) != null){
                String[] arr = str.split("=");
                if (arr.length != 2) continue;
                System.setProperty(arr[0].trim(), arr[1].trim());
            }

            reader.close();
        }catch (Exception e){
            System.out.println("Can not read .env file.");
            return;
        }
    }

    public void connectDB() {
        connection = new ConnectionImpl();

        try{
            Authentication rdsAuth = Authentication.make(System.getProperty("mysql.host"), Integer.parseInt(System.getProperty("mysql.port")), System.getProperty("mysql.db"), System.getProperty("mysql.id"), System.getProperty("mysql.pw"));
            Authentication cacheAuth = Authentication.make(System.getProperty("redis.host"), Integer.parseInt(System.getProperty("redis.port")), System.getProperty("redis.pw"));
            connection.connect(new Authentication[]{rdsAuth, cacheAuth});
        }catch(Exception e){
            System.out.println(e.toString());
        }
    }

    private void closeDB() {
        try{
            connection.close();
        }catch(Exception e){

        }
    }

    private void runServer() {
        s = new ServerImpl();
        s.bind(Integer.parseInt(System.getProperty("server.port")));
    }

    private void closeServer() {
        s.close();
    }

}
