package com.shuangduan.zcy.view.mine;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.app.Common;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.dialog.ShareDialog;
import com.shuangduan.zcy.listener.BaseUiListener;
import com.shuangduan.zcy.model.api.retrofit.RetrofitHelper;
import com.shuangduan.zcy.model.bean.ShareBean;
import com.shuangduan.zcy.utils.LoginUtils;
import com.shuangduan.zcy.utils.ShareUtils;
import com.tencent.tauth.Tencent;

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
    private Tencent mTencent;
    private BaseUiListener qqListener;

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
        tvBarTitle.setText(getString(R.string.recommend_friends));
        // 其中APP_ID是分配给第三方应用的appid，类型为String。
        mTencent = Tencent.createInstance(ShareUtils.app_id_qq, getApplicationContext());
        qqListener = new BaseUiListener();
    }

    //分享
    private void getShare() {

        OkGo.<String>post(RetrofitHelper.BASE_TEST_URL+ Common.USER_INFO_SHARE)
                .tag(this)
                .headers("token", SPUtils.getInstance().getString(SpConfig.TOKEN))//请求头
                .params("user_id", SPUtils.getInstance().getInt(SpConfig.USER_ID))//用户编号
                .execute(new com.lzy.okgo.callback.StringCallback() {//返回值

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        LogUtils.json(response.body());
                    }

                    @Override
                    public void onSuccess(com.lzy.okgo.model.Response<String> response) {
                        LogUtils.json(response.body());
                        try {
                            ShareBean bean=new Gson().fromJson(response.body(), ShareBean.class);
                            if (bean.getCode().equals("200")){
                                tvUrl.setText(bean.getData().getUrl());
                                Bitmap bitmap = BitmapFactory.decodeFile(bean.getData().getImage());
//                                Bitmap bitmap = ShareUtils.getBitMBitmap(bean.getData().getImage());
                                addDialog(new ShareDialog(RecommendFriendsActivity.this)
                                        .setOnShareListener(new ShareDialog.OnShareListener() {
                                            @Override
                                            public void qq() {
                                                ShareUtils.shareQQ(RecommendFriendsActivity.this, mTencent, qqListener
                                                        , bean.getData().getUrl(), bean.getData().getTitle(), bean.getData().getDes(), bean.getData().getImage());
                                            }

                                            @Override
                                            public void qqStone() {
                                                ShareUtils.shareQQStone(RecommendFriendsActivity.this, mTencent, qqListener
                                                        , bean.getData().getUrl(), bean.getData().getTitle(), bean.getData().getDes(), bean.getData().getImage());
                                            }

                                            @Override
                                            public void weChat() {
                                                ShareUtils.shareWeChat(RecommendFriendsActivity.this, ShareUtils.FRIEND
                                                        , bean.getData().getUrl(), bean.getData().getTitle(), bean.getData().getDes(), bitmap);
                                            }

                                            @Override
                                            public void friendCircle() {
                                                ShareUtils.shareWeChat(RecommendFriendsActivity.this, ShareUtils.FRIEND_CIRCLE
                                                        , bean.getData().getUrl(), bean.getData().getTitle(), bean.getData().getDes(), bitmap);
                                            }
                                        })
                                        .showDialog());
                            }else if (bean.getCode().equals("-1")){
                                ToastUtils.showShort(bean.getMsg());
                                LoginUtils.getExitLogin(RecommendFriendsActivity.this);
                            }else {
                                ToastUtils.showShort(bean.getMsg());
                            }
                        }catch (JsonSyntaxException | IllegalStateException ignored){
                            ToastUtils.showShort(getString(R.string.request_error));
                        }
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Tencent.onActivityResultData(requestCode, resultCode, data, qqListener);
    }

    @OnClick({R.id.iv_bar_back, R.id.tv_share})
    void onClick(View view){
        switch (view.getId()) {
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.tv_share:
                getShare();
                break;
        }
    }
}
