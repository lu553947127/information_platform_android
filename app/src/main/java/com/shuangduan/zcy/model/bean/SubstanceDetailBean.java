package com.shuangduan.zcy.model.bean;

import java.util.List;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.model.bean
 * @class describe
 * @time 2019/8/21 11:31
 * @change
 * @chang time
 * @class describe
 */
public class SubstanceDetailBean {

    /**
     * info : {"id":1,"material_name":"找物资挂篮杆件","count":"1","tel":"186****3913","project_name":"找物资项目","address":"高新区三庆齐盛广场","acceptance_price":"1.00","price":"0.01","start_time":"2019-08-16","end_time":"2019-09-07","is_pay":0}
     * list : [{"id":3,"material_name":"找买家杆件222","count":"2","tel":"186****3913","project_name":"项目名称","address":"项目地址","acceptance_price":"0.01","price":"0.00","start_time":"2019-08-16","end_time":"2019-09-07","is_pay":0}]
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
         * id : 1
         * material_name : 找物资挂篮杆件
         * count : 1
         * tel : 186****3913
         * project_name : 找物资项目
         * address : 高新区三庆齐盛广场
         * acceptance_price : 1.00
         * price : 0.01
         * start_time : 2019-08-16
         * end_time : 2019-09-07
         * is_pay : 0
         */

        private int id;
        private String material_name;
        private String count;
        private String real_name;
        private String tel;
        private String project_name;
        private String address;
        private String acceptance_price;
        private String price;
        private String start_time;
        private String end_time;
        private int is_pay;
        private String remark;//找物资详情描述
        private int status;

        @Override
        public String toString() {
            return "InfoBean{" +
                    "id=" + id +
                    ", material_name='" + material_name + '\'' +
                    ", count='" + count + '\'' +
                    ", real_name='" + real_name + '\'' +
                    ", tel='" + tel + '\'' +
                    ", project_name='" + project_name + '\'' +
                    ", address='" + address + '\'' +
                    ", acceptance_price='" + acceptance_price + '\'' +
                    ", price='" + price + '\'' +
                    ", start_time='" + start_time + '\'' +
                    ", end_time='" + end_time + '\'' +
                    ", is_pay=" + is_pay +
                    ", remark='" + remark + '\'' +
                    '}';
        }

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

        public String getReal_name() {
            return real_name;
        }

        public void setReal_name(String real_name) {
            this.real_name = real_name;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getProject_name() {
            return project_name;
        }

        public void setProject_name(String project_name) {
            this.project_name = project_name;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getAcceptance_price() {
            return acceptance_price;
        }

        public void setAcceptance_price(String acceptance_price) {
            this.acceptance_price = acceptance_price;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
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

        public int getIs_pay() {
            return is_pay;
        }

        public void setIs_pay(int is_pay) {
            this.is_pay = is_pay;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getRemark() {
            return remark;
        }
    }

    public static class ListBean {
        /**
         * id : 3
         * material_name : 找买家杆件222
         * count : 2
         * tel : 186****3913
         * project_name : 项目名称
         * address : 项目地址
         * acceptance_price : 0.01
         * price : 0.00
         * start_time : 2019-08-16
         * end_time : 2019-09-07
         * is_pay : 0
         */

        private int id;
        private String material_name;
        private String count;
        private String tel;
        private String real_name;
        private String project_name;
        private String address;
        private String acceptance_price;
        private String price;
        private int way;
        private String start_time;
        private String end_time;
        private int is_pay;

        //推荐
        private String remark;


        public String getRemark() {
            return remark;
        }

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

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getReal_name() {
            return real_name;
        }

        public void setReal_name(String real_name) {
            this.real_name = real_name;
        }

        public String getProject_name() {
            return project_name;
        }

        public void setProject_name(String project_name) {
            this.project_name = project_name;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getAcceptance_price() {
            return acceptance_price;
        }

        public void setAcceptance_price(String acceptance_price) {
            this.acceptance_price = acceptance_price;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public int getWay() {
            return way;
        }

        public void setWay(int way) {
            this.way = way;
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

        public int getIs_pay() {
            return is_pay;
        }

        public void setIs_pay(int is_pay) {
            this.is_pay = is_pay;
        }
    }
}
