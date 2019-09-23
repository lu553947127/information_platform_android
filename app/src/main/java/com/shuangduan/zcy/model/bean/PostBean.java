package com.shuangduan.zcy.model.bean;

/**
 * @author xuyu
 * @Package com.shuangduan.zcy.model.bean$
 * @class PostBean$
 * @class describe
 * @time 2019/9/23 19:48
 * @change
 * @class describe
 */
public class PostBean {
    public String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "PostBean{" +
                "name='" + name + '\'' +
                '}';
    }
}
