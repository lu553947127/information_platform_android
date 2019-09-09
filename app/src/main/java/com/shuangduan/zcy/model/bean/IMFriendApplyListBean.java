package com.shuangduan.zcy.model.bean;

import java.util.List;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.model.bean
 * @class describe
 * @time 2019/8/29 9:33
 * @change
 * @chang time
 * @class describe
 */
public class IMFriendApplyListBean {
    /**
     * code : 200
     * msg : 成功
     * time : 1568012792
     * data : {"page":1,"count":1,"list":[{"id":8,"username":"李世","image":"http://information-api.oss-cn-qingdao.aliyuncs.com/38d07a6a0d112c65e2671849d502a02a.jpg","apply_user_id":31,"receive_user_id":85,"apply_user_msg":"李晓迪","receive_user_msg":null,"apply_status":1,"look_status":1,"create_time":"2019-09-07 16:00:00"}]}
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
         * page : 1
         * count : 1
         * list : [{"id":8,"username":"李世","image":"http://information-api.oss-cn-qingdao.aliyuncs.com/38d07a6a0d112c65e2671849d502a02a.jpg","apply_user_id":31,"receive_user_id":85,"apply_user_msg":"李晓迪","receive_user_msg":null,"apply_status":1,"look_status":1,"create_time":"2019-09-07 16:00:00"}]
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
             * id : 8
             * username : 李世
             * image : http://information-api.oss-cn-qingdao.aliyuncs.com/38d07a6a0d112c65e2671849d502a02a.jpg
             * apply_user_id : 31
             * receive_user_id : 85
             * apply_user_msg : 李晓迪
             * receive_user_msg : null
             * apply_status : 1
             * look_status : 1
             * create_time : 2019-09-07 16:00:00
             */

            private String id;
            private String username;
            private String image;
            private int apply_user_id;
            private int receive_user_id;
            private String apply_user_msg;
            private String receive_user_msg;
            private int apply_status;
            private int look_status;
            private String create_time;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
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

            public int getApply_user_id() {
                return apply_user_id;
            }

            public void setApply_user_id(int apply_user_id) {
                this.apply_user_id = apply_user_id;
            }

            public int getReceive_user_id() {
                return receive_user_id;
            }

            public void setReceive_user_id(int receive_user_id) {
                this.receive_user_id = receive_user_id;
            }

            public String getApply_user_msg() {
                return apply_user_msg;
            }

            public void setApply_user_msg(String apply_user_msg) {
                this.apply_user_msg = apply_user_msg;
            }

            public String getReceive_user_msg() {
                return receive_user_msg;
            }

            public void setReceive_user_msg(String receive_user_msg) {
                this.receive_user_msg = receive_user_msg;
            }

            public int getApply_status() {
                return apply_status;
            }

            public void setApply_status(int apply_status) {
                this.apply_status = apply_status;
            }

            public int getLook_status() {
                return look_status;
            }

            public void setLook_status(int look_status) {
                this.look_status = look_status;
            }

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }
        }
    }
}
