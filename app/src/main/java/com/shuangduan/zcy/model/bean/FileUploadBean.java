package com.shuangduan.zcy.model.bean;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.model.bean
 * @ClassName: FileUploadBean
 * @Description: java类作用描述
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/9/21 13:59
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/9/21 13:59
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class FileUploadBean {
    /**
     * code : 200
     * msg : 成功
     * time : 1569046708
     * data : {"image_id":"1271","source":"http://information-api.oss-cn-qingdao.aliyuncs.com/d13f0468c5f32ba7edf00a0f6ec5b82e.jpg","thumbnail":"http://information-api.oss-cn-qingdao.aliyuncs.com/d13f0468c5f32ba7edf00a0f6ec5b82e.jpg?x-oss-process=image/resize,m_lfit,h_150,w_150"}
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
         * image_id : 1271
         * source : http://information-api.oss-cn-qingdao.aliyuncs.com/d13f0468c5f32ba7edf00a0f6ec5b82e.jpg
         * thumbnail : http://information-api.oss-cn-qingdao.aliyuncs.com/d13f0468c5f32ba7edf00a0f6ec5b82e.jpg?x-oss-process=image/resize,m_lfit,h_150,w_150
         */

        private String image_id;
        private String source;
        private String thumbnail;

        public String getImage_id() {
            return image_id;
        }

        public void setImage_id(String image_id) {
            this.image_id = image_id;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }
    }
}
