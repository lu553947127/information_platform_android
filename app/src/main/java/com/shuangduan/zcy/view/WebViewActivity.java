package com.shuangduan.zcy.view;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;

import com.blankj.utilcode.util.BarUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.app.Common;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.model.api.retrofit.RetrofitHelper;
import com.shuangduan.zcy.utils.WebViewUtils;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.view
 * @ClassName: WebViewActivity
 * @Description: h5加载页
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/9/24 18:35
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/9/24 18:35
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
@SuppressLint("Registered")
public class WebViewActivity extends BaseActivity {

    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.web_view)
    WebViewUtils webViewUtils;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_web_view;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        if (Objects.requireNonNull(getIntent().getStringExtra("register")).equals("privacy")){
            tvBarTitle.setText(getString(R.string.register_privacy));
            webViewUtils.loadUrl(RetrofitHelper.BASE_TEST_URL+ Common.AGREEMENT_PRIVACY);
        }else if (Objects.requireNonNull(getIntent().getStringExtra("register")).equals("register")){
            tvBarTitle.setText(getString(R.string.register_register));
            webViewUtils.loadUrl(RetrofitHelper.BASE_TEST_URL+ Common.AGREEMENT_REGISTER);
        }else if (Objects.requireNonNull(getIntent().getStringExtra("register")).equals("warrant")){
            tvBarTitle.setText("认购协议");
            webViewUtils.loadUrl(RetrofitHelper.BASE_TEST_URL+ Common.AGREEMENT_WARRANT);
        }
    }

    @OnClick(R.id.iv_bar_back)
    void onClick(){finish();}
}
