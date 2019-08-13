package com.shuangduan.zcy.model.bean;

import java.util.List;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.model.bean
 * @class describe
 * @time 2019/7/23 11:48
 * @change
 * @chang time
 * @class describe
 */
public class TrackBean {

    /**
     * page : 1
     * count : 3
     * list : [{"amount":3,"name":"都...","tel":"178****8888","user_id":19,"price":"112.00","remarks":"的士大夫士大...","create_time":"2019-07-16 10:08:27","is_pay":0,"image":[{"thumbnail":"http://api.info.com/uploads/thumb/20190716/707aa171fa4dd1616844dd66d18176ed.jpg","source":"http://api.info.com/uploads/20190716/707aa171fa4dd1616844dd66d18176ed.jpg"}]}]
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
         * amount : 3
         * name : 都...
         * tel : 178****8888
         * user_id : 19
         * price : 112.00
         * remarks : 的士大夫士大...
         * create_time : 2019-07-16 10:08:27
         * is_pay : 0
         * image : [{"thumbnail":"http://api.info.com/uploads/thumb/20190716/707aa171fa4dd1616844dd66d18176ed.jpg","source":"http://api.info.com/uploads/20190716/707aa171fa4dd1616844dd66d18176ed.jpg"}]
         */

        private int id;
        private String name;
        private String tel;
        private int user_id;
        private String price;
        private String remarks;
        private String create_time;
        private int is_pay;
        private List<ImageBean> image;
        private String avatar;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public int getIs_pay() {
            return is_pay;
        }

        public void setIs_pay(int is_pay) {
            this.is_pay = is_pay;
        }

        public List<ImageBean> getImage() {
            return image;
        }

        public void setImage(List<ImageBean> image) {
            this.image = image;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public static class ImageBean {
            /**
             * thumbnail : http://api.info.com/uploads/thumb/20190716/707aa171fa4dd1616844dd66d18176ed.jpg
             * source : http://api.info.com/uploads/20190716/707aa171fa4dd1616844dd66d18176ed.jpg
             */

            private String thumbnail;
            private String source;

            public String getThumbnail() {
                return thumbnail;
            }

            public void setThumbnail(String thumbnail) {
                this.thumbnail = thumbnail;
            }

            public String getSource() {
                return source;
            }

            public void setSource(String source) {
                this.source = source;
            }
        }
    }
}
