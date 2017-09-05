package com.example.myapplicationdemo.bean;

/**
 * Created by Administrator on 2017/9/5.
 */

public class LoginBean {


    /**
     * error : false
     * msg : {"uid":"4","email":"12@12.com","username":"lhh","phone":"13800000001","address":"1","creattime":"1491653817","logintime":"1504599072","usertype":"0","score":"0","cost":"0.00","loginip":"58.59.202.147","logo":"/upload/user/20170408213705302.jpg","status":"0","safepwd":null,"group":"5","is_bang":"0","parent_id":"0","total":"0","admin_id":"450100","sex":"0","wxopenid":"","shopid":"0","temp_password":null,"shopcost":"0.00","backacount":null,"md_flag":"0"}
     */

    private boolean error;
    private MsgBean msg;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public MsgBean getMsg() {
        return msg;
    }

    public void setMsg(MsgBean msg) {
        this.msg = msg;
    }

    public static class MsgBean {
        /**
         * uid : 4
         * email : 12@12.com
         * username : lhh
         * phone : 13800000001
         * address : 1
         * creattime : 1491653817
         * logintime : 1504599072
         * usertype : 0
         * score : 0
         * cost : 0.00
         * loginip : 58.59.202.147
         * logo : /upload/user/20170408213705302.jpg
         * status : 0
         * safepwd : null
         * group : 5
         * is_bang : 0
         * parent_id : 0
         * total : 0
         * admin_id : 450100
         * sex : 0
         * wxopenid :
         * shopid : 0
         * temp_password : null
         * shopcost : 0.00
         * backacount : null
         * md_flag : 0
         */

        private String uid;
        private String email;
        private String username;
        private String phone;
        private String address;
        private String creattime;
        private String logintime;
        private String usertype;
        private String score;
        private String cost;
        private String loginip;
        private String logo;
        private String status;
        private Object safepwd;
        private String group;
        private String is_bang;
        private String parent_id;
        private String total;
        private String admin_id;
        private String sex;
        private String wxopenid;
        private String shopid;
        private Object temp_password;
        private String shopcost;
        private Object backacount;
        private String md_flag;

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getCreattime() {
            return creattime;
        }

        public void setCreattime(String creattime) {
            this.creattime = creattime;
        }

        public String getLogintime() {
            return logintime;
        }

        public void setLogintime(String logintime) {
            this.logintime = logintime;
        }

        public String getUsertype() {
            return usertype;
        }

        public void setUsertype(String usertype) {
            this.usertype = usertype;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public String getCost() {
            return cost;
        }

        public void setCost(String cost) {
            this.cost = cost;
        }

        public String getLoginip() {
            return loginip;
        }

        public void setLoginip(String loginip) {
            this.loginip = loginip;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Object getSafepwd() {
            return safepwd;
        }

        public void setSafepwd(Object safepwd) {
            this.safepwd = safepwd;
        }

        public String getGroup() {
            return group;
        }

        public void setGroup(String group) {
            this.group = group;
        }

        public String getIs_bang() {
            return is_bang;
        }

        public void setIs_bang(String is_bang) {
            this.is_bang = is_bang;
        }

        public String getParent_id() {
            return parent_id;
        }

        public void setParent_id(String parent_id) {
            this.parent_id = parent_id;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getAdmin_id() {
            return admin_id;
        }

        public void setAdmin_id(String admin_id) {
            this.admin_id = admin_id;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getWxopenid() {
            return wxopenid;
        }

        public void setWxopenid(String wxopenid) {
            this.wxopenid = wxopenid;
        }

        public String getShopid() {
            return shopid;
        }

        public void setShopid(String shopid) {
            this.shopid = shopid;
        }

        public Object getTemp_password() {
            return temp_password;
        }

        public void setTemp_password(Object temp_password) {
            this.temp_password = temp_password;
        }

        public String getShopcost() {
            return shopcost;
        }

        public void setShopcost(String shopcost) {
            this.shopcost = shopcost;
        }

        public Object getBackacount() {
            return backacount;
        }

        public void setBackacount(Object backacount) {
            this.backacount = backacount;
        }

        public String getMd_flag() {
            return md_flag;
        }

        public void setMd_flag(String md_flag) {
            this.md_flag = md_flag;
        }
    }
}
