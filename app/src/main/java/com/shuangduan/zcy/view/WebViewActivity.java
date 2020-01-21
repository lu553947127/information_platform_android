package com.shuangduan.zcy.view;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.RequiresApi;
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

    @SuppressLint("ClickableViewAccessibility")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        switch (Objects.requireNonNull(getIntent().getStringExtra("register"))) {
            case "privacy":
                tvBarTitle.setText(getString(R.string.register_privacy));
                webViewUtils.loadUrl(RetrofitHelper.BASE_TEST_URL + Common.AGREEMENT_PRIVACY, 0);
                break;
            case "register":
                tvBarTitle.setText(getString(R.string.register_register));
                webViewUtils.loadUrl(RetrofitHelper.BASE_TEST_URL + Common.AGREEMENT_REGISTER, 0);
                break;
            case "warrant":
                tvBarTitle.setText("认购协议");
                webViewUtils.loadUrl(RetrofitHelper.BASE_TEST_URL + Common.AGREEMENT_WARRANT, 0);
                break;
            case "weather":
                tvBarTitle.setText("天气详情");
                webViewUtils.loadUrl(Common.WEATHER_H5, 1);

                webViewUtils.getWebView().setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                        return true;
                    }
                });

                break;
            case "friend":
                tvBarTitle.setText("邀请好友");
                webViewUtils.loadUrl(getIntent().getStringExtra("url"), 0);
                break;
        }
    }

    @OnClick(R.id.iv_bar_back)
    void onClick() {
        finish();
    }
}
