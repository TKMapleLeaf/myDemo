package com.example.retrofitcache.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/9/11.
 */

public class HomeBean {


    /**
     * dynamic : [{"des":"阿里巴巴公司","job":"销售主管","name":"admin","path":"http://img.ishuo.cn/doc/1608/704-160Q5092P4-51.jpg","photes":[{"dynamicid":"2","url":"http://a.hiphotos.baidu.com/image/pic/item/a6efce1b9d16fdfa0fbc1ebfb68f8c5495ee7b8b.jpg","userid":"1"}],"userid":"1"},{"des":"京东公司","job":"研发人员","name":"test","path":"http://img5.imgtn.bdimg.com/it/u=512967538,61044361&fm=27&gp=0.jpg","photes":[{"dynamicid":"1","url":"http://f.hiphotos.baidu.com/image/pic/item/faf2b2119313b07e97f760d908d7912396dd8c9c.jpg","userid":"2"},{"dynamicid":"1","url":"http://g.hiphotos.baidu.com/image/pic/item/4b90f603738da977c76ab6fab451f8198718e39e.jpg","userid":"2"},{"dynamicid":"1","url":"http://e.hiphotos.baidu.com/image/pic/item/902397dda144ad343de8b756d4a20cf430ad858f.jpg","userid":"2"}],"userid":"2"},{"des":"腾讯公司","job":"研发人员","name":"test1","path":"http://www.ld12.com/upimg358/20160130/19075626962282.jpg","photes":[{"dynamicid":"1","url":"","userid":"3"}],"userid":"3"},{"des":"阿里巴巴公司","job":"研发人员","name":"test999","path":"http://www.fzlqqqm.com/uploads/allimg/20150729/201507291603115441.jpg","photes":[{"dynamicid":"1","url":"http://c.hiphotos.baidu.com/image/pic/item/7dd98d1001e939011b9c86d07fec54e737d19645.jpg","userid":"5"},{"dynamicid":"1","url":"http://pica.nipic.com/2007-10-17/20071017111345564_2.jpg","userid":"5"}],"userid":"5"},{"des":"帝国集团","job":"研发人员","name":"test2","path":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1505987370&di=932c4e0c496f2f99dd5f21ed55d31be9&imgtype=jpg&er=1&src=http%3A%2F%2Fi0.hdslb.com%2Fvideo%2F44%2F44dba6fb6be69f44ab60e68c415ba295.jpg","photes":[{"dynamicid":"1","url":"http://pic31.nipic.com/20130624/8821914_104949466000_2.jpg","userid":"4"},{"dynamicid":"1","url":"http://pic4.nipic.com/20091203/1295091_123813163959_2.jpg","userid":"4"},{"dynamicid":"1","url":"http://pic4.nipic.com/20091101/3672704_160309066949_2.jpg","userid":"4"},{"dynamicid":"1","url":"http://pica.nipic.com/2007-10-17/20071017111345564_2.jpg","userid":"4"},{"dynamicid":"1","url":"http://c.hiphotos.baidu.com/image/pic/item/7dd98d1001e939011b9c86d07fec54e737d19645.jpg","userid":"4"},{"dynamicid":"1","url":"http://b.hiphotos.baidu.com/image/pic/item/a71ea8d3fd1f4134e61e0f90211f95cad1c85e36.jpg","userid":"4"},{"dynamicid":"1","url":"http://a.hiphotos.baidu.com/image/pic/item/a6efce1b9d16fdfa0fbc1ebfb68f8c5495ee7b8b.jpg","userid":"4"},{"dynamicid":"1","url":"http://e.hiphotos.baidu.com/image/pic/item/902397dda144ad343de8b756d4a20cf430ad858f.jpg","userid":"4"},{"dynamicid":"1","url":"http://g.hiphotos.baidu.com/image/pic/item/4b90f603738da977c76ab6fab451f8198718e39e.jpg","userid":"4"}],"userid":"4"}]
     * response : dynamic
     */

    private String response;
    private List<DynamicBean> dynamic;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public List<DynamicBean> getDynamic() {
        return dynamic;
    }

    public void setDynamic(List<DynamicBean> dynamic) {
        this.dynamic = dynamic;
    }

    public static class DynamicBean {
        /**
         * des : 阿里巴巴公司
         * job : 销售主管
         * name : admin
         * path : http://img.ishuo.cn/doc/1608/704-160Q5092P4-51.jpg
         * photes : [{"dynamicid":"2","url":"http://a.hiphotos.baidu.com/image/pic/item/a6efce1b9d16fdfa0fbc1ebfb68f8c5495ee7b8b.jpg","userid":"1"}]
         * userid : 1
         */

        private String des;
        private String job;
        private String name;
        private String path;
        private String userid;
        private List<PhotesBean> photes;

        public String getDes() {
            return des;
        }

        public void setDes(String des) {
            this.des = des;
        }

        public String getJob() {
            return job;
        }

        public void setJob(String job) {
            this.job = job;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public List<PhotesBean> getPhotes() {
            return photes;
        }

        public void setPhotes(List<PhotesBean> photes) {
            this.photes = photes;
        }

        @Override
        public String toString() {
            return "DynamicBean{" +
                    "des='" + des + '\'' +
                    ", job='" + job + '\'' +
                    ", name='" + name + '\'' +
                    ", path='" + path + '\'' +
                    ", userid='" + userid + '\'' +
                    ", photes=" + photes +
                    '}';
        }

        public static class PhotesBean {
            /**
             * dynamicid : 2
             * url : http://a.hiphotos.baidu.com/image/pic/item/a6efce1b9d16fdfa0fbc1ebfb68f8c5495ee7b8b.jpg
             * userid : 1
             */

            private String dynamicid;
            private String url;
            private String userid;

            public String getDynamicid() {
                return dynamicid;
            }

            public void setDynamicid(String dynamicid) {
                this.dynamicid = dynamicid;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getUserid() {
                return userid;
            }

            public void setUserid(String userid) {
                this.userid = userid;
            }

            @Override
            public String toString() {
                return "PhotesBean{" +
                        "dynamicid='" + dynamicid + '\'' +
                        ", url='" + url + '\'' +
                        ", userid='" + userid + '\'' +
                        '}';
            }
        }
    }

    @Override
    public String toString() {
        return "HomeBean{" +
                "response='" + response + '\'' +
                ", dynamic=" + dynamic +
                '}';
    }
}
