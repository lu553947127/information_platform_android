package com.shuangduan.zcy.model.bean;

import java.util.List;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.model.bean
 * @class describe
 * @time 2019/7/25 10:44
 * @change
 * @chang time
 * @class describe
 */
public class UserInfoBean {

    /**
     * id : 33
     * image : http://xx.yijijian.com/uploads/thumb/20190719/c7b0f2fb8a240ef4377f39374fbd050f.jpg
     * username : 哒哒哒
     * sex : -1
     * tel : 13554976061
     * identity_card :
     * email :
     * company : 济南双端6科技有限公司
     * position :
     * business_city : 西城区、崇文区
     * experience : 0
     * managing_products :
     * city_array : ["110102","110103"]
     */

    private int id;
    private String image;
    private String username;
    private int sex;
    private String tel;
    private String identity_card;
    private String email;
    private String company;
    private String position;
    private String business_city;
    private int experience;
    private String managing_products;
    private List<String> city_array;
    private String funds;
    private int count;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getIdentity_card() {
        return identity_card;
    }

    public void setIdentity_card(String identity_card) {
        this.identity_card = identity_card;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getBusiness_city() {
        return business_city;
    }

    public void setBusiness_city(String business_city) {
        this.business_city = business_city;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public String getManaging_products() {
        return managing_products;
    }

    public void setManaging_products(String managing_products) {
        this.managing_products = managing_products;
    }

    public List<String> getCity_array() {
        return city_array;
    }

    public void setCity_array(List<String> city_array) {
        this.city_array = city_array;
    }

    public String getFunds() {
        return funds;
    }

    public void setFunds(String funds) {
        this.funds = funds;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
