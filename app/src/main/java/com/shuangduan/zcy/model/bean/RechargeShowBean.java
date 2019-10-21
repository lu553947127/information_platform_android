package com.shuangduan.zcy.model.bean;

import java.util.List;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.model.bean
 * @class describe
 * @time 2019/8/13 14:43
 * @change
 * @chang time
 * @class describe
 */
public class RechargeShowBean {

    /**
     * tel : 13212901897
     * list : [{"amount":1,"coin":"10.00","price":"10.00"},{"amount":2,"coin":"30.00","price":"30.00"},{"amount":3,"coin":"50.00","price":"50.00"},{"amount":4,"coin":"100.00","price":"100.00"}]
     */

    private String tel;
    private List<ListBean> list;

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean extends BaseSelectorBean {
        /**
         * amount : 1
         * coin : 10.00
         * price : 10.00
         */

        private int id;
        private String coin;
        private String price;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCoin() {
            return coin;
        }

        public void setCoin(String coin) {
            this.coin = coin;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }
    }
}
