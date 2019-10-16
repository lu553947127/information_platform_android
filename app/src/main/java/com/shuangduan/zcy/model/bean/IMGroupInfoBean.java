package com.shuangduan.zcy.model.bean;

import java.util.List;

/**
 * @author 鹿鸿祥 QQ:553947127
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.model.bean
 * @class describe
 * @time 2019/9/11 15:28
 * @change
 * @chang time
 * @class describe
 */
public class IMGroupInfoBean {
    /**
     * code : 200
     * msg : 成功
     * time : 1568186639
     * data : {"info":{"id":8,"group_name":"陕西合阳至铜川、吴起至华池高速公路PPP（HTTJ-04标）李","province":"陕西省","city":""},"page":"1","count":3,"list":[{"id":85,"image":"http://information-api.oss-cn-qingdao.aliyuncs.com/eaab08e3c8d8a86ffd97814a905ec4fc.jpeg","username":"鹿鸿祥"},{"id":33,"image":"http://information-api.oss-cn-qingdao.aliyuncs.com/5adb0e33d685223bfe79a51fee17431f.png","username":"哒哒哒哈"},{"id":55,"image":"http://information-api.oss-cn-qingdao.aliyuncs.com/accdf4f9ffb831d42eafe7614cb9560d.jpg","username":"vhh1"}]}
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
        /**
         * info : {"id":8,"group_name":"陕西合阳至铜川、吴起至华池高速公路PPP（HTTJ-04标）李","province":"陕西省","city":""}
         * page : 1
         * count : 3
         * list : [{"id":85,"image":"http://information-api.oss-cn-qingdao.aliyuncs.com/eaab08e3c8d8a86ffd97814a905ec4fc.jpeg","username":"鹿鸿祥"},{"id":33,"image":"http://information-api.oss-cn-qingdao.aliyuncs.com/5adb0e33d685223bfe79a51fee17431f.png","username":"哒哒哒哈"},{"id":55,"image":"http://information-api.oss-cn-qingdao.aliyuncs.com/accdf4f9ffb831d42eafe7614cb9560d.jpg","username":"vhh1"}]
         */

        private InfoBean info;
        private int page;
        private int count;
        private List<ListBean> list;

        public InfoBean getInfo() {
            return info;
        }

        public void setInfo(InfoBean info) {
            this.info = info;
        }

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

        public static class InfoBean {
            /**
             * id : 8
             * group_name : 陕西合阳至铜川、吴起至华池高速公路PPP（HTTJ-04标）李
             * province : 陕西省
             * city :
             */

            private int id;
            private String group_name;
            private String province;
            private String city;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
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

        public static class ListBean {
            /**
             * id : 85
             * image : http://information-api.oss-cn-qingdao.aliyuncs.com/eaab08e3c8d8a86ffd97814a905ec4fc.jpeg
             * username : 鹿鸿祥
             */

            private int id;
            private String image;
            private String username;
            private int cardStatus;

            public ListBean(String username) {
                this.username = username;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public int getCardStatus() {
                return cardStatus;
            }

            public void setCardStatus(int cardStatus) {
                this.cardStatus = cardStatus;
            }
        }
    }
}
