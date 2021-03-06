package com.shuangduan.zcy.model.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.model.bean
 * @class describe
 * @time 2019/7/23 11:57
 * @change
 * @chang time
 * @class describe
 */
public class ConsumeBean {

    /**
     * page : 1
     * count : 1
     * list : [{"user_id":26,"count":2,"username":"","image":""}]
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
         * user_id : 26
         * count : 2
         * username :
         * image :
         */

        private int user_id;
        private int count;
        private String username;
        private String image;

        //身份认证标识：0未认证 1审核中 2已认证
        @SerializedName("card_status")
        private int cardStatus;

        public int getCardStatus() {
            return cardStatus;
        }

        public void setCardStatus(int cardStatus) {
            this.cardStatus = cardStatus;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }
}
