package com.shuangduan.zcy.view.mine;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.blankj.utilcode.util.BarUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.dialog.ShareDialog;
import com.shuangduan.zcy.listener.BaseUiListener;
import com.shuangduan.zcy.utils.ShareUtils;
import com.shuangduan.zcy.vm.ShareVm;
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

    private Tencent mTencent;
    private BaseUiListener qqListener;
    private ShareVm shareVm;

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
        initShare();
    }

    private void initShare(){
        // 其中APP_ID是分配给第三方应用的appid，类型为String。
        mTencent = Tencent.createInstance(ShareUtils.app_id_qq, getApplicationContext());

        shareVm = ViewModelProviders.of(this).get(ShareVm.class);
        shareVm.shareLiveData.observe(this, shareBean -> {
            final String url = getString(R.string.share_url);
            final String title = "紫菜云分享";
            final String des = "租赁共享高端平台";

            qqListener = new BaseUiListener();
            addDialog(new ShareDialog(this)
                    .setOnShareListener(new ShareDialog.OnShareListener() {
                        @Override
                        public void qq() {
                            ShareUtils.shareQQ(RecommendFriendsActivity.this, mTencent, qqListener, url, title, des, "http://information-api.oss-cn-qingdao.aliyuncs.com/5adb0e33d685223bfe79a51fee17431f.png");
                        }

                        @Override
                        public void qqStone() {
                            ShareUtils.shareQQStone(RecommendFriendsActivity.this, mTencent, qqListener, url, title, des, "http://information-api.oss-cn-qingdao.aliyuncs.com/5adb0e33d685223bfe79a51fee17431f.png");
                        }

                        @Override
                        public void weChat() {
                            ShareUtils.shareWeChat(RecommendFriendsActivity.this, ShareUtils.FRIEND, url, title, des, shareBean.getBitmap());
                        }

                        @Override
                        public void friendCircle() {
                            ShareUtils.shareWeChat(RecommendFriendsActivity.this, ShareUtils.FRIEND_CIRCLE, url, title, des, shareBean.getBitmap());
                        }
                    })
                    .showDialog());
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
                shareVm.getBitmap("http://information-api.oss-cn-qingdao.aliyuncs.com/5adb0e33d685223bfe79a51fee17431f.png", BitmapFactory.decodeResource(getResources(), R.drawable.default_pic));
                break;
        }
    }
}
