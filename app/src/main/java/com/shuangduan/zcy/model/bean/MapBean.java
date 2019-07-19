package com.shuangduan.zcy.model.bean;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.model.bean
 * @class describe
 * @time 2019/7/18 20:50
 * @change
 * @chang time
 * @class describe
 */
public class MapBean {

    /**
     * id : 2
     * longitude : 117.143219
     * latitude : 36.683159
     * title : 标题
     * intro : 介绍
     * phases : 工程构思
     * subscription_num : 0
     * update_time : 2019-07-19 11:09:18
     */

    private int id;
    private double longitude;
    private double latitude;
    private String title;
    private String intro;
    private String phases;
    private int subscription_num;
    private String update_time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getPhases() {
        return phases;
    }

    public void setPhases(String phases) {
        this.phases = phases;
    }

    public int getSubscription_num() {
        return subscription_num;
    }

    public void setSubscription_num(int subscription_num) {
        this.subscription_num = subscription_num;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }
}
