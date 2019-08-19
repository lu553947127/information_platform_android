package com.shuangduan.zcy.model.bean;

import java.util.List;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.model.bean
 * @class describe
 * @time 2019/8/12 15:18
 * @change
 * @chang time
 * @class describe
 */
public class SupplierJoinImageBean {
    private List<Integer> images;
    private int[] serve_address;

    public List<Integer> getImages() {
        return images;
    }

    public void setImages(List<Integer> images) {
        this.images = images;
    }

    public int[] getServe_address() {
        return serve_address;
    }

    public void setServe_address(int[] serve_address) {
        this.serve_address = serve_address;
    }
}
