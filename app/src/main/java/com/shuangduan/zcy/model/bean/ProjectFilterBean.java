package com.shuangduan.zcy.model.bean;

import java.util.List;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.model.bean
 * @class describe
 * @time 2019/8/12 16:20
 * @change
 * @chang time
 * @class describe
 */
public class ProjectFilterBean {
    private List<Integer> province;
    private List<Integer> city;
    private List<Integer> type;
    private List<Integer> phases;

    public List<Integer> getProvince() {
        return province;
    }

    public void setProvince(List<Integer> province) {
        this.province = province;
    }

    public List<Integer> getCity() {
        return city;
    }

    public void setCity(List<Integer> city) {
        this.city = city;
    }

    public List<Integer> getType() {
        return type;
    }

    public void setType(List<Integer> type) {
        this.type = type;
    }

    public List<Integer> getPhases() {
        return phases;
    }

    public void setPhases(List<Integer> phases) {
        this.phases = phases;
    }
}
