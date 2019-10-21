package com.shuangduan.zcy.model.bean;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.model.bean
 * @class describe
 * @time 2019/8/5 9:50
 * @change
 * @chang time
 * @class describe
 */
public class MyPhasesBean {
    private int id;
    private String phases_name;
    private int is_select;

    public String getPhases_name() {
        return phases_name;
    }

    public void setPhases_name(String phases_name) {
        this.phases_name = phases_name;
    }

    public int getIs_select() {
        return is_select;
    }

    public void setIs_select(int is_select) {
        this.is_select = is_select;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
