package com.shuangduan.zcy.model.event;

import java.util.List;

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
    private List<Integer> id;

    public TypesArrayEvent(String name,List<Integer>  id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public List<Integer> getId() {
        return id;
    }
}
