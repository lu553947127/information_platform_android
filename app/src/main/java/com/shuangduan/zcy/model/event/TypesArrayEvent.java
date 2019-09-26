package com.shuangduan.zcy.model.event;

/**
 * @author xuyu
 * @Package com.shuangduan.zcy.model.event$
 * @class TypesArrayEvent$
 * @class describe
 * @time 2019/9/26 18:08
 * @change
 * @class describe
 */
public class TypesArrayEvent {

    private String name;
    private String id[];

    public TypesArrayEvent(String name, String[] id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String[] getId() {
        return id;
    }
}
