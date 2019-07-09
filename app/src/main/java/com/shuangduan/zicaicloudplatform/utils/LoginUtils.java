package com.shuangduan.zicaicloudplatform.utils;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.shuangduan.zicaicloudplatform.app.SpConfig;

/**
 * <pre>
 *     author : 宁文强
 *     e-mail : ningwenqiang@lanhuiplay.com
 *     time   : 2018/09/01
 *     desc   : 检测是否登录
 *     version: 1.0
 * </pre>
 */

public class LoginUtils {

    /**
     * 检测登录，未登录则跳转登录界面
     * @return
     */
    public static boolean checkLogin(){
        String token = SPUtils.getInstance().getString(SpConfig.TOKEN);
        if (StringUtils.isTrimEmpty(token)){
//            ActivityUtils.startActivity(LoginActivity.class);
            return false;
        }
        return true;
    }

    /**
     * 是否登录
     * @return
     */
    public static boolean isLogin(){
        String token = SPUtils.getInstance().getString(SpConfig.TOKEN);
        if (StringUtils.isTrimEmpty(token)){
            return false;
        }
        return true;
    }

}
