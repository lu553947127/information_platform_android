package com.shuangduan.zcy.model.bean;

import java.util.List;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.model.bean
 * @class describe
 * @time 2019/8/20 14:03
 * @change
 * @chang time
 * @class describe
 */
public class DemandBuyerBean {

    /**
     * page : 1
     * count : 2
     * list : [{"id":3,"material_name":"找买家杆件222","count":"2","acceptance_price":"0.01","start_time":"2019-08-16","end_time":"2019-09-07"}]
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
         * id : 3
         * material_name : 找买家杆件222
         * count : 2
         * acceptance_price : 0.01
         * start_time : 2019-08-16
         * end_time : 2019-09-07
         */

        private int id;
        private String material_name;
        private String count;
        private String acceptance_price;
        private String start_time;
        private String end_time;
        private int status;
        private int way;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getMaterial_name() {
            return material_name;
        }

        public void setMaterial_name(String material_name) {
            this.material_name = material_name;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public String getAcceptance_price() {
            return acceptance_price;
        }

        public void setAcceptance_price(String acceptance_price) {
            this.acceptance_price = acceptance_price;
        }

        public String getStart_time() {
            return start_time;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getWay() {
            return way;
        }

        public void setWay(int way) {
            this.way = way;
        }
    }
}
