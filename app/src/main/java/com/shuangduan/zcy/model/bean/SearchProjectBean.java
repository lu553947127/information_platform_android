package com.shuangduan.zcy.model.bean;

import java.util.List;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.model.bean
 * @class describe
 * @time 2019/7/23 8:54
 * @change
 * @chang time
 * @class describe
 */
public class SearchProjectBean {

    /**
     * page : 1
     * count : 23
     * list : [{"amount":2786,"longitude":"0.000000","latitude":"0.000000","title":"中国水电建设集团十五工程局有限公司广西左江治旱驮英水库枢纽项目","intro":"驮英水库位于广西宁明县那堪乡垌中村上游约6km的珠江流...","phases":"文件审批","subscription_num":0,"update_time":"2019-07-21 06:29:49"}]
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
         * longitude : 0.000000
         * latitude : 0.000000
         * title : 中国水电建设集团十五工程局有限公司广西左江治旱驮英水库枢纽项目
         * intro : 驮英水库位于广西宁明县那堪乡垌中村上游约6km的珠江流...
         * phases : 文件审批
         * subscription_num : 0
         * update_time : 2019-07-21 06:29:49
         */

        private int id;
        private String longitude;
        private String latitude;
        private String title;
        private String intro;
        private String phases;
        private int subscription_num;
        private String update_time;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getIntro() {
            return intro;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }

        public String getPhases() {
            return phases;
        }

        public void setPhases(String phases) {
            this.phases = phases;
        }

        public int getSubscription_num() {
            return subscription_num;
        }

        public void setSubscription_num(int subscription_num) {
            this.subscription_num = subscription_num;
        }

        public String getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }
    }
}
