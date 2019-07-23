package com.shuangduan.zcy.model.event;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.model.event
 * @class describe
 * @time 2019/7/18 20:21
 * @change
 * @chang time
 * @class describe
 */
public class CityEvent {

    public int[] citys;
    public String city;

    public CityEvent(int[] citys, String city) {
        this.citys = citys;
        this.city = city;
    }
}
