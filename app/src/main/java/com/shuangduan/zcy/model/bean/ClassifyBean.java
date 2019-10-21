package com.shuangduan.zcy.model.bean;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.model.bean
 * @class describe
 * @time 2019/7/11 14:57
 * @change
 * @chang time
 * @class describe
 */
public class ClassifyBean {
    public static final int GCXX = 1;
    public static final int ZCXX = 2;
    public static final int JJWZ = 3;
    public static final int YZGYS = 4;

    public static final int PQZX = 5;
    public static final int WDSY = 6;
    public static final int FBXQ = 7;
    public static final int FBXX = 8;
    private int type;
    private int img;
    private String title;

    public ClassifyBean(int img, String title, int type) {
        this.img = img;
        this.title = title;
        this.type = type;
    }

    public int getImg() {
        return img;
    }

    public String getTitle() {
        return title;
    }

    public int getType() {
        return type;
    }
}
