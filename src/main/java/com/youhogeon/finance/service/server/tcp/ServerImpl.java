package com.youhogeon.finance.service.server.tcp;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

import com.youhogeon.finance.controller.RequestController;
import com.youhogeon.finance.service.server.Server;

public class ServerImpl implements Server {

    ServerSocket serverSocket = null;

    @Override
    public void bind(int port) {

        try {
            serverSocket = new ServerSocket();
            serverSocket.bind(new InetSocketAddress(port));
        } catch (Exception e) {
            e.printStackTrace();
        }

        while (true) {
            try {
                Socket s =  serverSocket.accept();
                Runnable x = new RequestController(s);
                Thread t = new Thread(x);
                t.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
    }

    @Override
    public void close() {
        try{
            serverSocket.close();
        }catch(Exception e){

        }
    }
}
