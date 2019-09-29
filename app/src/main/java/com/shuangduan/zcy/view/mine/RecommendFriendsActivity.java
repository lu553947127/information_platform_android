package com.shuangduan.zcy.view.mine;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.app.Common;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.dialog.ShareDialog;
import com.shuangduan.zcy.listener.BaseUiListener;
import com.shuangduan.zcy.manage.ShareManage;
import com.shuangduan.zcy.model.api.retrofit.RetrofitHelper;
import com.shuangduan.zcy.model.bean.ShareBean;
import com.shuangduan.zcy.model.event.CompanyEvent;
import com.shuangduan.zcy.model.event.ShareEvent;
import com.shuangduan.zcy.utils.LoginUtils;
import com.shuangduan.zcy.utils.ShareUtils;
import com.shuangduan.zcy.view.projectinfo.ProjectDetailActivity;
import com.tencent.tauth.Tencent;

import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zicaicloudplatform.view.activity
 * @class describe 推荐好友
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
    @BindView(R.id.tv_url)
    TextView tvUrl;

    //分享控制器
    private ShareManage shareManage;


    @Override
    protected int initLayoutRes() {
        return R.layout.activity_recomment_friends;
    }

    @Override
    public boolean isUseEventBus() {
        return true;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(getString(R.string.recommend_friends));


        //初始化分享功能
        shareManage = ShareManage.newInstance(getApplicationContext());
        shareManage.init(this, ShareManage.SHARE_RECOMMEND_FRIENDS_TYPE, getIntent().getIntExtra(CustomConfig.PROJECT_ID, 0));
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Tencent.onActivityResultData(requestCode, resultCode, data, shareManage.getQQListener());
    }

    @OnClick({R.id.iv_bar_back, R.id.tv_share})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.tv_share:
                shareManage.showDialog();
                break;
        }
    }

    @Subscribe
    public void onEventUpdateUrl(ShareEvent event) {
        tvUrl.setText(event.url);
    }

}
