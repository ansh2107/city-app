package com.cities.springbatchcities.dao;

public class Response {
    private String message;
    private int code;
    private String extendedMsg;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getExtendedMsg() {
        return extendedMsg;
    }

    public void setExtendedMsg(String extendedMsg) {
        this.extendedMsg = extendedMsg;
    }

    public Response(String message, int code, String extendedMsg) {
        this.message = message;
        this.code = code;
        this.extendedMsg = extendedMsg;
    }
}
