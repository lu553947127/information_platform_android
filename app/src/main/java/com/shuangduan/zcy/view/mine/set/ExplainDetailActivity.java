package com.shuangduan.zcy.view.mine.set;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;

import com.blankj.utilcode.util.BarUtils;
import com.bumptech.glide.Glide;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.utils.image.PictureEnlargeUtils;
import com.shuangduan.zcy.vm.ExplainVm;
import com.shuangduan.zcy.weight.RichText;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view.mine
 * @class 首页收益说明书详情
 * @time 2019/8/22 10:37
 * @change
 * @chang time
 * @class describe
 */
public class ExplainDetailActivity extends BaseActivity {
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
    private ExplainVm explainVm;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_explain_eetail;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(getString(R.string.explain));

        explainVm = getViewModel(ExplainVm.class);
        explainVm.id = getIntent().getIntExtra(CustomConfig.EXPLAIN_ID, 0);
        explainVm.detailLiveData.observe(this, explainDetailBean -> {
            tvTitle.setText(explainDetailBean.getTitle());
            tvTime.setText(explainDetailBean.getCreate_time());
            tvContent.setGlide(Glide.with(this));
            tvContent.setOnRichClickListener(new RichText.CustomRichClickListener(){
                @Override
                public void onNormalUrl(String url) {

                }

                @Override
                public void OnImg(String imgUrl) {
                    PictureEnlargeUtils.getPictureEnlarge(ExplainDetailActivity.this,imgUrl);
                }
            });
            tvContent.setHtml(explainDetailBean.getContent());
        });
        explainVm.pageStateLiveData.observe(this, s -> {
            switch (s){
                case PageState.PAGE_LOADING:
                    showLoading();
                    break;
                default:
                    hideLoading();
                    break;
            }
        });
        explainVm.getDetail();
    }

    @OnClick(R.id.iv_bar_back)
    void onClick(){finish();}
}
