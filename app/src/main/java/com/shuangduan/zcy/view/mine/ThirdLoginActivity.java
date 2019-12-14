package com.shuangduan.zcy.view.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.utils.image.ImageConfig;
import com.shuangduan.zcy.utils.image.ImageLoader;
import com.shuangduan.zcy.weight.CircleImageView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.view.mine
 * @ClassName: ThirdLoginActivity
 * @Description: 第三方登录账号绑定
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/12/14 10:21
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/12/14 10:21
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class ThirdLoginActivity extends BaseActivity {
    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_wechat_circle)
    CircleImageView ivWechatCircle;
    @BindView(R.id.tv_wechat_name)
    TextView tvWechatName;
    @BindView(R.id.tv_status)
    TextView tvStatus;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_third_login;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText("微信登录账号绑定");
        ImageLoader.load(this, new ImageConfig.Builder()
                .url(SPUtils.getInstance().getString(SpConfig.HEAD_IMG_URL))
                .placeholder(R.drawable.icon_wechat)
                .errorPic(R.drawable.icon_wechat)
                .imageView(ivWechatCircle)
                .build());
        tvWechatName.setText(StringUtils.isTrimEmpty(SPUtils.getInstance().getString(SpConfig.NICKNAME)) ? "微信昵称" : SPUtils.getInstance().getString(SpConfig.NICKNAME));
        tvStatus.setText(StringUtils.isTrimEmpty(SPUtils.getInstance().getString(SpConfig.NICKNAME)) ? "未绑定": "解绑");
    }

    @OnClick({R.id.iv_bar_back, R.id.rl_wechat})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.rl_wechat://微信绑定/解绑
                break;
        }
    }
}
