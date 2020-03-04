package com.zcy.baselibrary.navigationBar;

/**
 * @ProjectName: information_platform_android
 * @Package: com.zcy.baselibrary.navigationBar
 * @ClassName: INavigationBar
 * @Description: 定义头部标题导航栏规范
 * @Author: 徐玉
 * @CreateDate: 2020/3/4 11:20
 * @UpdateUser:
 * @UpdateDate:
 * @UpdateRemark:
 * @Version: 1.0
 */
public interface INavigationBar {
    //头部资源ID
    int bindLayoutId();

    //绑定头部参数
    void applyView();
}

