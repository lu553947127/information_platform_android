package com.shuangduan.zcy.adminManage.bean;

/**
 * @author xuyu
 * @Package com.shuangduan.zcy.adminManage.bean$
 * @class TurnoverNameBean$
 * @class describe
 * @time 2019/11/4 13:54
 * @change
 * @class describe
 */
public class TurnoverNameBean {
    public int id;
    public String name;

    public TurnoverNameBean(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "TurnoverNameBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
