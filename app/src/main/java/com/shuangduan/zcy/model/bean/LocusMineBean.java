package com.shuangduan.zcy.model.bean;

import java.util.List;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.model.bean
 * @class describe
 * @time 2019/7/16 20:41
 * @change
 * @chang time
 * @class describe
 */
public class LocusMineBean {

    /**
     * page : 1
     * count : 3
     * list : [{"id":2709,"title":"中建八局阳新高速黄河大桥","intro":"濮阳至阳新高速，北起河南濮阳南到湖北阳新，位于中原...","phases":"项目完成","track_count":0,"create_time":"2019-07-21 06:29:49"}]
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
         * id : 2709
         * title : 中建八局阳新高速黄河大桥
         * intro : 濮阳至阳新高速，北起河南濮阳南到湖北阳新，位于中原...
         * phases : 项目完成
         * track_count : 0
         * create_time : 2019-07-21 06:29:49
         */

        private int id;
        private String title;
        private String intro;
        private String phases;
        private int track_count;
        private String create_time;

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

        public int getTrack_count() {
            return track_count;
        }

        public void setTrack_count(int track_count) {
            this.track_count = track_count;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }
    }
}
