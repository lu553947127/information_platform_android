package com.shuangduan.zcy.model.bean;

import java.util.List;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.model.bean
 * @class describe
 * @time 2019/8/12 9:45
 * @change
 * @chang time
 * @class describe
 */
public class SupplierBean {

    /**
     * page : 1
     * count : 23
     * list : [{"amount":1,"name":"市场人员","headimg":null,"tel":"028****58353","sex":0,"company":"四川博忠金属制造有限公司","serve_address":"全国","product":"钢结构、钢模板","sort":0,"images_json":[],"is_pay":0}]
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
         * name : 市场人员
         * headimg : null
         * tel : 028****58353
         * sex : 0
         * company : 四川博忠金属制造有限公司
         * serve_address : 全国
         * product : 钢结构、钢模板
         * sort : 0
         * images_json : []
         * is_pay : 0
         */

        private int id;
        private String name;
        private String headimg;
        private String tel;
        private int sex;
        private String company;
        private String serve_address;
        private String product;
        private String detail_price;
        private int sort;
        private int is_pay;
        private List<ImageBean> images_json;

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

        public String getHeadimg() {
            return headimg;
        }

        public void setHeadimg(String headimg) {
            this.headimg = headimg;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getServe_address() {
            return serve_address;
        }

        public void setServe_address(String serve_address) {
            this.serve_address = serve_address;
        }

        public String getProduct() {
            return product;
        }

        public void setProduct(String product) {
            this.product = product;
        }

        public String getDetail_price() {
            return detail_price;
        }

        public void setDetail_price(String detail_price) {
            this.detail_price = detail_price;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }

        public int getIs_pay() {
            return is_pay;
        }

        public void setIs_pay(int is_pay) {
            this.is_pay = is_pay;
        }

        public List<ImageBean> getImages_json() {
            return images_json;
        }

        public void setImages_json(List<ImageBean> images_json) {
            this.images_json = images_json;
        }

        public static class ImageBean{
            private String source;
            private String thumbnail;

            public String getSource() {
                return source;
            }

            public void setSource(String source) {
                this.source = source;
            }

            public String getThumbnail() {
                return thumbnail;
            }

            public void setThumbnail(String thumbnail) {
                this.thumbnail = thumbnail;
            }
        }
    }
}
