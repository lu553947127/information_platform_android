package com.shuangduan.zcy.model.bean;

import java.util.List;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.model.bean
 * @class describe
 * @time 2019/8/13 16:38
 * @change
 * @chang time
 * @class describe
 */
public class RechargeRecordBean {

    /**
     * page : 1
     * count : 2
     * list : [{"order_sn":"sdapp201908077087062","user_id":19,"price":"100.00","pay_method":2,"create_time":"2019-08-07 11:23:47"},{"order_sn":"sdapp201908077338669","user_id":19,"price":"100.00","pay_method":2,"create_time":"2019-08-07 10:52:21"}]
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
         * order_sn : sdapp201908077087062
         * user_id : 19
         * price : 100.00
         * pay_method : 2
         * create_time : 2019-08-07 11:23:47
         */

        private int id;
        private String order_sn;
        private int user_id;
        private String price;
        private int pay_method;
        private String create_time;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getOrder_sn() {
            return order_sn;
        }

        public void setOrder_sn(String order_sn) {
            this.order_sn = order_sn;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public int getPay_method() {
            return pay_method;
        }

        public void setPay_method(int pay_method) {
            this.pay_method = pay_method;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }
    }
}
