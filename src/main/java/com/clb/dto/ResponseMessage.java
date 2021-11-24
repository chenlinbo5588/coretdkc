package com.clb.dto;

import java.io.Serializable;

public class ResponseMessage implements Serializable {
    int status;
    Object message;
    String errmsg;

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getMessage() {
        return this.message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public String getErrmsg() {
        return this.errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public ResponseMessage() {
    }

    public ResponseMessage(int status) {
        this.status = 0;
    }

    public ResponseMessage(Object data) {
        this.status = 0;
        this.message = data;
    }

    public ResponseMessage(int status, String err) {
        this.status = status;
        this.errmsg = err;
    }
}
