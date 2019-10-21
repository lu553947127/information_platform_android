package com.shuangduan.zcy.model.bean;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.model.bean
 * @class describe
 * @time 2019/8/20 18:49
 * @change
 * @chang time
 * @class describe
 */
public class RelationshipDetailBean {

    /**
     * price : 1000.00
     * start_time : 2019-08-05
     * end_time : 2019-08-30
     * title : 找工程
     * id : 8
     * user_id : 48
     * intro : 找工程信息
     * reply_id : 0
     * validity : 2019-08-05—2019-08-30
     * reply_status : 1
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
}
