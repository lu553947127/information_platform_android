package com.shuangduan.zcy.model.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.model.bean
 * @class describe
 * @time 2019/8/29 9:27
 * @change
 * @chang time
 * @class describe
 */
public class IMFriendSearchBean {
    private List<FriendBean> friend;
    private List<GroupBean> group;

    public List<FriendBean> getFriend() {
        return friend;
    }

    public void setFriend(List<FriendBean> friend) {
        this.friend = friend;
    }

    public List<GroupBean> getGroup() {
        return group;
    }

    public void setGroup(List<GroupBean> group) {
        this.group = group;
    }

    public static class FriendBean {
        /**
         * userId : 21
         * name : 李彤1
         * portraitUri : http://information-api.oss-cn-qingdao.aliyuncs.com/d8599639c512386c19c893e1038eca63.png
         * company : 技术上就是就是就是
         */

        private String userId;
        private String name;
        private String portraitUri;
        private String company;

        //身份认证状态：0未认证 1审核中 2已认证
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

    public static class GroupBean {
        /**
         * group_id : 15
         * group_name : 新模式李
         * province : 内蒙古自治区
         * city : 包头市
         */

        private String group_id;
        private String group_name;
        private String province;
        private String city;

        public String getGroup_id() {
            return group_id;
        }

        public void setGroup_id(String group_id) {
            this.group_id = group_id;
        }

        public String getGroup_name() {
            return group_name;
        }

        public void setGroup_name(String group_name) {
            this.group_name = group_name;
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
