package com.shuangduan.zcy.model.bean;

public class Friend {

    private String userId;
    private String userName;
    private String avator;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAvator() {
        return avator;
    }

    public void setAvator(String avator) {
        this.avator = avator;
    }

    public Friend(String userId, String userName, String avator) {
        this.userId = userId;
        this.userName = userName;
        this.avator = avator;
    }
}
