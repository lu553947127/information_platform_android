package com.shuangduan.zcy.model.bean;

import java.util.List;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.model.bean
 * @class describe
 * @time 2019/8/22 15:19
 * @change
 * @chang time
 * @class describe
 */
public class ProjectSubFirstBean {

    /**
     * id : 2890
     * title : 工程信息测试20190820
     * order_sn : sdapp201908221308628
     * select : [{"time":"3个月","months":3,"than":0.3,"price":"0.00","expect_price":"0.00"}]
     */

    private int id;
    private String title;
    private String order_sn;
    private List<SelectBean> select;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }

    public List<SelectBean> getSelect() {
        return select;
    }

    public void setSelect(List<SelectBean> select) {
        this.select = select;
    }

    public static class SelectBean {
        /**
         * time : 3个月
         * months : 3
         * than : 0.3
         * price : 0.00
         * expect_price : 0.00
         */

        private String time;
        private int months;
        private double than;
        private String price;
        private String expect_price;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public int getMonths() {
            return months;
        }

        public void setMonths(int months) {
            this.months = months;
        }

        public double getThan() {
            return than;
        }

        public void setThan(double than) {
            this.than = than;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getExpect_price() {
            return expect_price;
        }

        public void setExpect_price(String expect_price) {
            this.expect_price = expect_price;
        }
    }
}
