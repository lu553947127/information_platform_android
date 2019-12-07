package com.shuangduan.zcy.model.bean;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.model.bean
 * @class describe
 * @time 2019/9/9 13:56
 * @change
 * @chang time
 * @class describe
 */
public class IMFriendApplyCountBean {

    private int count;//通讯录有新的好友提醒数量
    private int subscribe;//订阅信息数量
    private int material;//闲置提醒数量
    private int order;//订单提醒数量

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(int subscribe) {
        this.subscribe = subscribe;
    }

    public int getMaterial() {
        return material;
    }

    public void setMaterial(int material) {
        this.material = material;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
