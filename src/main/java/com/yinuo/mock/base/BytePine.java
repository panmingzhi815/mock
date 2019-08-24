package com.yinuo.mock.base;

public class BytePine {
    private final StringBuilder stringBuilder;

    public BytePine(int capacity) {
        this.stringBuilder = new StringBuilder(capacity);
    }

    public BytePine add(String byteStr){
        return this;
    }

    public BytePine add(byte b){
        return this;
    }

    public String getStr(){
        return stringBuilder.toString();
    }

    public byte[] getBytes(){
        return new byte[0];
    }
}
