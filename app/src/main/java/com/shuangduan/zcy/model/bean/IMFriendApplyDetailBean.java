package com.shuangduan.zcy.model.bean;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.model.bean
 * @class describe
 * @time 2019/8/29 9:31
 * @change
 * @chang time
 * @class describe
 */
public class IMFriendApplyDetailBean {

    /**
     * id : 2
     * username : 丁树国
     * image : http://information-api.oss-cn-qingdao.aliyuncs.com/f06135bc11230fcbcd1d9d8231856469.jpg
     * receive_user_id : 19
     * apply_user_id : 48
     * apply_user_msg : 你好
     * apply_status : 1
     * create_time : 2019-08-28 13:50:07
     */

    private int id;
    private String username;
    private String image;
    private int receive_user_id;
    private int apply_user_id;
    private String apply_user_msg;
    private int apply_status;
    private String create_time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public int getReceive_user_id() {
        return receive_user_id;
    }

    public void setReceive_user_id(int receive_user_id) {
        this.receive_user_id = receive_user_id;
    }

    public int getApply_user_id() {
        return apply_user_id;
    }

    public void setApply_user_id(int apply_user_id) {
        this.apply_user_id = apply_user_id;
    }

    public String getApply_user_msg() {
        return apply_user_msg;
    }

    public void setApply_user_msg(String apply_user_msg) {
        this.apply_user_msg = apply_user_msg;
    }

    public int getApply_status() {
        return apply_status;
    }

    public void setApply_status(int apply_status) {
        this.apply_status = apply_status;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }
}
