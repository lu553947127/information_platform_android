package com.shuangduan.zcy.model.bean;

public class IMWechatUserInfoBean {
    /**
     * code : 200
     * msg : 成功
     * time : 1567837270
     * data : {"userId":31,"portraitUri":"http://information-api.oss-cn-qingdao.aliyuncs.com/38d07a6a0d112c65e2671849d502a02a.jpg","name":"李嘉诚"}
     */

    private String code;
    private String msg;
    private int time;
    private DataBean data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * userId : 31
         * portraitUri : http://information-api.oss-cn-qingdao.aliyuncs.com/38d07a6a0d112c65e2671849d502a02a.jpg
         * name : 李嘉诚
         */

        private String userId;
        private String portraitUri;
        private String name;
        private String company;
        private String position;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getPortraitUri() {
            return portraitUri;
        }

        public void setPortraitUri(String portraitUri) {
            this.portraitUri = portraitUri;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }
    }
}
