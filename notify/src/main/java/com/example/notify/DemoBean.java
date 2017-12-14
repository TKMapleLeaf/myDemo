package com.example.notify;

/**
 * @author luhh on 2017/12/14.
 *         desc
 */

public class DemoBean {


    /**
     * data : {"cid":"2","cname":"cdemo"}
     * id : 1
     * name : demo
     */

    private DataBean data;
    private int id;
    private String name;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static class DataBean {
        /**
         * cid : 2
         * cname : cdemo
         */

        private String cid;
        private String cname;

        public String getCid() {
            return cid;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }

        public String getCname() {
            return cname;
        }

        public void setCname(String cname) {
            this.cname = cname;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "cid='" + cid + '\'' +
                    ", cname='" + cname + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "DemoBean{" +
                "data=" + data +
                ", id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
