package com.shuangduan.zcy.view.mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;

import com.blankj.utilcode.util.BarUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.manage.ShareManage;
import com.tencent.tauth.Tencent;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zicaicloudplatform.view.activity
 * @class 邀请好友
 * @time 2019/7/10 14:48
 * @change
 * @chang time
 * @class describe
 */
public class RecommendFriendsActivity extends BaseActivity {
    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    //分享控制器
    private ShareManage shareManage;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_recomment_friends;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(getString(R.string.invite_friends));
        //初始化分享功能
        shareManage = ShareManage.newInstance(getApplicationContext());
        shareManage.init(this, ShareManage.SHARE_RECOMMEND_FRIENDS_TYPE, getIntent().getIntExtra(CustomConfig.PROJECT_ID, 0));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Tencent.onActivityResultData(requestCode, resultCode, data, shareManage.getQQListener());
    }

    @OnClick({R.id.iv_bar_back, R.id.iv_share})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.iv_share:
                shareManage.initDialog(this, shareManage.getItem().getUrl(), shareManage.getItem().getTitle(),
                        shareManage.getItem().getDes(), shareManage.getItem().getImage(), shareManage.getBitmap(),"邀请好友");
                break;
        }
    }
}
