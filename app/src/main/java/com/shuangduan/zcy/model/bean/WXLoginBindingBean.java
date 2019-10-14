package com.shuangduan.zcy.model.bean;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.model.bean
 * @ClassName: WXLoginBindingBean
 * @Description: java类作用描述
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/10/12 17:19
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/10/12 17:19
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class WXLoginBindingBean {

    private int user_id;
    private String token;
    private String tel;
    private int info_status;
    private int pay_status;
    private int card_status;

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
}
