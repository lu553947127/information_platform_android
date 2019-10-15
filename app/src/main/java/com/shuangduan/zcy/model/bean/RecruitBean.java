package com.shuangduan.zcy.model.bean;

import java.util.List;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.model.bean
 * @class describe
 * @time 2019/7/12 9:02
 * @change
 * @chang time
 * @class describe
 */
public class RecruitBean {

    /**
     * page : 1
     * count : 65
     * list : [{"amount":68,"title":"中铁十九局集团有限公司G3011敦当高速公路DD1合同段施工总承包项目柴油招标采购方案招标公告","content":"\" ","province":"甘肃省","city":"敦煌市","create_time":"2019-07-04 23:52:18","is_pay":0}]
     */

    private int page;
    private int count;
    private int rec;
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

    public int getRec() {
        return rec;
    }

    public void setRec(int rec) {
        this.rec = rec;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * amount : 68
         * title : 中铁十九局集团有限公司G3011敦当高速公路DD1合同段施工总承包项目柴油招标采购方案招标公告
         * content : "
         * province : 甘肃省
         * city : 敦煌市
         * create_time : 2019-07-04 23:52:18
         * is_pay : 0
         */

        private int id;
        private String title;
        private String content;
        private String province;
        private String city;
        private String create_time;
        private int is_pay;

        private String area;


        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

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

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public int getIs_pay() {
            return is_pay;
        }

        public void setIs_pay(int is_pay) {
            this.is_pay = is_pay;
        }
    }
}
