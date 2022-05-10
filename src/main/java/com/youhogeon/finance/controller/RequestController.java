package com.youhogeon.finance.controller;

import java.io.InputStream;
import java.net.Socket;

import com.youhogeon.finance.service.StatusService;
import com.youhogeon.finance.service.StockService;
import com.youhogeon.protocol.Request;
import com.youhogeon.protocol.Response;

public class RequestController implements Runnable {

    public static final int API_PING = 1000,
                            API_MARKET_OPEN = 1001,
                            API_CHECK_STOCK_CODE_AVAILABLE = 2000,
                            API_CURRENT_PRICE = 2001,
                            API_STOCK_DATA = 2002,
                            API_GET_BALANCE = 3000,
                            API_GET_STOCK = 3001,
                            API_ORDER_STOCK = 3002;

    Socket socket = null;
    InputStream is;
    Response res;
    ResponseController resCont;

    public RequestController(Socket s) {
        this.socket = s;
        res = new Response();
        resCont = new ResponseController(socket, res);
    }

    public void run() {
        try {
            Request req = new Request(socket.getInputStream());
            res.setRID(req.getRID());

            switch (req.getAPINo()) {
                case API_PING:
                    res.setBody(StatusService.ping());
                    break;
                case API_MARKET_OPEN:
                    res.setBody(StatusService.ping());
                    if (!StatusService.marketOpen()) throw new Exception("100");
                    break;
                case API_CHECK_STOCK_CODE_AVAILABLE:
                        res.setBody(StockService.available(req.getBody()));
                        break;
                case API_CURRENT_PRICE:
                    if (req.getLength() < 6) throw new Exception("140");

                    res.setBody(StockService.currentPrice(req.getBody()));
                    break;
                default:
                    throw new Exception("150");
            }

            res.setCode(200);
            resCont.send();
        } catch (Exception e){
            int code = 150;
            if (e.getMessage().length() == 3) code = Integer.parseInt(e.getMessage());

            res.setCode(code);
            resCont.send();
        }
    }
}
