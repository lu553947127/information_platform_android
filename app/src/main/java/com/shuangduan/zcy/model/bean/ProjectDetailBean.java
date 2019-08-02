package com.shuangduan.zcy.model.bean;

import java.util.List;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.model.api
 * @class describe
 * @time 2019/7/23 11:48
 * @change
 * @chang time
 * @class describe
 */
public class ProjectDetailBean {

    /**
     * detail : {"title":"F息烽至黔西高速公路TJ-2标","province":"贵州省","city":"遵义市","longitude":"0.000000","latitude":"0.000000","update_time":"2019-07-23 09:43:21","phases":"项目完成","type":"","cycle":"2019-07-22至2019-07-22","acreage":"","valuation":"","warrant_status":0,"warrant_price":"0.00","detail_price":"0.00","intro":"项目起点位于小寨坝顺...","materials":"","is_pay":0,"collection":0}
     * contact : []
     */

    private DetailBean detail;
    private List<ContactBean> contact;

    public DetailBean getDetail() {
        return detail;
    }

    public void setDetail(DetailBean detail) {
        this.detail = detail;
    }

    public List<ContactBean> getContact() {
        return contact;
    }

    public void setContact(List<ContactBean> contact) {
        this.contact = contact;
    }

    public static class DetailBean {
        /**
         * title : F息烽至黔西高速公路TJ-2标
         * province : 贵州省
         * city : 遵义市
         * longitude : 0.000000
         * latitude : 0.000000
         * update_time : 2019-07-23 09:43:21
         * phases : 项目完成
         * type :
         * cycle : 2019-07-22至2019-07-22
         * acreage :
         * valuation :
         * warrant_status : 0
         * warrant_price : 0.00
         * detail_price : 0.00
         * intro : 项目起点位于小寨坝顺...
         * materials :
         * is_pay : 0
         * collection : 0
         */

        private String title;
        private String province;
        private String city;
        private double longitude;
        private double latitude;
        private String update_time;
        private String phases;
        private String type;
        private String cycle;
        private String acreage;
        private String valuation;
        private int warrant_status;
        private String warrant_price;
        private String detail_price;
        private String intro;
        private String materials;
        private int is_pay;
        private int collection;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public String getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }

        public String getPhases() {
            return phases;
        }

        public void setPhases(String phases) {
            this.phases = phases;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getCycle() {
            return cycle;
        }

        public void setCycle(String cycle) {
            this.cycle = cycle;
        }

        public String getAcreage() {
            return acreage;
        }

        public void setAcreage(String acreage) {
            this.acreage = acreage;
        }

        public String getValuation() {
            return valuation;
        }

        public void setValuation(String valuation) {
            this.valuation = valuation;
        }

        public int getWarrant_status() {
            return warrant_status;
        }

        public void setWarrant_status(int warrant_status) {
            this.warrant_status = warrant_status;
        }

        public String getWarrant_price() {
            return warrant_price;
        }

        public void setWarrant_price(String warrant_price) {
            this.warrant_price = warrant_price;
        }

        public String getDetail_price() {
            return detail_price;
        }

        public void setDetail_price(String detail_price) {
            this.detail_price = detail_price;
        }

        public String getIntro() {
            return intro;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }

        public String getMaterials() {
            return materials;
        }

        public void setMaterials(String materials) {
            this.materials = materials;
        }

        public int getIs_pay() {
            return is_pay;
        }

        public void setIs_pay(int is_pay) {
            this.is_pay = is_pay;
        }

        public int getCollection() {
            return collection;
        }

        public void setCollection(int collection) {
            this.collection = collection;
        }
    }

    public static class ContactBean{

        /**
         * name : 刘王
         * tel : 136****6666
         * company : 济南双端科技...
         * phone_type : 主体负责人
         * province : 北京市
         * city :
         */

        private String name;
        private String tel;
        private String company;
        private String phone_type;
        private String province;
        private String city;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
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

        public String getPhone_type() {
            return phone_type;
        }

        public void setPhone_type(String phone_type) {
            this.phone_type = phone_type;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }
    }
}
