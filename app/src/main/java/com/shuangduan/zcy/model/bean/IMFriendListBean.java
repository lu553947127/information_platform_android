package com.shuangduan.zcy.model.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.model.bean
 * @class describe
 * @time 2019/9/9 13:29
 * @change
 * @chang time
 * @class describe
 */
public class IMFriendListBean {
    /**
     * page : 1
     * count : 5
     * list : [{"userId":19,"name":"荣云测试1李","portraitUri":"http://information-api.oss-cn-qingdao.aliyuncs.com/581db79fb67428a12e518269b14ff8fd.jpeg","company":"哇哈哈公司"},{"userId":20,"name":"王宝宝1李","portraitUri":"http://information-api.oss-cn-qingdao.aliyuncs.com/d8599639c512386c19c893e1038eca63.png","company":"济南双端1科技有限公司"},{"userId":21,"name":"李彤1","portraitUri":"http://information-api.oss-cn-qingdao.aliyuncs.com/d8599639c512386c19c893e1038eca63.png","company":"技术上就是就是就是"},{"userId":48,"name":"丁树国1","portraitUri":"http://information-api.oss-cn-qingdao.aliyuncs.com/f06135bc11230fcbcd1d9d8231856469.jpg","company":"双端"},{"userId":87,"name":"李白","portraitUri":"http://information-api.oss-cn-qingdao.aliyuncs.com/50eeceb9a04cdb724977649e65c082bc.jpeg","company":"博远"}]
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
         * userId : 19
         * name : 荣云测试1李
         * portraitUri : http://information-api.oss-cn-qingdao.aliyuncs.com/581db79fb67428a12e518269b14ff8fd.jpeg
         * company : 哇哈哈公司
         */

        private String userId;
        private String name;
        private String portraitUri;

        public ListBean(String name) {
            this.name = name;
        }

        private String company;

        //省份验证状态：0未认证 1审核中 2已认证
        @SerializedName("card_status")
        private int cardStatus;

        public int getCardStatus() {
            return cardStatus;
        }

        public void setCardStatus(int cardStatus) {
            this.cardStatus = cardStatus;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPortraitUri() {
            return portraitUri;
        }

        public void setPortraitUri(String portraitUri) {
            this.portraitUri = portraitUri;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }
    }
}
