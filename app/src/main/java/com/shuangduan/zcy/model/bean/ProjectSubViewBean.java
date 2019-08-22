package com.shuangduan.zcy.model.bean;

import java.util.List;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.model.bean
 * @class describe
 * @time 2019/8/22 15:21
 * @change
 * @chang time
 * @class describe
 */
public class ProjectSubViewBean {

    /**
     * info : {"username":"哒哒哒哈","create_time":"2019-08-22 16:41:31","start_time":"2019-08-22","end_time":"2019-11-22","expect_price":"37.50","income_price":"0.00","count":0}
     * list : [{"time":"2019-08-22","value":0}]
     */

    private InfoBean info;
    private List<ListBean> list;

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class InfoBean {
        /**
         * username : 哒哒哒哈
         * create_time : 2019-08-22 16:41:31
         * start_time : 2019-08-22
         * end_time : 2019-11-22
         * expect_price : 37.50
         * income_price : 0.00
         * count : 0
         */

        private String username;
        private String create_time;
        private String start_time;
        private String end_time;
        private String expect_price;
        private String income_price;
        private int count;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
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

        public String getExpect_price() {
            return expect_price;
        }

        public void setExpect_price(String expect_price) {
            this.expect_price = expect_price;
        }

        public String getIncome_price() {
            return income_price;
        }

        public void setIncome_price(String income_price) {
            this.income_price = income_price;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }

    public static class ListBean {
        /**
         * time : 2019-08-22
         * value : 0
         */

        private String time;
        private int value;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }
}
