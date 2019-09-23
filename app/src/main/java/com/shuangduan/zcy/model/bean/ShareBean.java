package com.shuangduan.zcy.model.bean;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.model.bean
 * @class describe
 * @time 2019/8/15 16:48
 * @change
 * @chang time
 * @class describe
 */
public class ShareBean {
    /**
     * code : 200
     * msg : 成功
     * time : 1569206158
     * data : {"url":"http://xx.yijijian.com/share/web/index?invite_tel=18866619695","title":"title","des":"des","image":"http://information-api.oss-cn-qingdao.aliyuncs.com/logo_about.png"}
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
         * url : http://xx.yijijian.com/share/web/index?invite_tel=18866619695
         * title : title
         * des : des
         * image : http://information-api.oss-cn-qingdao.aliyuncs.com/logo_about.png
         */

        private String url;
        private String title;
        private String des;
        private String image;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDes() {
            return des;
        }

        public void setDes(String des) {
            this.des = des;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }
}
