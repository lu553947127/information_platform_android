package com.shuangduan.zcy.swipeback;

/**
 * 项目名称：智慧街道工作人员端标准版
 * 类名称：SwipeBackActivityBase
 * 类描述：仿ios右滑删除
 * 创建人：鹿鸿祥
 * 创建时间：2019年04月11日 下午14:50:35
 * 修改人：鹿鸿祥
 * 修改时间：2019年04月11日 下午14:50:35
 * 修改备注：
 */
public interface SwipeBackActivityBase {

    /**
     * @return the SwipeBackLayout associated with this activity.
     */
    public abstract SwipeBackLayout getSwipeBackLayout();

    public abstract void setSwipeBackEnable(boolean enable);

    /**
     * Scroll out contentView and finish the activity
     */
    public abstract void scrollToFinishActivity();
}
