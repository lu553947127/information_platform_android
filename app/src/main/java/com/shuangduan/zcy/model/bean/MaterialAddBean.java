package com.shuangduan.zcy.model.bean;

import java.util.Set;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.model.bean
 * @ClassName: MaterialAddBean
 * @Description: java类作用描述
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/9/25 16:48
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/9/25 16:48
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class MaterialAddBean {

    String id;
    String order_id;

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
