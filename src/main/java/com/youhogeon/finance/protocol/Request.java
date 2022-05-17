package com.youhogeon.finance.protocol;

import java.io.InputStream;
import java.util.Arrays;

import com.youhogeon.finance.helper.Buffer;

public class Request {
    byte[] header_raw, body_raw;
    Buffer header, body;

    public Request(InputStream is) throws Exception {
        header_raw = new byte[30];

        if (is.read(header_raw, 0, 30) != 30) throw new Exception();
        header = new Buffer(header_raw);

        int len = getLength();
        body_raw = new byte[len + 30];
        if (is.read(body_raw, 30, len) != len) throw new Exception();

        body_raw = Arrays.copyOfRange(body_raw, 30, len + 30);
        body = new Buffer(body_raw);
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
        StringBuilder result = new StringBuilder();

        for (int i = 4; i < 24; i++) result.append(String.format("%02x", header_raw[i]));

        return result.toString();
    }

    public int getRID() {
        return (int) header.getShort(24);
    }

    public int getLength() {
        return (int) header.getShort(26);
    }

    public Buffer getBody() {
        return body;
    }
}
