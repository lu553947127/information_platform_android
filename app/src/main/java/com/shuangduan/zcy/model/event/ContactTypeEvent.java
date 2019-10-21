package com.shuangduan.zcy.model.event;

import com.shuangduan.zcy.model.bean.ContactTypeBean;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.model.event
 * @class describe
 * @time 2019/7/31 9:22
 * @change
 * @chang time
 * @class describe
 */
public class ContactTypeEvent {
    private ContactTypeBean bean;

    public ContactTypeEvent(ContactTypeBean bean) {
        this.bean = bean;
    }

    public ContactTypeBean getBean() {
        return bean;
    }
}
