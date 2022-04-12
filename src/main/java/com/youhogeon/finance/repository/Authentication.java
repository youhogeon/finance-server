package com.youhogeon.finance.repository;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Authentication {

    private String host, db, id, pw;
    private int port;

    public static Authentication make(String host, int port, String db, String id, String pw) {
        Authentication a = make(host, port, pw);
        a.setDb(db);
        a.setId(id);

        return a;
    }

    public static Authentication make(String host, int port, String pw) {
        Authentication a = new Authentication();
        a.setHost(host);
        a.setPort(port);
        a.setPw(pw);

        return a;
    }
    
}
