package com.shuangduan.zcy.model.event;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.model.event
 * @ClassName: MaterialDetailEvent
 * @Description: java类作用描述
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/9/25 11:39
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/9/25 11:39
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class MaterialDetailEvent {

    public String material_id;

    public MaterialDetailEvent(String material_id) {
        this.material_id = material_id;
    }
}
