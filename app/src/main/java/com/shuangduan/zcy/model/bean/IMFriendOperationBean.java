package com.shuangduan.zcy.model.bean;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.model.bean
 * @class describe
 * @time 2019/9/9 15:54
 * @change
 * @chang time
 * @class describe
 */
public class IMFriendOperationBean {
    /**
     * code : 200
     * msg : 您已拒绝该用户的好友申请
     * time : 1568019707
     * data : {"apply_user_id":"","apply_count":null}
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
         * apply_user_id :
         * apply_count : null
         */

        private String apply_user_id;
        private Object apply_count;

        public String getApply_user_id() {
            return apply_user_id;
        }

        public void setApply_user_id(String apply_user_id) {
            this.apply_user_id = apply_user_id;
        }

        public Object getApply_count() {
            return apply_count;
        }

        public void setApply_count(Object apply_count) {
            this.apply_count = apply_count;
        }
    }
}
