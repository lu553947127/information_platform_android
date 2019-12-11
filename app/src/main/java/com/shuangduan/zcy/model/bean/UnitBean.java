package com.shuangduan.zcy.model.bean;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.model.bean
 * @ClassName: UnitBean
 * @Description: java类作用描述
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/12/11 9:41
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/12/11 9:41
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class UnitBean {

    private int unit_id;
    private String unit_name;

    public UnitBean(int unit_id, String unit_name) {
        this.unit_id = unit_id;
        this.unit_name = unit_name;
    }

    public int getUnit_id() {
        return unit_id;
    }

    public void setUnit_id(int unit_id) {
        this.unit_id = unit_id;
    }

    public String getUnit_name() {
        return unit_name;
    }

    public void setUnit_name(String unit_name) {
        this.unit_name = unit_name;
    }
}
