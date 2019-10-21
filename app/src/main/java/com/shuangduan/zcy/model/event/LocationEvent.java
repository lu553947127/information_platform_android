package com.shuangduan.zcy.model.event;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.model.event
 * @class describe
 * @time 2019/7/30 11:20
 * @change
 * @chang time
 * @class describe
 */
public class LocationEvent {
    private String province;
    private String city;
    private int provinceId;
    private int cityId;
    private double latitude;
    private double longitude;

    public LocationEvent(String province, String city, int provinceId, int cityId, double latitude, double longitude) {
        this.province = province;
        this.city = city;
        this.provinceId = provinceId;
        this.cityId = cityId;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getProvince() {
        return province;
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

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
