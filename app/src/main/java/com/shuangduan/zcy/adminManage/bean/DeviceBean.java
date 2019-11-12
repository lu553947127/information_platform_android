package com.shuangduan.zcy.adminManage.bean;

import java.util.List;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.adminManage.bean
 * @ClassName: DeviceBean
 * @Description: java类作用描述
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/11/6 9:49
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/11/6 9:49
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class DeviceBean {
    /**
     * page : 1
     * count : 8
     * list : [{"id":478,"supplier_id":113,"category":"桥梁设备","material_id":"数控压浆系统","stock":435235,"is_shelf":"未上架","province":"天津市","city":"河东区","address":"天津","use_status":"闲置","company":"双端数字"},{"id":444,"supplier_id":113,"category":"路面设备","material_id":"稳定土厂拌设备","stock":35,"is_shelf":"公开上架","province":"北京市","city":"西城区","address":"西城区","use_status":"闲置","company":"双端数字"},{"id":443,"supplier_id":113,"category":"桥梁设备","material_id":"数控压浆系统","stock":434,"is_shelf":"未上架","province":"内蒙古自治区","city":"包头市","address":"包头市","use_status":"闲置","company":"双端数字"},{"id":442,"supplier_id":113,"category":"土石方设备","material_id":"装载机","stock":45,"is_shelf":"内部上架","province":"山西省","city":"太原市","address":"太原市","use_status":"闲置","company":"双端数字"},{"id":441,"supplier_id":113,"category":"通用设备","material_id":"轻型普通货车","stock":2342,"is_shelf":"公开上架","province":"河北省","city":"石家庄市","address":"石家庄市","use_status":"待报废","company":"双端数字"},{"id":440,"supplier_id":113,"category":"试验测量设备","material_id":"模块化数字汽车衡","stock":3254,"is_shelf":"公开上架","province":"北京市","city":"东城区","address":"东城区","use_status":"闲置","company":"双端数字"},{"id":439,"supplier_id":113,"category":"养护设备","material_id":" 除雪车","stock":121,"is_shelf":"公开上架","province":"天津市","city":"和平区","address":"和平区","use_status":"闲置","company":"双端数字"},{"id":438,"supplier_id":113,"category":"其他设备","material_id":"非接触式平衡梁","stock":1234,"is_shelf":"内部上架","province":"北京市","city":"东城区","address":"北京市","use_status":"再用","company":"双端数字"}]
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
         * id : 478
         * supplier_id : 113
         * category : 桥梁设备
         * material_id : 数控压浆系统
         * stock : 435235
         * is_shelf : 未上架
         * province : 天津市
         * city : 河东区
         * address : 天津
         * use_status : 闲置
         * company : 双端数字
         */

        private int id;
        private int supplier_id;
        private String category;
        private String material_id;
        private int stock;
        private String is_shelf;
        private String province;
        private String city;
        private String address;
        private String use_status;
        private String company;
        private String project;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getSupplier_id() {
            return supplier_id;
        }

        public void setSupplier_id(int supplier_id) {
            this.supplier_id = supplier_id;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getMaterial_id() {
            return material_id;
        }

        public void setMaterial_id(String material_id) {
            this.material_id = material_id;
        }

        public int getStock() {
            return stock;
        }

        public void setStock(int stock) {
            this.stock = stock;
        }

        public String getIs_shelf() {
            return is_shelf;
        }

        public void setIs_shelf(String is_shelf) {
            this.is_shelf = is_shelf;
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

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getUse_status() {
            return use_status;
        }

        public void setUse_status(String use_status) {
            this.use_status = use_status;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getProject() {
            return project;
        }

        public void setProject(String project) {
            this.project = project;
        }
    }
}
