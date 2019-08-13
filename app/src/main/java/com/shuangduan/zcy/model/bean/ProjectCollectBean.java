package com.shuangduan.zcy.model.bean;

import java.util.List;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.model.bean
 * @class describe
 * @time 2019/8/5 14:43
 * @change
 * @chang time
 * @class describe
 */
public class ProjectCollectBean {

    /**
     * page : 1
     * count : 5
     * list : [{"amount":2869,"title":"中国电信","phases":"工程构思","create_time":"2019-08-02 17:51:47","intro":""}]
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
         * amount : 2869
         * title : 中国电信
         * phases : 工程构思
         * create_time : 2019-08-02 17:51:47
         * intro :
         */

        private int id;
        private String title;
        private String phases;
        private String create_time;
        private String intro;

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

        public String getPhases() {
            return phases;
        }

        public void setPhases(String phases) {
            this.phases = phases;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getIntro() {
            return intro;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }
    }
}
