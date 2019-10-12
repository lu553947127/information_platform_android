package com.shuangduan.zcy.model.event;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.model.event
 * @ClassName: WxLoginEvent
 * @Description: java类作用描述
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/10/12 17:28
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/10/12 17:28
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class WxLoginEvent {

    private String unionid;
    private String openid;

    public WxLoginEvent(String unionid, String openid) {
        this.unionid = unionid;
        this.openid = openid;
    }

    public String getUnionId() {
        return unionid;
    }

    public String getOpenId() {
        return openid;
    }
}
