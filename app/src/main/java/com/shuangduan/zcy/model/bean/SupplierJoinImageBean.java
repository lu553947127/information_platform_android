package com.shuangduan.zcy.model.bean;

import java.util.List;

/**
 * @author 徐玉 QQ:876885613
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
    private List<Integer> serve_address;

    public List<Integer> getImages() {
        return images;
    }

    public void setImages(List<Integer> images) {
        this.images = images;
    }

    public List<Integer> getServe_address() {
        return serve_address;
    }

    public void setServe_address(List<Integer> serve_address) {
        this.serve_address = serve_address;
    }
}
