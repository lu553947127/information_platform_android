package com.shuangduan.zcy.model.event;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.model.event
 * @ClassName: WxBindEvent
 * @Description: java类作用描述
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/12/24 11:46
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/12/24 11:46
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class WxBindEvent {

    private String code;

    public WxBindEvent(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
