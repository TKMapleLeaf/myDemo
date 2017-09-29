package com.example.moments.bean;

/**
 * Created by Administrator on 2017/9/20.
 */

public class QiniuToken {


    /**
     * response : true
     * token : ""
     */

    private boolean response;
    private String token;

    public boolean isResponse() {
        return response;
    }

    public void setResponse(boolean response) {
        this.response = response;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
