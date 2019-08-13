package com.shuangduan.zcy.model.bean;

import java.util.List;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.model.bean
 * @class describe
 * @time 2019/8/7 9:08
 * @change
 * @chang time
 * @class describe
 */
public class WithdrawRecordBean {

    /**
     * page : 1
     * count : 0
     * list : [{"amount":1,"user_id":17,"order_sn":"201907221749411111111119","bankcard_id":1,"bankcard_type":"建设银行(4121)","bankcard_num":"621700151224121","real_name":"张三","price":"0.00","status":"1","remark":null,"create_time":"2019-07-22 17:49:41","update_time":"2019-07-22 17:49:41"}]
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
         * user_id : 17
         * order_sn : 201907221749411111111119
         * bankcard_id : 1
         * bankcard_type : 建设银行(4121)
         * bankcard_num : 621700151224121
         * real_name : 张三
         * price : 0.00
         * status : 1
         * remark : null
         * create_time : 2019-07-22 17:49:41
         * update_time : 2019-07-22 17:49:41
         */

        private int id;
        private int user_id;
        private String order_sn;
        private int bankcard_id;
        private String bankcard_type;
        private String bankcard_num;
        private String real_name;
        private String price;
        private int status;
        private Object remark;
        private String create_time;
        private String update_time;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getOrder_sn() {
            return order_sn;
        }

        public void setOrder_sn(String order_sn) {
            this.order_sn = order_sn;
        }

        public int getBankcard_id() {
            return bankcard_id;
        }

        public void setBankcard_id(int bankcard_id) {
            this.bankcard_id = bankcard_id;
        }

        public String getBankcard_type() {
            return bankcard_type;
        }

        public void setBankcard_type(String bankcard_type) {
            this.bankcard_type = bankcard_type;
        }

        public String getBankcard_num() {
            return bankcard_num;
        }

        public void setBankcard_num(String bankcard_num) {
            this.bankcard_num = bankcard_num;
        }

        public String getReal_name() {
            return real_name;
        }

        public void setReal_name(String real_name) {
            this.real_name = real_name;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public Object getRemark() {
            return remark;
        }

        public void setRemark(Object remark) {
            this.remark = remark;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }
    }
}
