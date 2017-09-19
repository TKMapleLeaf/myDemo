package com.example.moments.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/9/14.
 */

public class DynamicBean {


    /**
     * dynamic : [{"des":"阿里巴巴公司","job":"销售主管","name":"admin","path":"http://img.ishuo.cn/doc/1608/704-160Q5092P4-51.jpg","photes":[{"dynamicid":"2","url":"http://a.hiphotos.baidu.com/image/pic/item/a6efce1b9d16fdfa0fbc1ebfb68f8c5495ee7b8b.jpg","userid":"1"}],"userid":"1"},{"des":"阿里巴巴公司","job":"研发人员","name":"test","path":"http://img5.imgtn.bdimg.com/it/u=512967538,61044361&fm=27&gp=0.jpg","photes":[{"dynamicid":"1","url":"http://f.hiphotos.baidu.com/image/pic/item/faf2b2119313b07e97f760d908d7912396dd8c9c.jpg","userid":"2"},{"dynamicid":"1","url":"http://g.hiphotos.baidu.com/image/pic/item/4b90f603738da977c76ab6fab451f8198718e39e.jpg","userid":"2"},{"dynamicid":"1","url":"http://e.hiphotos.baidu.com/image/pic/item/902397dda144ad343de8b756d4a20cf430ad858f.jpg","userid":"2"}],"userid":"2"}]
     * response : dynamic
     */

    private String response;
    private List<Dynamic> dynamic;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public List<Dynamic> getDynamic() {
        return dynamic;
    }

    public void setDynamic(List<Dynamic> dynamic) {
        this.dynamic = dynamic;
    }

    public static class Dynamic {
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
        private String content;
        private String dynaminid;
        private List<PhotoInfo> photes;

        public String getDynaminid() {
            return dynaminid;
        }

        public void setDynaminid(String dynaminid) {
            this.dynaminid = dynaminid;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

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

        public List<PhotoInfo> getPhotes() {
            return photes;
        }

        public void setPhotes(List<PhotoInfo> photes) {
            this.photes = photes;
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
        }
    }
}
