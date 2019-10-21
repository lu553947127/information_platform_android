package com.shuangduan.zcy.model.event;

import com.blankj.utilcode.util.StringUtils;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.model.event
 * @class describe
 * @time 2019/8/2 10:58
 * @change
 * @chang time
 * @class describe
 */
public class ProjectNameEvent {
    private String name;
    private int id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ProjectNameEvent(String name, int id) {
        this.name = name;
        this.id = id;
    }
}
