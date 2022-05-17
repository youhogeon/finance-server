package com.youhogeon.finance;

import java.io.*;
import java.net.*;

import com.youhogeon.finance.helper.Buffer;
import com.youhogeon.finance.service.AppService;

import static org.assertj.core.api.Assertions.*;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class SocketTest {
    static Socket socket;

    @BeforeClass
    public static void 초기화() throws Exception {
        AppService appService = new AppService();

        new Thread(() -> {
            appService.run();
        }).start();

        Thread.sleep(1000);
    }

    @Before
    public void 소켓() throws Exception{
        socket = new Socket();
        socket.connect(new InetSocketAddress("localhost", Integer.parseInt(System.getProperty("server.port"))));
    }

    @After
    public void 소켓닫기() throws Exception{
        socket.close();
    }


    @Test
    public void API_PING() throws Exception {

        short RID = (short)(Math.random() * 10 + 10);
        int time = (int)(System.currentTimeMillis() / 1000);

        Buffer b = new Buffer(30);
        b.add(1, (short)1000);
        b.add(24, RID);

        OutputStream os = socket.getOutputStream();
        os.write(b.getBuffer());
        os.flush();
        
        InputStream is = socket.getInputStream();
        byte[] bytes = new byte[100];
        is.read(bytes);
        b = new Buffer(bytes);

        os.close();
        is.close();
        //SEND PING REQUEST

        /*assertThat(b.getShort(4))
            .isEqualTo(4);
        //응답 헤더의 length값 확인
        assertThat(b.getInt(8))
        .isGreaterThanOrEqualTo(time);
        //응답 본문(=시간) 확인*/

        assertThat(b.getShort(2))
        .isEqualTo(RID);
        //응답 헤더의 RID값 확인

        assertThat(b.getByte(1))
        .isEqualTo((byte)141);
        //응답 헤더의 RID값 확인

    }
}
