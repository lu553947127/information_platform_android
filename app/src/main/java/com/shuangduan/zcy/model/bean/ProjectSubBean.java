package com.shuangduan.zcy.model.bean;

import java.util.List;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.model.bean
 * @class describe
 * @time 2019/8/5 9:02
 * @change
 * @chang time
 * @class describe
 */
public class ProjectSubBean {

    /**
     * page : 1
     * count : 40
     * list : [{"type_id":2557,"create_time":"2019-07-25 15:43:07","title":"江北高速1标","intro":"本项目起于宜昌市太平溪新港，经沙坪跨乐天...","phases":"工程构思"},{"type_id":2558,"create_time":"2019-07-25 15:43:07","title":"兰州至海口国家高速公路重庆至遵义段（贵州境）扩容工程第CZTJ-11标段","intro":"项目起于与重庆交界的下坪，与重遵扩容（重...","phases":"勘察设计"}]
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
         * type_id : 2557
         * create_time : 2019-07-25 15:43:07
         * title : 江北高速1标
         * intro : 本项目起于宜昌市太平溪新港，经沙坪跨乐天...
         * phases : 工程构思
         */

        private int type_id;
        private String create_time;
        private String title;
        private String intro;
        private String phases;

        public int getType_id() {
            return type_id;
        }

        public void setType_id(int type_id) {
            this.type_id = type_id;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
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
    }
}
