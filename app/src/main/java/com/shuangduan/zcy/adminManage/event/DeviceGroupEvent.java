package com.shuangduan.zcy.adminManage.event;

import com.shuangduan.zcy.model.event.BaseEvent;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.adminManage.event
 * @ClassName: DeviceGroupEvent
 * @Description: java类作用描述
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/11/6 14:26
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/11/6 14:26
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class DeviceGroupEvent extends BaseEvent {

    public int material_id;
    public String material_name;

    public DeviceGroupEvent(int material_id, String material_name) {
        this.material_id = material_id;
        this.material_name = material_name;
    }

    public int getMaterial_id() {
        return material_id;
    }

    public void setMaterial_id(int material_id) {
        this.material_id = material_id;
    }

    public String getMaterial_name() {
        return material_name;
    }

    public void setMaterial_name(String material_name) {
        this.material_name = material_name;
    }
}
