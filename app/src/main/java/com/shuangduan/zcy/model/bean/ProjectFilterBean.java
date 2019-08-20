package com.shuangduan.zcy.model.bean;

import java.util.List;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.model.bean
 * @class describe
 * @time 2019/8/12 16:20
 * @change
 * @chang time
 * @class describe
 */
public class ProjectFilterBean {
    private List<Integer> city;
    private List<Integer> type;
    private List<Integer> phases;

    public ProjectFilterBean(List<Integer> city, List<Integer> type, List<Integer> phases) {
        this.city = city;
        this.type = type;
        this.phases = phases;
    }
}
