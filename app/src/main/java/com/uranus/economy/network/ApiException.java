package com.uranus.economy.network;

public class ApiException extends RuntimeException {

    private static final long serialVersionUID = -5353528843278696989L;

    private int code;

    public ApiException(int code,String message) {
        super(message);
        this.code = code;
    }


    public int getCode() {
        return code;
    }
}