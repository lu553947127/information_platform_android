package com.shuangduan.zcy.adminManage.bean;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.adminManage.bean
 * @ClassName: TurnoverCompanyBean
 * @Description: java类作用描述
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/10/29 16:15
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/10/29 16:15
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class TurnoverCompanyBean {
    /**
     * supplier_id : 113
     * company : 双端数字
     */

    private int supplier_id;
    private String company;

    public TurnoverCompanyBean(int supplier_id, String company) {
        this.supplier_id = supplier_id;
        this.company = company;
    }

    public int getSupplier_id() {
        return supplier_id;
    }

    public void setSupplier_id(int supplier_id) {
        this.supplier_id = supplier_id;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
