package com.shuangduan.zcy.model.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.model.bean
 * @class describe
 * @time 2019/8/7 14:58
 * @change
 * @chang time
 * @class describe
 */
public class MaterialDetailBean {
    /**
     * id : 12
     * images : [{"url":"http://zicaiyun-pc.oss-cn-qingdao.aliyuncs.com/40518db810c3d99b8efb5b0e678a3d38.jpg","heade_url":"http://zicaiyun-pc.oss-cn-qingdao.aliyuncs.com/40518db810c3d99b8efb5b0e678a3d38.jpg"},{"url":"http://zicaiyun-pc.oss-cn-qingdao.aliyuncs.com/365c63269c7881cc4552c31e30dfa93a.jpg","heade_url":"http://zicaiyun-pc.oss-cn-qingdao.aliyuncs.com/365c63269c7881cc4552c31e30dfa93a.jpg"},{"url":"http://zicaiyun-pc.oss-cn-qingdao.aliyuncs.com/d4e99d35f014d4db3f4b392482fdaf29.jpg","heade_url":"http://zicaiyun-pc.oss-cn-qingdao.aliyuncs.com/d4e99d35f014d4db3f4b392482fdaf29.jpg"},{"url":"http://zicaiyun-pc.oss-cn-qingdao.aliyuncs.com/448b9fed22349a77b6fc7e4f4ae185e7.jpg","heade_url":"http://zicaiyun-pc.oss-cn-qingdao.aliyuncs.com/448b9fed22349a77b6fc7e4f4ae185e7.jpg"},{"url":"http://zicaiyun-pc.oss-cn-qingdao.aliyuncs.com/9abb921c8cac90c95d0c9e62e3cb3c56.jpg","heade_url":"http://zicaiyun-pc.oss-cn-qingdao.aliyuncs.com/9abb921c8cac90c95d0c9e62e3cb3c56.jpg"}]
     * enclosure : [{"url":"http://zicaiyun-pc.oss-cn-qingdao.aliyuncs.com/0e09a504a37ec0785cd9679152d8fc03.jpg","heade_url":"http://zicaiyun-pc.oss-cn-qingdao.aliyuncs.com/0e09a504a37ec0785cd9679152d8fc03.jpg"},{"url":"http://zicaiyun-pc.oss-cn-qingdao.aliyuncs.com/250dadf6d491fd1640f38b0c96d5fb25.jpeg","heade_url":"http://zicaiyun-pc.oss-cn-qingdao.aliyuncs.com/250dadf6d491fd1640f38b0c96d5fb25.jpeg"},{"url":"http://zicaiyun-pc.oss-cn-qingdao.aliyuncs.com/e158c6454f0274398afc9d1c0ebf8077.jpg","heade_url":"http://zicaiyun-pc.oss-cn-qingdao.aliyuncs.com/e158c6454f0274398afc9d1c0ebf8077.jpg"}]
     * category : 37
     * supplier_id : 81
     * material_id : 38
     * unit_price : 1
     * guidance_price : 1
     * stock : 0
     * sales_volume : 0
     * spec : HH
     * unit : 8
     * address_list : [{"id":6,"address":"山东省济南市新泺大街"},{"id":11,"address":"山东省济南市市政府"},{"id":12,"address":"山东省济南市三庆齐盛广场"}]
     * material_category : 贝雷片
     * company : Help Heroes Ltd
     * address : 俺啥也不知道，俺啥也不想说
     * company_website : https://www.helphero.com
     * serve_address : 济南市 济宁市 青岛市 威海市 静安区 浦东新区
     * tel : 15588385761
     */

    private int id;
    private int category;
    private int supplier_id;
    private int material_id;
    private int unit_price;
    private int guidance_price;
    private int stock;
    private String sales_volume;
    private String spec;
    private String unit;
    private String material_category;
    private String company;
    private String address;
    private String company_website;
    private String serve_address;
    private String tel;
    private String product;
    private String supplie_address;
    private String is_collection;
    private String enclosure;
    private String status;
    private List<ImagesBean> images;
    private List<AddressListBean> address_list;

    //浏览总人数
    @SerializedName("browse_count")
    private int browseCount;

    //浏览人信息
    public User user;

    //物资类型
    @SerializedName("is_shelf")
    private int isShelf;
    //供应方式
    private int method;

    public int getBrowseCount() {
        return browseCount;
    }

    public void setBrowseCount(int browseCount) {
        this.browseCount = browseCount;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getIsShelf() {
        return isShelf;
    }

    public void setIsShelf(int isShelf) {
        this.isShelf = isShelf;
    }

    public int getMethod() {
        return method;
    }

    public void setMethod(int method) {
        this.method = method;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEnclosure() {
        return enclosure;
    }

    public void setEnclosure(String enclosure) {
        this.enclosure = enclosure;
    }

    public String getIs_collection() {
        return is_collection;
    }

    public void setIs_collection(String is_collection) {
        this.is_collection = is_collection;
    }

    public String getSupplie_address() {
        return supplie_address;
    }

    public void setSupplie_address(String supplie_address) {
        this.supplie_address = supplie_address;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getSupplier_id() {
        return supplier_id;
    }

    public void setSupplier_id(int supplier_id) {
        this.supplier_id = supplier_id;
    }

    public int getMaterial_id() {
        return material_id;
    }

    public void setMaterial_id(int material_id) {
        this.material_id = material_id;
    }

    public int getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(int unit_price) {
        this.unit_price = unit_price;
    }

    public int getGuidance_price() {
        return guidance_price;
    }

    public void setGuidance_price(int guidance_price) {
        this.guidance_price = guidance_price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getSales_volume() {
        return sales_volume;
    }

    public void setSales_volume(String sales_volume) {
        this.sales_volume = sales_volume;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getMaterial_category() {
        return material_category;
    }

    public void setMaterial_category(String material_category) {
        this.material_category = material_category;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCompany_website() {
        return company_website;
    }

    public void setCompany_website(String company_website) {
        this.company_website = company_website;
    }

    public String getServe_address() {
        return serve_address;
    }

    public void setServe_address(String serve_address) {
        this.serve_address = serve_address;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public List<ImagesBean> getImages() {
        return images;
    }

    public void setImages(List<ImagesBean> images) {
        this.images = images;
    }

    public List<AddressListBean> getAddress_list() {
        return address_list;
    }

    public void setAddress_list(List<AddressListBean> address_list) {
        this.address_list = address_list;
    }

    public static class ImagesBean {
        /**
         * url : http://zicaiyun-pc.oss-cn-qingdao.aliyuncs.com/40518db810c3d99b8efb5b0e678a3d38.jpg
         * heade_url : http://zicaiyun-pc.oss-cn-qingdao.aliyuncs.com/40518db810c3d99b8efb5b0e678a3d38.jpg
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

    public static class AddressListBean {
        /**
         * id : 6
         * address : 山东省济南市新泺大街
         */

        private int id;
        private String address;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }

    public class User{
        private int id;
        private String image;

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
    }
}
