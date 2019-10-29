package com.shuangduan.zcy.adminManage.event;

import com.shuangduan.zcy.model.event.BaseEvent;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.adminManage.event
 * @ClassName: TurnoverEvent
 * @Description: java类作用描述
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/10/29 17:29
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/10/29 17:29
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class TurnoverEvent extends BaseEvent {

    public int company_id;
    public String company_name;

    public TurnoverEvent(int company_id, String company_name) {
        this.company_id = company_id;
        this.company_name = company_name;
    }

    public int getCompany_id() {
        return company_id;
    }

    public void setCompany_id(int company_id) {
        this.company_id = company_id;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }
}
