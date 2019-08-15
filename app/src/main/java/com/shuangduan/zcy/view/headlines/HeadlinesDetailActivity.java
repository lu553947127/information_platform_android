package com.shuangduan.zcy.view.headlines;

import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityOptionsCompat;
import androidx.lifecycle.ViewModelProviders;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.bumptech.glide.Glide;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.view.PhotoViewActivity;
import com.shuangduan.zcy.vm.HeadlinesVm;
import com.shuangduan.zcy.weight.RichText;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view.headlines
 * @class describe  头条详情
 * @time 2019/8/15 15:50
 * @change
 * @chang time
 * @class describe
 */
public class HeadlinesDetailActivity extends BaseActivity {
    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_content)
    RichText tvContent;
    private HeadlinesVm headlinesVm;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_headlines_detail;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(getString(R.string.head_detail));
        int id = getIntent().getIntExtra(CustomConfig.HEADLINES_ID, 0);

        headlinesVm = ViewModelProviders.of(this).get(HeadlinesVm.class);
        headlinesVm.id = id;
        headlinesVm.detailLiveData.observe(this, headlinesDetailBean -> {
            tvTitle.setText(headlinesDetailBean.getTitle());
            tvTime.setText(headlinesDetailBean.getCreate_time());
            tvContent.setGlide(Glide.with(this));
            tvContent.setOnRichClickListener(new RichText.CustomRichClickListener(){
                @Override
                public void onNormalUrl(String url) {
//                    Bundle bundle = new Bundle();
//                    bundle.putString(CustomConfig.TITLE, getString(R.string.app_name));
//                    bundle.putString(CustomConfig.URL, url);
//                    ActivityUtils.startActivity(bundle, WebActivity.class);
                }

                @Override
                public void OnImg(String imgUrl) {
                    startPhotoActivity(imgUrl);
                }
            });
            tvContent.setHtml(headlinesDetailBean.getContent());
        });
        headlinesVm.pageStateLiveData.observe(this, s -> {
            switch (s){
                case PageState.PAGE_LOADING:
                    showLoading();
                    break;
                default:
                    hideLoading();
                    break;
            }
        });
        headlinesVm.getDetail();
    }

    private void startPhotoActivity(String url){
        ArrayList<String> list = new ArrayList<>();
        Bundle bundle = new Bundle();
        bundle.putInt("position", 0);
        bundle.putStringArrayList(CustomConfig.PHOTO_VIEW_URL_LIST, list);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //共享shareElement这个View
            ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(this, tvContent, "shareElement");
            ActivityUtils.startActivity(bundle, PhotoViewActivity.class, activityOptionsCompat.toBundle());
        } else {
            ActivityUtils.startActivity(bundle, PhotoViewActivity.class);
        }
    }

    @OnClick(R.id.iv_bar_back)
    void onClick(){finish();}
}
