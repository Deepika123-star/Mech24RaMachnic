package com.smartwebarts.mech24ra.retrofit;

public class MessageModel {

    private String msg;
    private String message;
    private String status;

    public String getMsg() {
        return msg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
