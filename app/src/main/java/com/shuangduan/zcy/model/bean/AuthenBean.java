package com.shuangduan.zcy.model.bean;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.model.bean
 * @class describe
 * @time 2019/8/6 14:25
 * @change
 * @chang time
 * @class describe
 */
public class AuthenBean {

    /**
     * card_status : 0
     * identity_image_front :
     * identity_image_reverse_site :
     */

    private int card_status;
    private String identity_image_front;
    private String identity_image_reverse_site;
    private String real_name;
    private String identity_card;

    public int getCard_status() {
        return card_status;
    }

    public void setCard_status(int card_status) {
        this.card_status = card_status;
    }

    public String getIdentity_image_front() {
        return identity_image_front;
    }

    public void setIdentity_image_front(String identity_image_front) {
        this.identity_image_front = identity_image_front;
    }

    public String getIdentity_image_reverse_site() {
        return identity_image_reverse_site;
    }

    public void setIdentity_image_reverse_site(String identity_image_reverse_site) {
        this.identity_image_reverse_site = identity_image_reverse_site;
    }

    public String getReal_name() {
        return real_name;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    public String getIdentity_card() {
        return identity_card;
    }

    public void setIdentity_card(String identity_card) {
        this.identity_card = identity_card;
    }
}
