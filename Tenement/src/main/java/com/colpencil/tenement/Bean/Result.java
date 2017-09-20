package com.colpencil.tenement.Bean;

public class Result {

    public int code;
    public String message;
    public String data;

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
