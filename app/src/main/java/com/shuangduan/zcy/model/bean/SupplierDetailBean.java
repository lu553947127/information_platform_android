package com.shuangduan.zcy.model.bean;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.model.bean
 * @class describe
 * @time 2019/8/12 10:19
 * @change
 * @chang time
 * @class describe
 */
public class SupplierDetailBean {

    /**
     * name : 市场人员
     * headimg : null
     * sex : 0
     * tel : 028****58353
     * company : 四川博忠金属制造有限公司
     * position : null
     * serve_address : 全国
     * experience : 0
     * product : 钢结构、钢模板
     * is_pay : 0
     */

    private String name;
    private String headimg;
    private int sex;
    private String tel;
    private String company;
    private String position;
    private String serve_address;
    private int experience;
    private String product;
    private int is_pay;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeadimg() {
        return headimg;
    }

    public void setHeadimg(String headimg) {
        this.headimg = headimg;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getServe_address() {
        return serve_address;
    }

    public void setServe_address(String serve_address) {
        this.serve_address = serve_address;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public int getIs_pay() {
        return is_pay;
    }

    public void setIs_pay(int is_pay) {
        this.is_pay = is_pay;
    }
}
