package com.shuangduan.zcy.adminManage.bean;

import java.util.List;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.adminManage.bean
 * @ClassName: DeviceDetailBean
 * @Description: java类作用描述
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/11/6 10:23
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/11/6 10:23
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class DeviceDetailBean {
    /**
     * category_name : 通用设备
     * material_id_name : 皮卡车
     * unit_id_name : null
     * stock : 65465
     * unit_name : 间
     * spec : 654645
     * use_status_name : 再用
     * material_status_name :
     * province_name : 黑龙江
     * city_name : 鸡西市
     * address : 64564
     * person_liable : 546464564
     * tel : 15666666666
     * is_shelf_name : 未上架
     * method : 0
     * shelf_start_time :
     * shelf_end_time :
     * shelf_type : 1
     * guidance_price : 0
     * plan_name :
     * brand :
     * main_params :
     * power : 0
     * original_price : 0
     * start_date :
     * entry_time :
     * exit_time :
     * user_id : 48
     * technology_month_count : 0
     * technology_detail :
     * equipment_time :
     * operator_name :
     * images : [{"url":"https://zicaiyun-pc.oss-cn-qingdao.aliyuncs.com/8cb57d79f5d3ad840f6e3d7507cad364.jpg","heade_url":"https://zicaiyun-pc.oss-cn-qingdao.aliyuncs.com/8cb57d79f5d3ad840f6e3d7507cad364.jpg?x-oss-process=image/resize,m_lfit,h_250,w_250"},{"url":"https://zicaiyun-pc.oss-cn-qingdao.aliyuncs.com/dab5d1a9ea35efe6a67de541ae5807b6.jpg","heade_url":"https://zicaiyun-pc.oss-cn-qingdao.aliyuncs.com/dab5d1a9ea35efe6a67de541ae5807b6.jpg?x-oss-process=image/resize,m_lfit,h_250,w_250"},{"url":"https://zicaiyun-pc.oss-cn-qingdao.aliyuncs.com/a35a43c9b3d09b84c21d0c02c09cde35.jpg","heade_url":"https://zicaiyun-pc.oss-cn-qingdao.aliyuncs.com/a35a43c9b3d09b84c21d0c02c09cde35.jpg?x-oss-process=image/resize,m_lfit,h_250,w_250"},{"url":"https://zicaiyun-pc.oss-cn-qingdao.aliyuncs.com/038c81ce0784469c5c72ba9787b03f1b.jpg","heade_url":"https://zicaiyun-pc.oss-cn-qingdao.aliyuncs.com/038c81ce0784469c5c72ba9787b03f1b.jpg?x-oss-process=image/resize,m_lfit,h_250,w_250"},{"url":"https://zicaiyun-pc.oss-cn-qingdao.aliyuncs.com/4bbf9b196a64dafb5d943d1673a21f11.jpg","heade_url":"https://zicaiyun-pc.oss-cn-qingdao.aliyuncs.com/4bbf9b196a64dafb5d943d1673a21f11.jpg?x-oss-process=image/resize,m_lfit,h_250,w_250"}]
     * username : 湮、渲染离别
     */

    private String category_name;
    private String material_id_name;
    private String unit_id_name;
    private String stock;
    private String unit_name;
    private String spec;
    private String use_status_name;
    private String material_status_name;
    private String province_name;
    private String city_name;
    private String address;
    private String person_liable;
    private String tel;
    private String is_shelf_name;
    private int method;
    private String shelf_start_time;
    private String shelf_end_time;
    private int shelf_type;
    private String guidance_price;
    private String plan_name;
    private String brand;
    private String main_params;
    private String power;
    private String original_price;
    private String start_date;
    private String entry_time;
    private String exit_time;
    private int user_id;
    private String technology_month_count;
    private String technology_detail;
    private String equipment_time;
    private String operator_name;
    private String username;
    private String encoding;
    private String use_month_count;
    private List<ImagesBean> images;


    public String getUse_month_count() {
        return use_month_count;
    }

    public void setUse_month_count(String use_month_count) {
        this.use_month_count = use_month_count;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getMaterial_id_name() {
        return material_id_name;
    }

    public void setMaterial_id_name(String material_id_name) {
        this.material_id_name = material_id_name;
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

    public String getUse_status_name() {
        return use_status_name;
    }

    public void setUse_status_name(String use_status_name) {
        this.use_status_name = use_status_name;
    }

    public String getMaterial_status_name() {
        return material_status_name;
    }

    public void setMaterial_status_name(String material_status_name) {
        this.material_status_name = material_status_name;
    }

    public String getProvince_name() {
        return province_name;
    }

    public void setProvince_name(String province_name) {
        this.province_name = province_name;
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

    public String getPlan_name() {
        return plan_name;
    }

    public void setPlan_name(String plan_name) {
        this.plan_name = plan_name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getMain_params() {
        return main_params;
    }

    public void setMain_params(String main_params) {
        this.main_params = main_params;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public String getOriginal_price() {
        return original_price;
    }

    public void setOriginal_price(String original_price) {
        this.original_price = original_price;
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

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getTechnology_month_count() {
        return technology_month_count;
    }

    public void setTechnology_month_count(String technology_month_count) {
        this.technology_month_count = technology_month_count;
    }

    public String getTechnology_detail() {
        return technology_detail;
    }

    public void setTechnology_detail(String technology_detail) {
        this.technology_detail = technology_detail;
    }

    public String getEquipment_time() {
        return equipment_time;
    }

    public void setEquipment_time(String equipment_time) {
        this.equipment_time = equipment_time;
    }

    public String getOperator_name() {
        return operator_name;
    }

    public void setOperator_name(String operator_name) {
        this.operator_name = operator_name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<ImagesBean> getImages() {
        return images;
    }

    public void setImages(List<ImagesBean> images) {
        this.images = images;
    }

    public static class ImagesBean {
        /**
         * url : https://zicaiyun-pc.oss-cn-qingdao.aliyuncs.com/8cb57d79f5d3ad840f6e3d7507cad364.jpg
         * heade_url : https://zicaiyun-pc.oss-cn-qingdao.aliyuncs.com/8cb57d79f5d3ad840f6e3d7507cad364.jpg?x-oss-process=image/resize,m_lfit,h_250,w_250
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
