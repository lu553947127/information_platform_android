package com.shuangduan.zcy.model.event;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.model.event
 * @class describe
 * @time 2019/7/18 20:21
 * @change
 * @chang time
 * @class describe
 */
public class CityEvent {

    public int[] business_city;
    public String city;

    public CityEvent(int[] citys, String city) {
        this.business_city = citys;
        this.city = city;
    }
}
