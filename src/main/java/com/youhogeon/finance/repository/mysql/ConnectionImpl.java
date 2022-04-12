package com.youhogeon.finance.repository.mysql;

import java.sql.DriverManager;

import com.youhogeon.finance.repository.Authentication;
import com.youhogeon.finance.repository.Connection;

public class ConnectionImpl implements Connection {

    public static java.sql.Connection connection;

    @Override
    public void connect(Authentication[] auth) throws Exception {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch(Exception e){
            throw new Exception("Can not find mysql jdbc driver.");
        }

        connection = DriverManager.getConnection("jdbc:mysql://"+auth[0].getHost()+":"+auth[0].getPort()+"/"+auth[0].getDb(), auth[0].getId(), auth[0].getPw());
    }

    @Override
    public void close() throws Exception {
        connection.close();
    }
    
}
