package com.shuangduan.zcy.model.bean;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.model.bean
 * @ClassName: WXLoginVerificationBean
 * @Description: java类作用描述
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/10/12 17:10
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/10/12 17:10
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class WXLoginVerificationBean {

    String wechat_status;
    String user_id;
    String token;
    String tel;
    String info_status;
    String pay_status;
    String card_status;

    public String getWechat_status() {
        return wechat_status;
    }

    public void setWechat_status(String wechat_status) {
        this.wechat_status = wechat_status;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
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

    public String getInfo_status() {
        return info_status;
    }

    public void setInfo_status(String info_status) {
        this.info_status = info_status;
    }

    public String getPay_status() {
        return pay_status;
    }

    public void setPay_status(String pay_status) {
        this.pay_status = pay_status;
    }

    public String getCard_status() {
        return card_status;
    }

    public void setCard_status(String card_status) {
        this.card_status = card_status;
    }
}
