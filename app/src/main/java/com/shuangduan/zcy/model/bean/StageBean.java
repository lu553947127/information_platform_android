package com.shuangduan.zcy.model.bean;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.model.bean
 * @class describe
 * @time 2019/7/19 15:41
 * @change
 * @chang time
 * @class describe
 */
public class StageBean extends BaseSelectorBean {
    private int id;
    private int parentid;
    private String phases_name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParentid() {
        return parentid;
    }

    public void setParentid(int parentid) {
        this.parentid = parentid;
    }

    public String getPhases_name() {
        return phases_name;
    }

    public void setPhases_name(String phases_name) {
        this.phases_name = phases_name;
    }
}
