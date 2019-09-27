package com.shuangduan.zcy.model.bean;

import java.util.List;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.model.bean
 * @class describe
 * @time 2019/8/1 16:11
 * @change
 * @chang time
 * @class describe
 */
public class ContactListBean {
    private List<Integer> type;
    private List<ContactBean> contact;

    public List<Integer> getType() {
        return type;
    }

    public void setType(List<Integer> type) {
        this.type = type;
    }

    public List<ContactBean> getContact() {
        return contact;
    }

    public void setContact(List<ContactBean> contact) {
        this.contact = contact;
    }
}
