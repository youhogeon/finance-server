package com.youhogeon.finance.repository.integrated;

import com.youhogeon.finance.repository.Authentication;
import com.youhogeon.finance.repository.Connection;

public class ConnectionImpl implements Connection {

    Connection rdbCon, cacheCon;

    @Override
    public void connect(Authentication[] auth) throws Exception {
        Connection rdbCon = new com.youhogeon.finance.repository.mysql.ConnectionImpl();
        Connection cacheCon = new com.youhogeon.finance.repository.redis.ConnectionImpl();

        rdbCon.connect(new Authentication[]{auth[0]});
        cacheCon.connect(new Authentication[]{auth[1]});
    }

    @Override
    public void close() throws Exception {
        rdbCon.close();
        cacheCon.close();
    }
    
}
