package com.shuangduan.zcy.view.recruit;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.blankj.utilcode.util.BarUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.ViewPagerAdapter;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.dialog.BaseDialog;
import com.shuangduan.zcy.dialog.CustomDialog;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.vm.RecruitDetailVm;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.http.Body;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view.recruit
 * @class describe  招采信息详情
 * @time 2019/7/12 9:24
 * @change
 * @chang time
 * @class describe
 */
public class RecruitDetailActivity extends BaseActivity {
    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.iv_bar_right)
    AppCompatImageView ivBarRight;
    @BindView(R.id.tv_bar_right)
    TextView tvBarRight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_release_time)
    TextView tvReleaseTime;
    @BindView(R.id.iv_collect)
    ImageView ivCollect;
    @BindView(R.id.tv_collect)
    TextView tvCollect;
    @BindView(R.id.ll_read_detail)
    LinearLayout llReadDetail;
//    @BindView(R.id.tv_intro_title)
//    TextView tvIntroTitle;
//    @BindView(R.id.tv_intro_time)
//    TextView tvIntroTime;
    @BindView(R.id.tv_intro_content)
    TextView tvIntroContent;
    private int id;
    private RecruitDetailVm recruitDetailVm;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_recruit_detail;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(getString(R.string.message_detail));
        tvBarRight.setVisibility(View.GONE);
        ivBarRight.setImageResource(R.drawable.icon_share);
        id = getIntent().getIntExtra(CustomConfig.RECRUIT_ID, 0);

        recruitDetailVm = ViewModelProviders.of(this).get(RecruitDetailVm.class);
        recruitDetailVm.detailLiveData.observe(this, recruitDetailBean -> {
            tvTitle.setText(recruitDetailBean.getTitle());
            tvReleaseTime.setText(String.format(getString(R.string.format_release_time), recruitDetailBean.getStart_time()));
            tvIntroContent.setText(recruitDetailBean.getContent());
            recruitDetailVm.collectionLiveData.postValue(recruitDetailBean.getCollection());
            llReadDetail.setVisibility(recruitDetailBean.getIs_pay() == 1?View.GONE:View.VISIBLE);
        });
        recruitDetailVm.collectionLiveData.observe(this, i -> {
            tvCollect.setText(getString(i == 1? R.string.collected: R.string.collection));
            ivCollect.setImageResource(i == 1? R.drawable.icon_collected: R.drawable.icon_collect);
        });
        recruitDetailVm.collectLiveData.observe(this, o -> recruitDetailVm.collectionLiveData.postValue(1));
        recruitDetailVm.collectCancelLiveData.observe(this, o -> recruitDetailVm.collectionLiveData.postValue(0));
        recruitDetailVm.pageStateLiveData.observe(this, s -> {
            switch (s){
                case PageState.PAGE_LOADING:
                    showLoading();
                    break;
                default:
                    hideLoading();
                    break;
            }
        });
        recruitDetailVm.getDetail(id);
    }

    @OnClick({R.id.iv_bar_back, R.id.iv_bar_right, R.id.ll_collect, R.id.ll_read_detail})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.iv_bar_right:

                break;
            case R.id.ll_collect:
                recruitDetailVm.collect(id);
                break;
            case R.id.ll_read_detail:
                new CustomDialog(this)
                        .setTip("查看此消息请支付" + recruitDetailVm.detailLiveData.getValue().getPrice() +"元")
                        .setCallBack(new BaseDialog.CallBack() {
                            @Override
                            public void cancel() {

                            }

                            @Override
                            public void ok(String s) {

                            }
                        })
                        .showDialog();
                break;
        }
    }

}
