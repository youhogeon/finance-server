package com.youhogeon.finance.helper;

public class Buffer{
    private byte buf[];
    private int size;

    public Buffer(int size){
        this.buf = new byte[size];
        this.size = size;
    }

    public Buffer(byte[] buffer){
        this.buf = buffer;
        this.size = buf.length;
    }
    
    public void add(int idx, byte s){
        this.buf[idx] = s;
    }
    
    public void add(int idx, short s){
        this.buf[idx + 1] = (byte) (s >> 8);
        this.buf[idx] = (byte) (s);
    }

    public void add(int idx, float f){
        int bits = Float.floatToIntBits(f);

        this.buf[idx + 3] = (byte) (bits >> 24);
        this.buf[idx + 2] = (byte) (bits >> 16);
        this.buf[idx + 1] = (byte) (bits >> 8);
        this.buf[idx] = (byte) (bits);
    }

    public void add(int idx, int i){
        this.buf[idx + 3] = (byte) (i >> 24);
        this.buf[idx + 2] = (byte) (i >> 16);
        this.buf[idx + 1] = (byte) (i >> 8);
        this.buf[idx] = (byte) (i);
    }

    public void add(int idx, String s){
        byte[] b = s.getBytes();
        int size = b.length;

        for (int i = 0; i < size; i++) this.buf[i + idx] = b[i];
    }

    public String toHex() {
        StringBuilder result = new StringBuilder();
        for (byte aByte : this.getBuffer()) {
            result.append("\\x");
            result.append(String.format("%02x", aByte));
        }
        return result.toString();
    }

    public String toBinary(){
        byte[] bytes = getBuffer();
        StringBuilder sb = new StringBuilder(bytes.length * Byte.SIZE);
        for( int i = 0; i < Byte.SIZE * bytes.length; i++ )
            sb.append((bytes[i / Byte.SIZE] << i % Byte.SIZE & 0x80) == 0 ? '0' : '1');
        return sb.toString();
    }

    public byte[] getBuffer(){
        return this.buf;
    }

    public byte getByte(int idx){
        return buf[idx];
    }

    public int getShort(int idx){
        int s = (int) ((buf[idx + 1] & 255) << 8 | (buf[idx] & 255));

        return s;
    }

    public int getInt(int idx){
        int s = (int) ((buf[idx + 3] & 255) << 24 | (buf[idx + 2] & 255) << 16 | (buf[idx + 1] & 255) << 8 | (buf[idx] & 255));

        return s;
    }

    public String getString(int idx, int length){
        return new String(buf, idx, length);
    }

    public float getFloat(int idx){
        float f = Float.intBitsToFloat(((buf[idx - 1] & 255) << 24) | ((buf[idx - 2] & 255) << 16) | ((buf[idx - 3] & 255) << 8) | ((buf[idx - 4] & 255)));

        if (Float.isNaN(f) || Float.isInfinite(f)) f = 0;

        return f;
    }

    public int getSize(){
        return size;
    }
}