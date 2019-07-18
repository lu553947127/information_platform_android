package com.shuangduan.zcy.model.bean;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.model.bean
 * @class describe
 * @time 2019/7/17 19:06
 * @change
 * @chang time
 * @class describe
 */
public class LoginBean {

    /**
     * user_id : 31
     * token : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJsYXQiOjE1NjM0MjcwNzYsIm5iZiI6MTU2MzQyNzA3NiwiZXhwIjoxNTY2MDE5MDc2LCJ1c2VyX2lkIjozMX0.pYxbRrFfm2p6pkMaJjJRYSAwPPd5DaQnRSqjR5PpkHY
     * tel : 15314283786
     */

    private int user_id;
    private String token;
    private String tel;
    private int info_status;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public int getInfo_status() {
        return info_status;
    }

    public void setInfo_status(int info_status) {
        this.info_status = info_status;
    }
}
