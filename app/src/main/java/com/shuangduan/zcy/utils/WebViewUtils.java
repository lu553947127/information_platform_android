package com.shuangduan.zcy.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.blankj.utilcode.util.ToastUtils;
import com.shuangduan.zcy.R;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.utils
 * @ClassName: WebViewUtils
 * @Description: h5页面加载框架
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/9/24 17:50
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/9/24 17:50
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class WebViewUtils extends LinearLayout {

    private Context mContext = null;
    private WebView mWebView = null;
    private View mBrowserControllerView = null;
    private ImageButton mGoBackBtn = null;
    private ImageButton mGoForwardBtn = null;
    private ImageButton mGoBrowserBtn = null;
    private ImageButton mRefreshBtn = null;

    private int mBarHeight = 5;
    private ProgressBar mProgressBar = null;

    private String mLoadUrl;

    public WebViewUtils(Context context) {
        super(context);
        init(context);
    }

    public WebViewUtils(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    @SuppressLint({"SetJavaScriptEnabled", "NewApi"})
    private void init(Context context) {
        mContext = context;
        setOrientation(VERTICAL);

        mProgressBar = (ProgressBar) LayoutInflater.from(context).inflate(R.layout.progress_horizontal, null);
        mProgressBar.setMax(100);
        mProgressBar.setProgress(0);
        addView(mProgressBar, LayoutParams.MATCH_PARENT, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, mBarHeight, getResources().getDisplayMetrics()));

        mWebView = new WebView(context);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        mWebView.getSettings().setDefaultTextEncodingName("UTF-8");
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        mWebView.getSettings().setBuiltInZoomControls(false);
        mWebView.getSettings().setSupportMultipleWindows(true);
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setSupportZoom(false);
        mWebView.getSettings().setPluginState(WebSettings.PluginState.ON);
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.getSettings().setLoadsImagesAutomatically(true);
        mWebView.getSettings().setJavaScriptEnabled(true);

        mWebView.getSettings().setAllowFileAccessFromFileURLs(true);

        LayoutParams lps = new LayoutParams(LayoutParams.MATCH_PARENT, 0, 1);
        addView(mWebView, lps);

        mWebView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100) {
                    mProgressBar.setVisibility(View.GONE);
                } else {
                    mProgressBar.setVisibility(View.VISIBLE);
                    mProgressBar.setProgress(newProgress);
                }
            }
        });

        mWebView.setWebViewClient(new WebViewClient() {

            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if(url == null) return false;
                try {
                    //微信 支付宝 邮件 电话 大众点评 知乎 头条 其他自定义的scheme
                    if(url.startsWith("weixin://")|| url.startsWith("alipays://")|| url.startsWith("mailto://")
                            || url.startsWith("tel://")|| url.startsWith("dianping://")|| url.startsWith("zhihu://")
                            || url.startsWith("snssdk143://")) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        mContext.startActivity(intent);
                        return true;
                    }
                } catch (Exception e) { //防止crash (如果手机上没有安装处理某个scheme开头的url的APP, 会导致crash)
                    return true;//没有安装该app时，返回true，表示拦截自定义链接，但不跳转，避免弹出上面的错误页面
                }
                //处理http和https开头的url
                view.loadUrl(url);
                return true;
            }
        });

        //监听webView滑动监听
        mWebView.setOnScrollChangeListener((v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            //由上往下
            if (scrollY>oldScrollY){
                hideBrowserController();
                //由下往上
            }else if (scrollY<oldScrollY){
                showBrowserController();
            }
        });

        mBrowserControllerView = LayoutInflater.from(context).inflate(R.layout.layout_web_view, null);
        mGoBackBtn = mBrowserControllerView.findViewById(R.id.browser_controller_back);
        mGoForwardBtn = mBrowserControllerView.findViewById(R.id.browser_controller_forward);
        mGoBrowserBtn = mBrowserControllerView.findViewById(R.id.browser_controller_go);
        mRefreshBtn = mBrowserControllerView.findViewById(R.id.browser_controller_refresh);

        mGoBackBtn.setOnClickListener(v -> {
            if (canGoBack()) {
                goBack();
            }
        });

        mGoForwardBtn.setOnClickListener(v -> {
            if (canGoForward()) {
                goForward();
            }
        });

        mRefreshBtn.setOnClickListener(v -> mWebView.loadUrl(mLoadUrl));

        mGoBrowserBtn.setOnClickListener(v -> {//用浏览器打开
            if (!TextUtils.isEmpty(mLoadUrl)) {
                if(mLoadUrl.startsWith("file:///")){
                    ToastUtils.showShort("此链接无法用浏览器打开");
                    return;
                }
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(mLoadUrl));
                mContext.startActivity(intent);
            }
        });

        addView(mBrowserControllerView, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
    }

    public void loadUrl(String url) {
        mWebView.loadUrl(url);
        mLoadUrl=url;
    }

    public boolean canGoBack() {
        return null != mWebView && mWebView.canGoBack();
    }

    public boolean canGoForward() {
        return null != mWebView && mWebView.canGoForward();
    }

    public void goBack() {
        if (null != mWebView) {
            mWebView.goBack();
        }
    }

    public void goForward() {
        if (null != mWebView) {
            mWebView.goForward();
        }
    }

    public WebView getWebView() {
        return mWebView;
    }

    public void hideBrowserController() {
        mBrowserControllerView.setVisibility(View.GONE);
    }

    public void showBrowserController() {
        mBrowserControllerView.setVisibility(View.VISIBLE);
    }

    public interface RefreshInterface{
        void refresh(String value);
    }

}
