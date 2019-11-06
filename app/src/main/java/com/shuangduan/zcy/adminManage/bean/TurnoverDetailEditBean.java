package com.shuangduan.zcy.adminManage.bean;

import java.util.List;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.adminManage.bean
 * @ClassName: TurnoverDetailEditBean
 * @Description: java类作用描述
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/11/5 14:14
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/11/5 14:14
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class TurnoverDetailEditBean {
    /**
     * category : 1
     * category_name : 支架及附件
     * material_id : 11
     * material_id_name : 碗口支架立杆
     * unit_id : 0
     * unit_id_name : null
     * stock : 99
     * unit_price : 99
     * unit : 3
     * unit_name : 组
     * spec : 我我我
     * use_status : 2
     * use_status_name : 闲置
     * material_status : 2
     * material_status_name : 基本完好
     * province : 220000
     * province_name : 吉林省
     * city : 220700
     * city_name : 松原市
     * address : 前郭尔罗斯蒙古族自治县前郭尔罗斯镇松江大街49
     * longitude : 124.823607
     * latitude : 45.118244
     * person_liable : 我我我
     * tel : 9
     * is_shelf : 1
     * is_shelf_name : 公开上架
     * method : 1
     * shelf_start_time :
     * shelf_end_time :
     * shelf_type : 1
     * guidance_price : 9
     * plan : 0
     * plan_name :
     * use_count : 0
     * start_date :
     * entry_time :
     * exit_time :
     * accumulated_amortization : 0
     * original_price : 0
     * net_worth : 0
     * remark :
     * images : [{"url":"https://information-api.oss-cn-qingdao.aliyuncs.com/402d9585648fa485dfec0ede94cf05ad.png","heade_url":"https://information-api.oss-cn-qingdao.aliyuncs.com/402d9585648fa485dfec0ede94cf05ad.png?x-oss-process=image/resize,m_lfit,h_250,w_250"},{"url":"https://information-api.oss-cn-qingdao.aliyuncs.com/80143e2599e7e6fb0434202397ea855f.jpeg","heade_url":"https://information-api.oss-cn-qingdao.aliyuncs.com/80143e2599e7e6fb0434202397ea855f.jpeg?x-oss-process=image/resize,m_lfit,h_250,w_250"}]
     */

    private int category;
    private String category_name;
    private int material_id;
    private String material_id_name;
    private int unit_id;
    private String unit_id_name;
    private String stock;
    private String unit_price;
    private int unit;
    private String unit_name;
    private String spec;
    private int use_status;
    private String use_status_name;
    private int material_status;
    private String material_status_name;
    private int province;
    private String province_name;
    private int city;
    private String city_name;
    private String address;
    private double longitude;
    private double latitude;
    private String person_liable;
    private String tel;
    private int is_shelf;
    private String is_shelf_name;
    private int method;
    private String shelf_start_time;
    private String shelf_end_time;
    private int shelf_type;
    private String guidance_price;
    private int plan;
    private String plan_name;
    private String use_count;
    private String start_date;
    private String entry_time;
    private String exit_time;
    private String accumulated_amortization;
    private String original_price;
    private String net_worth;
    private String remark;
    private List<ImagesBean> images;

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public int getMaterial_id() {
        return material_id;
    }

    public void setMaterial_id(int material_id) {
        this.material_id = material_id;
    }

    public String getMaterial_id_name() {
        return material_id_name;
    }

    public void setMaterial_id_name(String material_id_name) {
        this.material_id_name = material_id_name;
    }

    public int getUnit_id() {
        return unit_id;
    }

    public void setUnit_id(int unit_id) {
        this.unit_id = unit_id;
    }

    public String getUnit_id_name() {
        return unit_id_name;
    }

    public void setUnit_id_name(String unit_id_name) {
        this.unit_id_name = unit_id_name;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(String unit_price) {
        this.unit_price = unit_price;
    }

    public int getUnit() {
        return unit;
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }

    public String getUnit_name() {
        return unit_name;
    }

    public void setUnit_name(String unit_name) {
        this.unit_name = unit_name;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public int getUse_status() {
        return use_status;
    }

    public void setUse_status(int use_status) {
        this.use_status = use_status;
    }

    public String getUse_status_name() {
        return use_status_name;
    }

    public void setUse_status_name(String use_status_name) {
        this.use_status_name = use_status_name;
    }

    public int getMaterial_status() {
        return material_status;
    }

    public void setMaterial_status(int material_status) {
        this.material_status = material_status;
    }

    public String getMaterial_status_name() {
        return material_status_name;
    }

    public void setMaterial_status_name(String material_status_name) {
        this.material_status_name = material_status_name;
    }

    public int getProvince() {
        return province;
    }

    public void setProvince(int province) {
        this.province = province;
    }

    public String getProvince_name() {
        return province_name;
    }

    public void setProvince_name(String province_name) {
        this.province_name = province_name;
    }

    public int getCity() {
        return city;
    }

    public void setCity(int city) {
        this.city = city;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getPerson_liable() {
        return person_liable;
    }

    public void setPerson_liable(String person_liable) {
        this.person_liable = person_liable;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public int getIs_shelf() {
        return is_shelf;
    }

    public void setIs_shelf(int is_shelf) {
        this.is_shelf = is_shelf;
    }

    public String getIs_shelf_name() {
        return is_shelf_name;
    }

    public void setIs_shelf_name(String is_shelf_name) {
        this.is_shelf_name = is_shelf_name;
    }

    public int getMethod() {
        return method;
    }

    public void setMethod(int method) {
        this.method = method;
    }

    public String getShelf_start_time() {
        return shelf_start_time;
    }

    public void setShelf_start_time(String shelf_start_time) {
        this.shelf_start_time = shelf_start_time;
    }

    public String getShelf_end_time() {
        return shelf_end_time;
    }

    public void setShelf_end_time(String shelf_end_time) {
        this.shelf_end_time = shelf_end_time;
    }

    public int getShelf_type() {
        return shelf_type;
    }

    public void setShelf_type(int shelf_type) {
        this.shelf_type = shelf_type;
    }

    public String getGuidance_price() {
        return guidance_price;
    }

    public void setGuidance_price(String guidance_price) {
        this.guidance_price = guidance_price;
    }

    public int getPlan() {
        return plan;
    }

    public void setPlan(int plan) {
        this.plan = plan;
    }

    public String getPlan_name() {
        return plan_name;
    }

    public void setPlan_name(String plan_name) {
        this.plan_name = plan_name;
    }

    public String getUse_count() {
        return use_count;
    }

    public void setUse_count(String use_count) {
        this.use_count = use_count;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEntry_time() {
        return entry_time;
    }

    public void setEntry_time(String entry_time) {
        this.entry_time = entry_time;
    }

    public String getExit_time() {
        return exit_time;
    }

    public void setExit_time(String exit_time) {
        this.exit_time = exit_time;
    }

    public String getAccumulated_amortization() {
        return accumulated_amortization;
    }

    public void setAccumulated_amortization(String accumulated_amortization) {
        this.accumulated_amortization = accumulated_amortization;
    }

    public String getOriginal_price() {
        return original_price;
    }

    public void setOriginal_price(String original_price) {
        this.original_price = original_price;
    }

    public String getNet_worth() {
        return net_worth;
    }

    public void setNet_worth(String net_worth) {
        this.net_worth = net_worth;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<ImagesBean> getImages() {
        return images;
    }

    public void setImages(List<ImagesBean> images) {
        this.images = images;
    }

    public static class ImagesBean {
        /**
         * url : https://information-api.oss-cn-qingdao.aliyuncs.com/402d9585648fa485dfec0ede94cf05ad.png
         * heade_url : https://information-api.oss-cn-qingdao.aliyuncs.com/402d9585648fa485dfec0ede94cf05ad.png?x-oss-process=image/resize,m_lfit,h_250,w_250
         */

        private String url;
        private String heade_url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getHeade_url() {
            return heade_url;
        }

        public void setHeade_url(String heade_url) {
            this.heade_url = heade_url;
        }
    }
}
