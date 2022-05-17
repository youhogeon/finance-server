package com.youhogeon.finance.protocol;

import java.io.OutputStream;

import com.youhogeon.finance.helper.Buffer;

public class Response {
    private byte[] header_raw, body;
    Buffer header;

    public Response() {
        header_raw = new byte[8];
        header = new Buffer(header_raw);

        setProtocolVersion(1);
        setCode(150);
    }

    public void setProtocolVersion(int version) {
        header.add(0, (byte)version);
    }
    
    public void setCode(int code) {
        header.add(1, (byte)code);
    }
    
    public void setRID(int RID) {
        header.add(2, (short)RID);
    }
    
    private void setLength() {
        header.add(4, body.length);
    }

    public void setBody(byte[] body){
        this.body = body;
        setLength();
    }

    public void setBody(Buffer body){
        this.body = body.getBuffer();
        setLength();
    }

    public void write(OutputStream os) throws Exception{
        if (body == null){
            os.write(header_raw);
            return;
        }

        byte[] combined = new byte[header_raw.length + body.length];
        System.arraycopy(header_raw, 0, combined, 0, header_raw.length);
        System.arraycopy(body, 0, combined, header_raw.length, body.length);

        os.write(combined);
    }
}
