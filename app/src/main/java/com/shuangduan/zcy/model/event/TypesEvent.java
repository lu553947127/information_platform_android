package com.shuangduan.zcy.model.event;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.model.event
 * @class describe
 * @time 2019/7/31 19:35
 * @change
 * @chang time
 * @class describe
 */
public class TypesEvent {
    private String name;
    private int id;

    public TypesEvent(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}
