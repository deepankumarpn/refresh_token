package com.example.refreshtokenapp.data.remote;

import com.example.refreshtokenapp.data.remote.login.Data;

public class CommonResponse {

    private Object data;
    private String message;
    private int status;

    public Object getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }
}
