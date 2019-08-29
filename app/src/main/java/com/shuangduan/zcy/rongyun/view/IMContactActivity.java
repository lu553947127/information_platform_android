package com.shuangduan.zcy.rongyun.view;

import android.os.Bundle;

import com.blankj.utilcode.util.ActivityUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.base.BaseActivity;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.rongyun.view
 * @class describe  联系人列表
 * @time 2019/8/29 17:51
 * @change
 * @chang time
 * @class describe
 */
public class IMContactActivity extends BaseActivity {
    @Override
    protected int initLayoutRes() {
        return R.layout.activity_im_contact;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        findViewById(R.id.tv_new_friends).setOnClickListener(l -> ActivityUtils.startActivity(NewFriendsActivity.class));
    }
}
