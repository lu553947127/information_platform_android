package com.shuangduan.zcy.model.bean;

import java.util.List;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.model.bean
 * @class describe
 * @time 2019/7/12 19:39
 * @change
 * @chang time
 * @class describe
 */
public class ProjectInfoBean {

    /**
     * page : 1
     * count : 258
     * list : [{"id":1536,"longitude":"0.000000","latitude":"0.000000","title":"F息烽至黔西高速公路TJ-2标","intro":"项目起点位于小寨坝顺接开阳至息烽高速公路，与贵遵高...","phases":"项目完成","warrant_status":0,"subscription_num":0,"update_time":"2019-07-19 11:55:31"}]
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
         * id : 1536
         * longitude : 0.000000
         * latitude : 0.000000
         * title : F息烽至黔西高速公路TJ-2标
         * intro : 项目起点位于小寨坝顺接开阳至息烽高速公路，与贵遵高...
         * phases : 项目完成
         * warrant_status : 0
         * subscription_num : 0
         * update_time : 2019-07-19 11:55:31
         */

        private int id;
        private String longitude;
        private String latitude;
        private String title;
        private String intro;
        private String phases;
        private int warrant_status;
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

        public int getWarrant_status() {
            return warrant_status;
        }

        public void setWarrant_status(int warrant_status) {
            this.warrant_status = warrant_status;
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
