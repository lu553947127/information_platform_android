package com.shuangduan.zcy.view.headlines;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.ViewModelProviders;

import com.blankj.utilcode.util.LogUtils;
import com.bumptech.glide.Glide;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.manage.ShareManage;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.utils.DensityUtil;
import com.shuangduan.zcy.utils.image.PictureEnlargeUtils;
import com.shuangduan.zcy.vm.HeadlinesVm;
import com.shuangduan.zcy.weight.AdaptationScrollView;
import com.shuangduan.zcy.weight.RichText;
import com.tencent.tauth.Tencent;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view.headlines
 * @class describe  头条详情
 * @time 2019/8/15 15:50
 * @change
 * @chang time
 * @class describe
 */
public class HeadlinesDetailActivity extends BaseActivity {

    @BindView(R.id.scroll)
    AdaptationScrollView scrollView;
    @BindView(R.id.rl_toolbar)
    RelativeLayout toolbar;
    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.tv_bar_title_new)
    AppCompatTextView tvBarTitleNew;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_content)
    RichText tvContent;
    private HeadlinesVm headlinesVm;
    //分享管理
    private ShareManage shareManage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        isTranslationBar = true;
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_headlines_detail;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        //滑动布局滑动监听
        scrollView.setOnScrollChangeListener(new AdaptationScrollView.OnScrollChangeListener() {
            private int mScrollY_2 = 0;
            private int lastScrollY = 0;
            private int h = DensityUtil.dp2px(300);
            //设置折叠标题背景颜色
            private int color = ContextCompat.getColor(HeadlinesDetailActivity.this, R.color.colorPrimary) & 0x00ffffff;

            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (lastScrollY < h) {
                    LogUtils.i(lastScrollY);
                    scrollY = Math.min(h, scrollY);
                    mScrollY_2 = scrollY > h ? h : scrollY;
                    toolbar.setAlpha(1f * mScrollY_2 / h);
                    toolbar.setBackgroundColor(((255 * mScrollY_2 / h) << 24) | color);
                } else {
                    toolbar.setVisibility(View.VISIBLE);
                }
                lastScrollY = scrollY;
            }
        });

        int id = getIntent().getIntExtra(CustomConfig.HEADLINES_ID, 0);
        //初始化分享功能
        shareManage = ShareManage.newInstance(getApplicationContext());
        shareManage.init(this, ShareManage.SHARE_HEADLINES_TYPE, id);

        tvTitle.setSelected(true);

        headlinesVm = ViewModelProviders.of(this).get(HeadlinesVm.class);
        headlinesVm.id = id;
        headlinesVm.detailLiveData.observe(this, headlinesDetailBean -> {
            tvTitle.setText(headlinesDetailBean.getTitle());
            tvBarTitleNew.setText(headlinesDetailBean.getTitle());
            tvTime.setText("发布时间：" + headlinesDetailBean.getCreate_time());
            tvContent.setGlide(Glide.with(this));
            tvContent.setHtml(headlinesDetailBean.getContent());
            tvContent.setOnRichClickListener(new RichText.OnRichClickListener() {
                @Override
                public void onAtUser(String at) {

                }

                @Override
                public void onTopicTag(String topic) {

                }

                @Override
                public void onNormalUrl(String url) {

                }

                @Override
                public void onCustomTag(String tag) {

                }

                @Override
                public void OnImg(String imgUrl) {
                    PictureEnlargeUtils.getPictureEnlarge(HeadlinesDetailActivity.this, imgUrl);
                }
            });
        });
        headlinesVm.pageStateLiveData.observe(this, s -> {
            switch (s) {
                case PageState.PAGE_LOADING:
                    showLoading();
                    break;
                default:
                    hideLoading();
                    break;
            }
        });
        headlinesVm.getDetail();
        //置顶
        scrollView.smoothScrollTo(0, 0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Tencent.onActivityResultData(requestCode, resultCode, data, shareManage.getQQListener());
    }

    @OnClick({R.id.iv_bar_back, R.id.iv_bar_back_new, R.id.iv_bar_right, R.id.iv_bar_right_new})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_bar_back:
            case R.id.iv_bar_back_new:
                finish();
                break;
            case R.id.iv_bar_right:
            case R.id.iv_bar_right_new:
                shareManage.initDialog(this, shareManage.getItem().getUrl(), shareManage.getItem().getTitle(),
                        shareManage.getItem().getDes(), shareManage.getItem().getImage(), shareManage.getBitmap(),"分享基建头条");
                break;
        }
    }
}
