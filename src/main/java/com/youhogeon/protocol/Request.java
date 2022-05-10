package com.youhogeon.protocol;

import java.io.InputStream;
import java.util.Arrays;

import com.youhogeon.finance.helper.Buffer;

public class Request {
    byte[] header_raw, body;
    Buffer header;

    public Request(InputStream is) throws Exception {
        header_raw = new byte[30];

        if (is.read(header_raw, 0, 30) != 30) throw new Exception();
        header = new Buffer(header_raw);

        int len = getLength();
        body = new byte[len + 30];
        if (is.read(body, 30, len) != len) throw new Exception();

        body = Arrays.copyOfRange(body, 30, len + 30);
    }

    public int getProtocolVersion() {
        return (int) header.getByte(0);
    }

    public int getAPINo() {
        return (int) header.getShort(1);
    }

    public int getAPIVersion() {
        return (int) header.getByte(3);
    }

    public String getAuth() {
        return header.getString(4, 20);
    }

    public int getRID() {
        return (int) header.getShort(24);
    }

    public int getLength() {
        return (int) header.getShort(26);
    }

    public byte[] getBody() {
        return body;
    }
}
