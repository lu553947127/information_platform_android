package com.shuangduan.zcy.model.bean;

import java.util.List;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.model.bean
 * @class describe
 * @time 2019/8/29 9:27
 * @change
 * @chang time
 * @class describe
 */
public class IMFriendSearchBean {
    /**
     * code : 200
     * msg : 成功
     * time : 1567751529
     * data : {"friend":[{"userId":21,"name":"李彤1","portraitUri":"http://information-api.oss-cn-qingdao.aliyuncs.com/d8599639c512386c19c893e1038eca63.png","company":"技术上就是就是就是"},{"userId":20,"name":"王宝宝1李","portraitUri":"http://information-api.oss-cn-qingdao.aliyuncs.com/d8599639c512386c19c893e1038eca63.png","company":"济南双端1科技有限公司"},{"userId":19,"name":"荣云测试1李","portraitUri":"http://information-api.oss-cn-qingdao.aliyuncs.com/581db79fb67428a12e518269b14ff8fd.jpeg","company":"哇哈哈公司"}],"group":[{"group_id":15,"group_name":"新模式李","province":"内蒙古自治区","city":"包头市"},{"group_id":14,"group_name":"提供给李","province":"河北省","city":"唐山市"},{"group_id":9,"group_name":"丹锡李高速公路克什克腾至承德联络线克什克腾（经棚）至乌兰布统 （蒙冀界）段公路施工总承包","province":"内蒙古自治区","city":""}]}
     */

    private String code;
    private String msg;
    private int time;
    private DataBean data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
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
}
