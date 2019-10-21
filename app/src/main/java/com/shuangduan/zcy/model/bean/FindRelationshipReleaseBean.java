package com.shuangduan.zcy.model.bean;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.model.bean
 * @class describe
 * @time 2019/8/22 9:53
 * @change
 * @chang time
 * @class describe
 */
public class FindRelationshipReleaseBean {

    /**
     * price : 5.00
     * start_time : 2019-08-06
     * end_time : 2019-09-06
     * title : 山东省济南市高新区齐盛广场项目
     * id : 4
     * user_id : 49
     * intro : 寻找高新区齐盛广场项目，希望得到相关项目信息，以及联系人姓名和电话
     * reply_id : 2
     * validity : 2019-08-06—2019-09-06
     * reply_status : 2
     * reply : {"id":2,"reason":"","name":"刘明浩","tel":"13698608316","intro":"山东省济南市历下区三庆齐盛广场5号楼1605","relationships_id":4,"update_time":"2019-08-14 17:33:12","create_time":"2019-08-06 21:43:36","status":1,"user_id":49}
     */

    private String price;
    private String start_time;
    private String end_time;
    private String title;
    private int id;
    private int user_id;
    private String intro;
    private int reply_id;
    private String validity;
    private int reply_status;
    private ReplyBean reply;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

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

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public int getReply_id() {
        return reply_id;
    }

    public void setReply_id(int reply_id) {
        this.reply_id = reply_id;
    }

    public String getValidity() {
        return validity;
    }

    public void setValidity(String validity) {
        this.validity = validity;
    }

    public int getReply_status() {
        return reply_status;
    }

    public void setReply_status(int reply_status) {
        this.reply_status = reply_status;
    }

    public ReplyBean getReply() {
        return reply;
    }

    public void setReply(ReplyBean reply) {
        this.reply = reply;
    }

    public static class ReplyBean {
        /**
         * id : 2
         * reason :
         * name : 刘明浩
         * tel : 13698608316
         * intro : 山东省济南市历下区三庆齐盛广场5号楼1605
         * relationships_id : 4
         * update_time : 2019-08-14 17:33:12
         * create_time : 2019-08-06 21:43:36
         * status : 1
         * user_id : 49
         */

        private int id;
        private String reason;
        private String name;
        private String tel;
        private String intro;
        private int relationships_id;
        private String update_time;
        private String create_time;
        private int status;
        private int user_id;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
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

        public String getIntro() {
            return intro;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }

        public int getRelationships_id() {
            return relationships_id;
        }

        public void setRelationships_id(int relationships_id) {
            this.relationships_id = relationships_id;
        }

        public String getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }
    }
}
