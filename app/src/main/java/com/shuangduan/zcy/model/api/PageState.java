package com.shuangduan.zcy.model.api;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.model.api
 * @class describe
 * @time 2019/7/19 8:56
 * @change
 * @chang time
 * @class describe
 */
public class PageState {

    public static final String PAGE_NORMAL = "PAGE_NORMAL";//正常界面
    public static final String PAGE_LOADING = "PAGE_LOADING";//加载中
    public static final String PAGE_REFRESH = "PAGE_REFRESH";//刷新状态
    public static final String PAGE_LOAD_SUCCESS = "PAGE_LOAD_SUCCESS";//加载成功
    public static final String PAGE_NET_ERROR = "PAGE_NET_ERROR";//无网络
    public static final String PAGE_SERVICE_ERROR = "PAGE_SERVICE_ERROR";//服务器返回成功，非200状态
    public static final String PAGE_ERROR = "PAGE_ERROR";//报错

}
