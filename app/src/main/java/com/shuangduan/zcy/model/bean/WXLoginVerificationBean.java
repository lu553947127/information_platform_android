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

    private int wechat_status;
    private int user_id;
    private String token;
    private String tel;
    private int info_status;
    private int pay_status;
    private int card_status;
    private String unionid;
    private String openid;
    private String headimgurl;
    private String nickname;

    public int getWechat_status() {
        return wechat_status;
    }

    public void setWechat_status(int wechat_status) {
        this.wechat_status = wechat_status;
    }

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

    public int getPay_status() {
        return pay_status;
    }

    public void setPay_status(int pay_status) {
        this.pay_status = pay_status;
    }

    public int getCard_status() {
        return card_status;
    }

    public void setCard_status(int card_status) {
        this.card_status = card_status;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
