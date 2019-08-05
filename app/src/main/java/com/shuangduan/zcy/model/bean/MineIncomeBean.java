package com.shuangduan.zcy.model.bean;

import java.util.List;

/**
 * @author 宁文强 QQ:858777523
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
        private String funds;

        public String getAll_funds() {
            return all_funds;
        }

        public void setAll_funds(String all_funds) {
            this.all_funds = all_funds;
        }

        public String getFunds() {
            return funds;
        }

        public void setFunds(String funds) {
            this.funds = funds;
        }
    }

    public static class ListBean {
        /**
         * listtime : 01
         * price : 0
         */

        private String listtime;
        private int price;

        public String getListtime() {
            return listtime;
        }

        public void setListtime(String listtime) {
            this.listtime = listtime;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }
    }
}
