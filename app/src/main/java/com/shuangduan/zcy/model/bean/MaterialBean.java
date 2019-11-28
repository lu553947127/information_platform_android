package com.shuangduan.zcy.model.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.model.bean
 * @class describe
 * @time 2019/8/7 10:47
 * @change
 * @chang time
 * @class describe
 */
public class MaterialBean {

    /**
     * page : 1
     * count : 6
     * list : [{"amount":1,"name":"挂篮物资","oss_path":"23efd8a8e90434523c5e5b5a7f223f99.jpg","price":"7000.00","stock":269,"sell_stock":null,"tonne":100,"agent_id":3,"categoryId":11,"is_order":0,"image":"http://zicaiyun-pc.oss-cn-qingdao.aliyuncs.com/23efd8a8e90434523c5e5b5a7f223f99.jpg","amount":26900,"sell_amount":0,"agent_name":"湖北港桥模板有限公司"}]
     */

    private int page;
    private int count;
    private List<ListBean> list;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * amount : 1
         * name : 挂篮物资
         * oss_path : 23efd8a8e90434523c5e5b5a7f223f99.jpg
         * price : 7000.00
         * stock : 269
         * sell_stock : null
         * tonne : 100
         * agent_id : 3
         * categoryId : 11
         * is_order : 0
         * image : http://zicaiyun-pc.oss-cn-qingdao.aliyuncs.com/23efd8a8e90434523c5e5b5a7f223f99.jpg
         * amount : 26900
         * sell_amount : 0
         * agent_name : 湖北港桥模板有限公司
         */

        private int id;
        private String name;
        private String oss_path;
        private String price;
        //库存
        private int stock;
        private Object sell_stock;
        private int tonne;
        private int agent_id;
        //分类ID
        private int category;
        //是够购买过
        private int is_order;
        private String image;
        private int amount;
        private int sell_amount;
        private String agent_name;
        //缩略图
        private List<Images> images;
        //物质名称
        @SerializedName("material_name")
        private String materialName;
        //价格
        @SerializedName("guidance_price")
        private String guidancePrice;

        //规格
        private String spec;

        //地址
        private String address;
        //供应商
        @SerializedName("material_supplier")
        private String materialSupplie;
        //供应商ID
        @SerializedName("supplier_id")
        private int supplierId;

        //省份ID
        private long province;
        //市ID
        private long city;

        //销量
        @SerializedName("sales_volume")
        private int salesVolume;
        //单位
        private String unit;

        //供应方式
        private int method;

        //浏览人数
        @SerializedName("browse_count")
        private String browseCount;

        //设备名称
        @SerializedName("equipment_name")
        private String equipmentName;

        //供应商名称
        @SerializedName("equipment_supplier")
        private String equipmentSupplier;


        public String getEquipmentSupplier() {
            return equipmentSupplier;
        }

        public void setEquipmentSupplier(String equipmentSupplier) {
            this.equipmentSupplier = equipmentSupplier;
        }

        public String getEquipmentName() {
            return equipmentName;
        }

        public void setEquipmentName(String equipmentName) {
            this.equipmentName = equipmentName;
        }

        public String getBrowseCount() {
            return browseCount;
        }

        public void setBrowseCount(String browseCount) {
            this.browseCount = browseCount;
        }

        public int getMethod() {
            return method;
        }

        public void setMethod(int method) {
            this.method = method;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        @Override
        public String toString() {
            return "ListBean{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", oss_path='" + oss_path + '\'' +
                    ", price='" + price + '\'' +
                    ", stock=" + stock +
                    ", sell_stock=" + sell_stock +
                    ", tonne=" + tonne +
                    ", agent_id=" + agent_id +
                    ", category=" + category +
                    ", is_order=" + is_order +
                    ", image='" + image + '\'' +
                    ", amount=" + amount +
                    ", sell_amount=" + sell_amount +
                    ", agent_name='" + agent_name + '\'' +
                    ", images=" + images +
                    ", materialName='" + materialName + '\'' +
                    ", guidancePrice='" + guidancePrice + '\'' +
                    ", spec='" + spec + '\'' +
                    ", address='" + address + '\'' +
                    ", materialSupplie='" + materialSupplie + '\'' +
                    ", supplierId=" + supplierId +
                    ", province=" + province +
                    ", city=" + city +
                    ", salesVolume=" + salesVolume +
                    ", unit='" + unit + '\'' +
                    '}';
        }

        public int getSalesVolume() {
            return salesVolume;
        }

        public void setSalesVolume(int salesVolume) {
            this.salesVolume = salesVolume;
        }

        public List<Images> getImages() {
            return images;
        }

        public void setImages(List<Images> images) {
            this.images = images;
        }

        public String getMaterialName() {
            return materialName;
        }

        public void setMaterialName(String materialName) {
            this.materialName = materialName;
        }

        public String getGuidancePrice() {
            return guidancePrice;
        }

        public void setGuidancePrice(String guidancePrice) {
            this.guidancePrice = guidancePrice;
        }

        public String getSpec() {
            return spec;
        }

        public void setSpec(String spec) {
            this.spec = spec;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getMaterialSupplie() {
            return materialSupplie;
        }

        public void setMaterialSupplie(String materialSupplie) {
            this.materialSupplie = materialSupplie;
        }

        public int getSupplierId() {
            return supplierId;
        }

        public void setSupplierId(int supplierId) {
            this.supplierId = supplierId;
        }

        public long getProvince() {
            return province;
        }

        public void setProvince(long province) {
            this.province = province;
        }

        public long getCity() {
            return city;
        }

        public void setCity(long city) {
            this.city = city;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getOss_path() {
            return oss_path;
        }

        public void setOss_path(String oss_path) {
            this.oss_path = oss_path;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public int getStock() {
            return stock;
        }

        public void setStock(int stock) {
            this.stock = stock;
        }

        public Object getSell_stock() {
            return sell_stock;
        }

        public void setSell_stock(Object sell_stock) {
            this.sell_stock = sell_stock;
        }

        public int getTonne() {
            return tonne;
        }

        public void setTonne(int tonne) {
            this.tonne = tonne;
        }

        public int getAgent_id() {
            return agent_id;
        }

        public void setAgent_id(int agent_id) {
            this.agent_id = agent_id;
        }

        public int getCategory() {
            return category;
        }

        public void setCategory(int category_id) {
            this.category = category_id;
        }

        public int getIs_order() {
            return is_order;
        }

        public void setIs_order(int is_order) {
            this.is_order = is_order;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public int getSell_amount() {
            return sell_amount;
        }

        public void setSell_amount(int sell_amount) {
            this.sell_amount = sell_amount;
        }

        public String getAgent_name() {
            return agent_name;
        }

        public void setAgent_name(String agent_name) {
            this.agent_name = agent_name;
        }

    }

    public class Images {
        public String url;
//        @SerializedName("heade_url")
//        public String headeUrl;

        @Override
        public String toString() {
            return "Images{" +
                    "url='" + url + '\'' +
                    '}';
        }
    }
}
