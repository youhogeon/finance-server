package com.youhogeon.finance.repository;

public interface Connection {

    public void connect(Authentication[] auth) throws Exception;
    public void close() throws Exception;

}
