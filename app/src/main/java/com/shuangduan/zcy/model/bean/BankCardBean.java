package com.shuangduan.zcy.model.bean;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.model.bean
 * @class describe
 * @time 2019/8/6 15:25
 * @change
 * @chang time
 * @class describe
 */
public class BankCardBean {

    /**
     * amount : 2
     * type_name : 建设银行
     * card_num : **** **** **** **** 6124
     * icon : null
     * background : null
     * last_num : 6124
     */

    private int id;
    private String type_name;
    private String card_num;
    private String icon;
    private String background;
    private String last_num;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public String getCard_num() {
        return card_num;
    }

    public void setCard_num(String card_num) {
        this.card_num = card_num;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getLast_num() {
        return last_num;
    }

    public void setLast_num(String last_num) {
        this.last_num = last_num;
    }
}
