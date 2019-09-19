package com.shuangduan.zcy.model.event;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.model.event
 * @ClassName: CoinEvent
 * @Description: java类作用描述
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/9/19 10:40
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/9/19 10:40
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class CoinEvent extends BaseEvent {

    public String coin;

    public CoinEvent(String coin) {
        this.coin = coin;
    }
}
