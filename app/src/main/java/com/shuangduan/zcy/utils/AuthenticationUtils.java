package com.shuangduan.zcy.utils;

import android.app.Activity;
import android.os.Bundle;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SPUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.dialog.BaseDialog;
import com.shuangduan.zcy.dialog.CustomDialog;
import com.shuangduan.zcy.view.mine.user.AuthenticationActivity;

/**
 * @author 徐玉 QQ:876885613
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
    public static boolean Authentication(String type ){
        int authentication = SPUtils.getInstance().getInt(SpConfig.IS_VERIFIED);
        if (authentication != 2){
            //去认证
            Bundle bundle = new Bundle();
            bundle.putString(CustomConfig.UPLOAD_TYPE, CustomConfig.uploadTypeIdCard);
            bundle.putString(CustomConfig.AUTHENTICATION_TYPE, type);
            ActivityUtils.startActivity(bundle, AuthenticationActivity.class);
        }else {
            return true;
        }
        return false;
    }

    ///判断没有认证弹窗
    public static boolean AuthenticationCustom(Activity activity,String type){
        int authentication = SPUtils.getInstance().getInt(SpConfig.IS_VERIFIED);
        if (authentication != 2){
            new CustomDialog(activity)
                    .setTipLeftIcon(R.drawable.icon_tips,"抱歉，您还没有实名认证哦！",R.color.colorTv)
                    .setOk("去实名")
                    .setCallBack(new BaseDialog.CallBack() {
                        @Override
                        public void cancel() {

                        }

                        @Override
                        public void ok(String s) {
                            Bundle bundle = new Bundle();
                            bundle.putString(CustomConfig.UPLOAD_TYPE, CustomConfig.uploadTypeIdCard);
                            bundle.putString(CustomConfig.AUTHENTICATION_TYPE, type);
                            ActivityUtils.startActivity(bundle, AuthenticationActivity.class);
                        }
                    }).showDialog();
        }else {
            return true;
        }
        return false;
    }
}
