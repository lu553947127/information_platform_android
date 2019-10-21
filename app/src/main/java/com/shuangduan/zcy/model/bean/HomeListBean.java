package com.shuangduan.zcy.model.bean;

import java.util.List;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.model.bean
 * @class describe
 * @time 2019/8/15 9:32
 * @change
 * @chang time
 * @class describe
 */
public class HomeListBean {

    private List<HeadlinesBean> Headlines;
    private List<ExplainBean> explain;

    public List<HeadlinesBean> getHeadlines() {
        return Headlines;
    }

    public void setHeadlines(List<HeadlinesBean> Headlines) {
        this.Headlines = Headlines;
    }

    public List<ExplainBean> getExplain() {
        return explain;
    }

    public void setExplain(List<ExplainBean> explain) {
        this.explain = explain;
    }

    public static class HeadlinesBean {
        /**
         * id : 18
         * title : 中国铁设千亿铁路总承包再下一城：新建汕头至汕尾铁路传来喜报
         * create_time : 2019-07-19 14:13:08
         * oss_path : http://zicaiyun-pc.oss-cn-qingdao.aliyuncs.com/32ab0711bcbb5ca396f47c7ea5a6b8be.jpg
         */

        private int id;
        private String title;
        private String create_time;
        private String image;

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

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }

    public static class ExplainBean {
        /**
         * id : 1
         * title : 人脉收益说明书
         */

        private int id;
        private String title;
        private String content;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
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
    }
}
