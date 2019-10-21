package com.shuangduan.zcy.model.bean;

import java.util.List;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.model.bean
 * @class describe
 * @time 2019/7/19 18:56
 * @change
 * @chang time
 * @class describe
 */
public class TypeBean extends BaseSelectorBean {
    private int id;
    private int parentid;
    private String catname;
    private List<TypeBean> childList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParentid() {
        return parentid;
    }

    public void setParentid(int parentid) {
        this.parentid = parentid;
    }

    public String getCatname() {
        return catname;
    }

    public void setCatname(String catname) {
        this.catname = catname;
    }

    public List<TypeBean> getChildList() {
        return childList;
    }

    public void setChildList(List<TypeBean> childList) {
        this.childList = childList;
    }

}
