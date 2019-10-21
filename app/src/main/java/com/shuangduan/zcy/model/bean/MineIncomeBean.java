package com.shuangduan.zcy.model.bean;

import java.util.List;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.model.bean
 * @class describe
 * @time 2019/8/5 17:57
 * @change
 * @chang time
 * @class describe
 */
public class MineIncomeBean {

    /**
     * proceeds : {"all_funds":"0.00","funds":"0.00"}
     * list : [{"listtime":"01","price":0}]
     */

    private ProceedsBean proceeds;
    private List<ListBean> list;

    public ProceedsBean getProceeds() {
        return proceeds;
    }

    public void setProceeds(ProceedsBean proceeds) {
        this.proceeds = proceeds;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ProceedsBean {
        /**
         * all_funds : 0.00
         * funds : 0.00
         */

        private String all_funds;
        private String coin;

        public String getAll_funds() {
            return all_funds;
        }

        public void setAll_funds(String all_funds) {
            this.all_funds = all_funds;
        }

        public String getCoin() {
            return coin;
        }

        public void setCoin(String coin) {
            this.coin = coin;
        }
    }

    public static class ListBean {
        /**
         * listtime : 01
         * price : 0
         */

        private String listtime;
        private float price;

        public String getListtime() {
            return listtime;
        }

        public void setListtime(String listtime) {
            this.listtime = listtime;
        }

        public float getPrice() {
            return price;
        }

        public void setPrice(float price) {
            this.price = price;
        }
    }
}
