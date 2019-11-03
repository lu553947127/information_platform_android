package com.shuangduan.zcy.adminManage.bean;

import java.util.List;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.adminManage.bean
 * @ClassName: TurnoverBean
 * @Description: java类作用描述
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/10/25 15:11
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/10/25 15:11
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class TurnoverBean {
    /**
     * page : 1
     * count : 3
     * list : [{"id":679,"category":"支架及附件","material_id":"门式支架","is_shelf":"公开上架","province":"北京市","city":110101,"address":"112111","use_status":"闲置","company":"双端数字科技有限公司"},{"id":677,"category":"型钢","material_id":"H型钢","is_shelf":"内部上架","province":"黑龙江","city":230100,"address":"哈尔滨","use_status":"闲置","company":"双端数字科技有限公司"},{"id":676,"category":"支架及附件","material_id":"碗扣","is_shelf":"公开上架","province":"山东省","city":370100,"address":"历下区","use_status":"闲置","company":"双端数字科技有限公司"}]
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
         * id : 679
         * category : 支架及附件
         * material_id : 门式支架
         * is_shelf : 公开上架
         * province : 北京市
         * city : 110101
         * address : 112111
         * use_status : 闲置
         * company : 双端数字科技有限公司
         */

        private int id;
        private String category;
        private String material_id;
        private String is_shelf;
        private String province;
        private String city;
        private String address;
        private String use_status;
        private String company;
        private String stock;

        public String getStock() {
            return stock;
        }

        public void setStock(String stock) {
            this.stock = stock;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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
    }
}
