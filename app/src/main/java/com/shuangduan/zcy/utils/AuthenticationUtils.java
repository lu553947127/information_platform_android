package com.shuangduan.zcy.utils;

import android.os.Bundle;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SPUtils;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.view.mine.AuthenticationActivity;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.utils
 * @class describe 实名认证检测工具
 * @time 2019/8/28 11:36
 * @change
 * @chang time
 * @class describe
 */
public class AuthenticationUtils {
    /**
     * 没有认证调认证界面，认证过的返回true
     * @return
     */
    public static boolean Authentication(){
        int authentication = SPUtils.getInstance().getInt(SpConfig.IS_VERIFIED);
        if (authentication != 2){
            //去认证
            Bundle bundle = new Bundle();
            bundle.putString(CustomConfig.UPLOAD_TYPE, CustomConfig.uploadTypeIdCard);
            ActivityUtils.startActivity(bundle, AuthenticationActivity.class);
        }else {
            return true;
        }
        return false;
    }
}
