package com.shuangduan.zcy.model.bean;

import java.util.List;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.model.bean
 * @class describe
 * @time 2019/8/5 9:02
 * @change
 * @chang time
 * @class describe
 */
public class SubscribeBean {
    /**
     * page : 1
     * count : 14
     * list : [{"id":1594,"user_id":48,"type":1,"type_id":0,"title":"【易基建】闲置物资","content":"ydydy新上架闲置物资，包括横移运梁平车查看详情>>","send_time":"今天 13:51","is_view":0},{"id":1593,"user_id":48,"type":1,"type_id":0,"title":"【易基建】闲置物资","content":"ydydy新上架闲置物资，包括横移运梁平车查看详情>>","send_time":"今天 13:51","is_view":0},{"id":1592,"user_id":48,"type":1,"type_id":0,"title":"【易基建】闲置物资","content":"中国测试有限公司新上架闲置物资，包括钢轨、木板、H型钢、推土机、高位可搬式沥...查看详情>>","send_time":"今天 13:51","is_view":0},{"id":1590,"user_id":48,"type":1,"type_id":0,"title":"【易基建】闲置物资","content":"中国测试有限公司新上架闲置物资，包括钢轨、木板、H型钢、推土机、高位可搬式沥...查看详情>>","send_time":"今天 13:51","is_view":0},{"id":1591,"user_id":48,"type":1,"type_id":0,"title":"【易基建】闲置物资","content":"中国测试有限公司新上架闲置物资，包括钢轨、木板、H型钢、推土机、高位可搬式沥...查看详情>>","send_time":"今天 13:51","is_view":0}]
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
         * id : 1594
         * user_id : 48
         * type : 1
         * type_id : 0
         * title : 【易基建】闲置物资
         * content : ydydy新上架闲置物资，包括横移运梁平车查看详情>>
         * send_time : 今天 13:51
         * is_view : 0
         */

        private int id;
        private int user_id;
        private int type;
        private int type_id;
        private String title;
        private String content;
        private String send_time;
        private int is_view;

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

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getType_id() {
            return type_id;
        }

        public void setType_id(int type_id) {
            this.type_id = type_id;
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

        public String getSend_time() {
            return send_time;
        }

        public void setSend_time(String send_time) {
            this.send_time = send_time;
        }

        public int getIs_view() {
            return is_view;
        }

        public void setIs_view(int is_view) {
            this.is_view = is_view;
        }
    }
}
