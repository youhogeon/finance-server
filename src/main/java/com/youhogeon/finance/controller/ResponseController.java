package com.youhogeon.finance.controller;

import java.io.OutputStream;
import java.net.Socket;

import com.youhogeon.protocol.Response;

public class ResponseController {

    Socket s;
    Response r;

    public ResponseController(Socket s, Response r) {
        this.s = s;
        this.r = r;
    }

    public void send() {
        try {
            OutputStream os = s.getOutputStream();
            r.write(os);
            os.flush();

            os.close();
            s.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
