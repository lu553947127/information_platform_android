package com.shuangduan.zcy.model.bean;

import java.util.List;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.model.bean
 * @class describe
 * @time 2019/8/2 9:37
 * @change
 * @chang time
 * @class describe
 */
public class ProjectSearchBean {

    /**
     * count : 13
     * list : [{"amount":2786,"title":"中国水电建设集团十五工程局有限公司广西左江治旱驮英水库枢纽项目","province":"","city":""}]
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
         * amount : 2786
         * title : 中国水电建设集团十五工程局有限公司广西左江治旱驮英水库枢纽项目
         * province :
         * city :
         */

        private int id;
        private String title;
        private String province;
        private String city;

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
    }
}
