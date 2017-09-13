package com.example.retrofitcache.bean;

/**
 * Created by Administrator on 2017/9/5.
 */

public class LoginBean {


    /**
     * response : login
     * userInfo : {"userid":"1"}
     */

    private String response;
    private UserInfoBean userInfo;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public UserInfoBean getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoBean userInfo) {
        this.userInfo = userInfo;
    }

    public static class UserInfoBean {
        /**
         * userid : 1
         */

        private String userid;

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        @Override
        public String toString() {
            return "UserInfoBean{" +
                    "userid='" + userid + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "LoginBean{" +
                "response='" + response + '\'' +
                ", userInfo=" + userInfo +
                '}';
    }
}
