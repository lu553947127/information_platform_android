package com.shuangduan.zcy.model.bean;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.model.bean
 * @class describe
 * @time 2019/8/15 9:30
 * @change
 * @chang time
 * @class describe
 */
public class HomeBannerBean {

    /**
     * images : 图片2.gif
     * type : 1
     * url :
     * app_jump :
     */

    private String images;
    private int type;
    private String url;
    private String app_jump;

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getApp_jump() {
        return app_jump;
    }

    public void setApp_jump(String app_jump) {
        this.app_jump = app_jump;
    }
}
