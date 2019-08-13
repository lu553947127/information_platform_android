package com.shuangduan.zcy.model.bean;

import java.util.List;

/**
 * @author 宁文强 QQ:858777523
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
        private int stock;
        private Object sell_stock;
        private int tonne;
        private int agent_id;
        private int category_id;
        private int is_order;
        private String image;
        private int amount;
        private int sell_amount;
        private String agent_name;

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

        public int getCategory_id() {
            return category_id;
        }

        public void setCategory_id(int category_id) {
            this.category_id = category_id;
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
}
