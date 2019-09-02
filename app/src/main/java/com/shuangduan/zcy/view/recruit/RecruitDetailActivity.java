package com.shuangduan.zcy.view.recruit;

import android.content.Intent;
import android.graphics.BitmapFactory;
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

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.ViewPagerAdapter;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.dialog.BaseDialog;
import com.shuangduan.zcy.dialog.CustomDialog;
import com.shuangduan.zcy.dialog.PayDialog;
import com.shuangduan.zcy.dialog.ShareDialog;
import com.shuangduan.zcy.listener.BaseUiListener;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.utils.ShareUtils;
import com.shuangduan.zcy.view.mine.SetPwdPayActivity;
import com.shuangduan.zcy.view.recharge.RechargeActivity;
import com.shuangduan.zcy.vm.CoinPayVm;
import com.shuangduan.zcy.vm.RecruitDetailVm;
import com.shuangduan.zcy.vm.ShareVm;
import com.shuangduan.zcy.vm.UpdatePwdPayVm;
import com.shuangduan.zcy.weight.RichText;
import com.tencent.tauth.Tencent;

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
    @BindView(R.id.tv_intro_title)
    TextView tvIntroTitle;
    @BindView(R.id.tv_intro_time)
    TextView tvIntroTime;
    @BindView(R.id.tv_intro_content)
    RichText tvIntroContent;
    private int id;
    private RecruitDetailVm recruitDetailVm;
    private UpdatePwdPayVm updatePwdPayVm;
    private CoinPayVm coinPayVm;
    private Tencent mTencent;
    private BaseUiListener qqListener;
    private ShareVm shareVm;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_recruit_detail;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(getString(R.string.message_detail));
        tvBarRight.setVisibility(View.GONE);
        ivBarRight.setImageResource(R.drawable.icon_share);
        id = getIntent().getIntExtra(CustomConfig.RECRUIT_ID, 0);

        recruitDetailVm = ViewModelProviders.of(this).get(RecruitDetailVm.class);
        recruitDetailVm.id = id;
        recruitDetailVm.detailLiveData.observe(this, recruitDetailBean -> {
            tvTitle.setText(recruitDetailBean.getTitle());
            tvIntroTitle.setText(recruitDetailBean.getTitle());
            tvReleaseTime.setText(String.format(getString(R.string.format_release_time), recruitDetailBean.getStart_time()));
            tvIntroTime.setText(String.format(getString(R.string.format_release_time), recruitDetailBean.getStart_time()));
            tvIntroContent.setGlide(Glide.with(this));
            tvIntroContent.setHtml(recruitDetailBean.getContent());
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
        recruitDetailVm.getDetail();

        //支付密码状态查询
        updatePwdPayVm = ViewModelProviders.of(this).get(UpdatePwdPayVm.class);
        updatePwdPayVm.stateLiveData.observe(this, pwdPayStateBean -> {
            int status = pwdPayStateBean.getStatus();
            SPUtils.getInstance().put(SpConfig.PWD_PAY_STATUS, status);
            if (status == 1){
                goToPay();
            }else {
                ActivityUtils.startActivity(SetPwdPayActivity.class);
            }
        });
        updatePwdPayVm.pageStateLiveData.observe(this, s -> {
            switch (s){
                case PageState.PAGE_LOADING:
                    showLoading();
                    break;
                default:
                    hideLoading();
                    break;
            }
        });


        coinPayVm = ViewModelProviders.of(this).get(CoinPayVm.class);
        coinPayVm.recruitId = id;
        coinPayVm.recruitPayLiveData.observe(this, coinPayResultBean -> {
            if (coinPayResultBean.getPay_status() == 1){
                ToastUtils.showShort(getString(R.string.buy_success));
                recruitDetailVm.getDetail();
            }else {
                //余额不足
                addDialog(new CustomDialog(this)
                        .setIcon(R.drawable.icon_error)
                        .setTip("余额不足")
                        .setCallBack(new BaseDialog.CallBack() {
                            @Override
                            public void cancel() {

                            }

                            @Override
                            public void ok(String s) {
                                ActivityUtils.startActivity(RechargeActivity.class);
                            }
                        })
                        .showDialog());
            }
        });
        coinPayVm.pageStateLiveData.observe(this, s -> {
            switch (s){
                case PageState.PAGE_LOADING:
                    showLoading();
                    break;
                default:
                    hideLoading();
                    break;
            }
        });

        initShare();
    }

    @OnClick({R.id.iv_bar_back, R.id.iv_bar_right, R.id.ll_collect, R.id.ll_read_detail})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.iv_bar_right:
                shareVm.getBitmap("http:\\/\\/information-api.oss-cn-qingdao.aliyuncs.com\\/5adb0e33d685223bfe79a51fee17431f.png", BitmapFactory.decodeResource(getResources(), R.drawable.default_pic));
                break;
            case R.id.ll_collect:
                recruitDetailVm.collect();
                break;
            case R.id.ll_read_detail:
                addDialog(new CustomDialog(this)
                        .setTip(String.format(getString(R.string.format_pay_price), recruitDetailVm.detailLiveData.getValue().getPrice()))
                        .setCallBack(new BaseDialog.CallBack() {
                            @Override
                            public void cancel() {

                            }

                            @Override
                            public void ok(String s) {
                                int status = SPUtils.getInstance().getInt(SpConfig.PWD_PAY_STATUS, 0);
                                if (status == 1){
                                    goToPay();
                                }else {
                                    //查询是否设置支付密码
                                    updatePwdPayVm.payPwdState();
                                }
                            }
                        })
                        .showDialog());
                break;
        }
    }

    /**
     * 去支付
     */
    private void goToPay(){
        addDialog(new PayDialog(this)
                .setSingleCallBack((item, position) -> {
                    coinPayVm.payRecruit(item);
                })
                .showDialog());
    }

    /**
     * 分享
     */
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
                            ShareUtils.shareQQ(RecruitDetailActivity.this, mTencent, qqListener, url, title, des, "http://information-api.oss-cn-qingdao.aliyuncs.com/5adb0e33d685223bfe79a51fee17431f.png");
                        }

                        @Override
                        public void qqStone() {
                            ShareUtils.shareQQStone(RecruitDetailActivity.this, mTencent, qqListener, url, title, des, "http://information-api.oss-cn-qingdao.aliyuncs.com/5adb0e33d685223bfe79a51fee17431f.png");
                        }

                        @Override
                        public void weChat() {
                            ShareUtils.shareWeChat(RecruitDetailActivity.this, ShareUtils.FRIEND, url, title, des, shareBean.getBitmap());
                        }

                        @Override
                        public void friendCircle() {
                            ShareUtils.shareWeChat(RecruitDetailActivity.this, ShareUtils.FRIEND_CIRCLE, url, title, des, shareBean.getBitmap());
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

}
