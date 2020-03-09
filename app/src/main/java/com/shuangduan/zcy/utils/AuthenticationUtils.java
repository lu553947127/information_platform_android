package com.shuangduan.zcy.utils;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SPUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.view.mine.user.AuthenticationActivity;
import com.zcy.framelibrary.dialog.AlertDialog;
import com.zcy.framelibrary.dialog.listener.OnClickListenerWrapper;

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
     *
     * @return
     */
    public static boolean Authentication(String type) {
        int authentication = SPUtils.getInstance().getInt(SpConfig.IS_VERIFIED);
        if (authentication != 2) {
            //去认证
            Bundle bundle = new Bundle();
            bundle.putString(CustomConfig.UPLOAD_TYPE, CustomConfig.uploadTypeIdCard);
            bundle.putString(CustomConfig.AUTHENTICATION_TYPE, type);
            ActivityUtils.startActivity(bundle, AuthenticationActivity.class);
        } else {
            return true;
        }
        return false;
    }

    ///判断没有认证弹窗
    public static boolean AuthenticationCustom(BaseActivity activity, String type) {
        int authentication = SPUtils.getInstance().getInt(SpConfig.IS_VERIFIED);
        if (authentication != 2) {

            AlertDialog dialog = new AlertDialog.Builder(activity)
                    .setView(R.layout.dialog_custom)
                    .setText(R.id.tv_tip, "抱歉，您还没有实名认证哦！", R.color.colorTv)
                    .setText(R.id.tv_positive, "去实名")
                    .setOnClickListener(R.id.tv_negative, null)
                    .setOnClickListener(R.id.tv_positive, new OnClickListenerWrapper() {
                        @Override
                        public void onClickCall(View v) {

                            Bundle bundle = new Bundle();
                            bundle.putString(CustomConfig.UPLOAD_TYPE, CustomConfig.uploadTypeIdCard);
                            bundle.putString(CustomConfig.AUTHENTICATION_TYPE, type);
                            ActivityUtils.startActivity(bundle, AuthenticationActivity.class);
                        }
                    })
                    .show();

            TextView tipView = dialog.getView(R.id.tv_tip);
            Drawable drawable = activity.getResources().getDrawable(R.drawable.icon_tips);
            // 这一步必须要做,否则不会显示.
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            tipView.setCompoundDrawables(drawable, null, null, null);
            tipView.setCompoundDrawablePadding(DensityUtil.dp2px(5));
            activity.addDialog(dialog);
        } else {
            return true;
        }
        return false;
    }
}
