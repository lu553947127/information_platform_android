package com.shuangduan.zcy.model.bean;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.model.bean
 * @ClassName: MaterialPlaceOrderBean
 * @Description: java类作用描述
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/9/25 17:49
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/9/25 17:49
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class MaterialPlaceOrderBean {

    private int num;
    private int id;
    private String material_name;

    public MaterialPlaceOrderBean(int num, int id, String material_name) {
        this.num = num;
        this.id = id;
        this.material_name = material_name;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMaterial_name() {
        return material_name;
    }

    public void setMaterial_name(String material_name) {
        this.material_name = material_name;
    }
}
