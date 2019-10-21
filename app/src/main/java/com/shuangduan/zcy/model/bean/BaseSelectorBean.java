package com.shuangduan.zcy.model.bean;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.weight.selectorview
 * @class describe
 * @time 2019/7/13 9:14
 * @change
 * @chang time
 * @class describe
 */
public class BaseSelectorBean {

    //0 未选中 1 选中
    public int isSelect;//选中状态
    public int isCheck;//点击状态

    public int getIsSelect() {
        return isSelect;
    }

    public void setIsSelect(int isSelect) {
        this.isSelect = isSelect;
    }

    public int getIsCheck() {
        return isCheck;
    }

    public void setIsCheck(int isCheck) {
        this.isCheck = isCheck;
    }
}
