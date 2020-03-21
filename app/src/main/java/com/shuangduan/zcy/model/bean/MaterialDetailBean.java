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
    private int targetId;

    private String material_name;

    private String unit_price;
    private String guidance_price;
    private String stock;
    private String sales_volume;
    private String spec;
    private String unit;
    private String company;
    private String headimg;
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


    //浏览总人数
    @SerializedName("browse_count")
    private int browseCount;

    //浏览人信息
    public List<User> user;

    //物资类型 1 公开物资 2 物资未上架  3内购物资
    @SerializedName("is_shelf")
    private int isShelf;
    //供应方式 1 出租 2 售卖
    private int method;

    //是否面议 1：显示价格 2：面议
    @SerializedName("price_type")
    private int priceType;


    public int getPriceType() {
        return priceType;
    }

    public MaterialDetailBean() {
    }

    public int getBrowseCount() {
        return browseCount;
    }

    public List<User> getUser() {
        return user;
    }


    public int getIsShelf() {
        return isShelf;
    }


    public int getMethod() {
        return method;
    }



    public String getStatus() {
        return status;
    }


    public String getEnclosure() {
        return enclosure;
    }



    public String getIs_collection() {
        return is_collection;
    }


    public String getSupplie_address() {
        return supplie_address;
    }


    public String getProduct() {
        return product;
    }


    public int getId() {
        return id;
    }


    public int getCategory() {
        return category;
    }


    public int getSupplier_id() {
        return supplier_id;
    }

    public int getTargetId() {
        return targetId;
    }

    public void setTargetId(int targetId) {
        this.targetId = targetId;
    }

    public String getUnit_price() {
        return unit_price;
    }


    public String getGuidance_price() {
        return guidance_price;
    }


    public String getStock() {
        return stock;
    }


    public String getSales_volume() {
        return sales_volume;
    }


    public String getSpec() {
        return spec;
    }


    public String getUnit() {
        return unit;
    }


    public String getMaterialName() {
        return material_name;
    }


    public String getCompany() {
        return company;
    }

    public String getHeadimg() {
        return headimg;
    }

    public void setHeadimg(String headimg) {
        this.headimg = headimg;
    }

    public String getAddress() {
        return address;
    }


    public String getCompany_website() {
        return company_website;
    }


    public String getServe_address() {
        return serve_address;
    }


    public String getTel() {
        return tel;
    }

    public List<ImagesBean> getImages() {
        return images;
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

    public static class User {
        private int id;
        private String image;

        public User(int id) {
            this.id = id;
        }

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
