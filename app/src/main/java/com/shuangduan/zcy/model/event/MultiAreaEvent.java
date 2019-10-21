package com.shuangduan.zcy.model.event;

import java.util.List;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.model.event
 * @class describe
 * @time 2019/8/20 9:39
 * @change
 * @chang time
 * @class describe
 */
public class MultiAreaEvent {
    private List<Integer> cityResult;
    private String stringResult;

    public MultiAreaEvent(List<Integer> cityResult, String stringResult) {
        this.cityResult = cityResult;
        this.stringResult = stringResult;
    }

    public List<Integer> getCityResult() {
        return cityResult;
    }

    public String getStringResult() {
        return stringResult;
    }
}
