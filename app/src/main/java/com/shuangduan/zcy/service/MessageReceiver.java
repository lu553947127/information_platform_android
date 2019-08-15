package com.shuangduan.zcy.service;

import android.content.Context;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.app.SpConfig;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.JPushMessage;
import cn.jpush.android.service.JPushMessageReceiver;

/**
 * <pre>
 *     author : 宁文强
 *     e-mail : ningwenqiang@lanhuiplay.com
 *     time   : 2018/04/20
 *     desc   : JPush新的tag/alias接口结果返回需要开发者配置一个自定的广播
 *     version: 1.0
 * </pre>
 */

public class MessageReceiver extends JPushMessageReceiver {

    @Override
    public void onAliasOperatorResult(Context context, JPushMessage jPushMessage) {
        super.onAliasOperatorResult(context, jPushMessage);
        String alias = jPushMessage.getAlias();
        int errorCode = jPushMessage.getErrorCode();
        LogUtils.i("alias:", alias, errorCode);
        if (errorCode == 0){
            SPUtils.getInstance().put(SpConfig.ALIAS_STATUS, 1);
        }
        if (errorCode == 6002){
            int userId = SPUtils.getInstance().getInt(SpConfig.USER_ID, 0);
            JPushInterface.setAlias(context, userId, String.valueOf(userId));
        }
    }

}
