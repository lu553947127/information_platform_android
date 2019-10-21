package com.shuangduan.zcy.model.event;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zicaicloudplatform.model.event
 * @class describe  更新用户名通知事件
 * @time 2019/7/9 11:14
 * @change
 * @chang time
 * @class describe
 */
public class UserNameEvent extends BaseEvent {
    public String username;

    public UserNameEvent(String username) {
        this.username = username;
    }
}
