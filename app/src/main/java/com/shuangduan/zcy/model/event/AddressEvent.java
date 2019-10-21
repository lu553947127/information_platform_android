package com.shuangduan.zcy.model.event;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.model.event
 * @class describe
 * @time 2019/7/31 10:54
 * @change
 * @chang time
 * @class describe
 */
public class AddressEvent {
    private String province;
    private String city;
    private int provinceId;
    private int cityId;

    public AddressEvent(String province, String city, int provinceId, int cityId) {
        this.province = province;
        this.city = city;
        this.provinceId = provinceId;
        this.cityId = cityId;
    }

    public String getCity() {
        return city;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public int getCityId() {
        return cityId;
    }

    public String getProvince() {
        return province;
    }
}
