package com.shuangduan.zcy.view;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.ViewModelProviders;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SPUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseFragment;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.model.event.AvatarEvent;
import com.shuangduan.zcy.model.event.RechargeSuccessEvent;
import com.shuangduan.zcy.model.event.UserNameEvent;
import com.shuangduan.zcy.utils.AnimationUtils;
import com.shuangduan.zcy.utils.DensityUtil;
import com.shuangduan.zcy.utils.image.ImageConfig;
import com.shuangduan.zcy.utils.image.ImageLoader;
import com.shuangduan.zcy.view.mine.AuthenticationActivity;
import com.shuangduan.zcy.view.mine.MineWalletActivity;
import com.shuangduan.zcy.view.mine.HelperActivity;
import com.shuangduan.zcy.view.mine.MaterialOrderActivity;
import com.shuangduan.zcy.view.mine.MineCollectionActivity;
import com.shuangduan.zcy.view.income.MineIncomeActivity;
import com.shuangduan.zcy.view.mine.MineDemandActivity;
import com.shuangduan.zcy.view.mine.MineOrderActivity;
import com.shuangduan.zcy.view.mine.OrderSubActivity;
import com.shuangduan.zcy.view.mine.RecommendFriendsActivity;
import com.shuangduan.zcy.view.mine.SetActivity;
import com.shuangduan.zcy.view.mine.UserInfoActivity;
import com.shuangduan.zcy.view.release.ReleaseListActivity;
import com.shuangduan.zcy.vm.UserInfoVm;
import com.shuangduan.zcy.weight.AdaptationScrollView;
import com.shuangduan.zcy.weight.CircleImageView;
import com.shuangduan.zcy.weight.HeadZoomScrollView;

import org.greenrobot.eventbus.Subscribe;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 徐玉 QQ:876885613
 * @name ZICAICloudPlatform
 * @class name：com.example.zicaicloudplatform.view.fragment
 * @class 我的
 * @time 2019/7/5 13:29
 * @change
 * @chang time
 * @class describe
 */
public class MineFragment extends BaseFragment {

    @BindView(R.id.scroll)
    HeadZoomScrollView scrollView;
    @BindView(R.id.rl_toolbar)
    RelativeLayout toolbar;
    @BindView(R.id.iv_user)
    CircleImageView ivUser;
    @BindView(R.id.tv_username)
    AppCompatTextView tvUsername;
    @BindView(R.id.tv_username_top)
    TextView tvUsernameTop;
    @BindView(R.id.iv_sgs)
    AppCompatImageView ivSgs;
    @BindView(R.id.tv_num_of_people)
    AppCompatTextView tvNumOfPeople;
    @BindView(R.id.tv_authentication)
    AppCompatTextView tvAuthentication;
    @BindView(R.id.iv_red_envelopes)
    AppCompatImageView ivRedEnvelopes;
    @BindView(R.id.ll_admin_manage)
    LinearLayout llAdminManage;
    private UserInfoVm userInfoVm;

    public static MineFragment newInstance() {
        Bundle args = new Bundle();
        MineFragment fragment = new MineFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState, View v) {

        userInfoVm = ViewModelProviders.of(mActivity).get(UserInfoVm.class);
        userInfoVm.getInfoLiveData.observe(this, userInfoBean -> {
            SPUtils.getInstance().put(SpConfig.USERNAME, userInfoBean.getUsername());
            tvUsername.setText(userInfoBean.getUsername());
            tvUsernameTop.setText(userInfoBean.getUsername());
            tvNumOfPeople.setText(String.format(getString(R.string.format_num_of_people), userInfoBean.getCount()));
            //头像标识显示状态
            ivSgs.setVisibility(userInfoBean.getCardStatus() == 2 ? View.VISIBLE : View.INVISIBLE);
            ImageLoader.load(mContext, new ImageConfig.Builder()
                    .url(userInfoBean.getImage_thumbnail())
                    .placeholder(R.drawable.default_head)
                    .errorPic(R.drawable.default_head)
                    .imageView(ivUser)
                    .build());
        });
        userInfoVm.pageStateLiveData.observe(this, s -> {
            switch (s) {
                case PageState.PAGE_LOADING:
//                    showLoading();
                    break;
                default:
                    hideLoading();
                    break;
            }
        });

        //滑动布局滑动监听
        scrollView.setOnScrollChangeListener(new AdaptationScrollView.OnScrollChangeListener() {
            private int mScrollY_2 = 0;
            private int lastScrollY = 0;
            private int h = DensityUtil.dp2px(65);
            //设置折叠标题背景颜色
            private int color = ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.colorPrimary) & 0x00ffffff;

            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (lastScrollY < h) {
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

        scrollView.smoothScrollTo(0,0);

        //判断是否已经实名
        if (SPUtils.getInstance().getInt(SpConfig.IS_VERIFIED)==2){
            tvAuthentication.setText("已实名认证");
        }else {
            tvAuthentication.setText("申请实名认证");
        }

        //红包开启抖动动画
        new Handler().postDelayed(() -> {
            ObjectAnimator animator = AnimationUtils.tada(ivRedEnvelopes);
            animator.setRepeatCount(ValueAnimator.INFINITE);
            animator.start();
        }, 500);
    }

    @Override
    protected void initDataFromService() {
        userInfoVm.userInfo();
    }

    @Override
    public boolean isUseEventBus() {
        return true;
    }

    @OnClick({R.id.iv_help,R.id.iv_set,R.id.cl_user, R.id.tv_authentication, R.id.tv_wallet, R.id.rl_recommend_friends,
            R.id.tv_income, R.id.tv_mine_subscription, R.id.tv_read_history, R.id.tv_my_project, R.id.tv_my_demand,
            R.id.tv_my_collection,R.id.tv_my_material})
    void onClick(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.iv_help://帮助
                ActivityUtils.startActivity(HelperActivity.class);
                break;
            case R.id.iv_set://设置
                ActivityUtils.startActivity(SetActivity.class);
                break;
            case R.id.cl_user://用户详情
                bundle.putInt(CustomConfig.UID, SPUtils.getInstance().getInt(SpConfig.USER_ID));
                ActivityUtils.startActivity(bundle, UserInfoActivity.class);
                break;
            case R.id.tv_authentication://实名认证
                bundle.putString(CustomConfig.UPLOAD_TYPE, CustomConfig.uploadTypeIdCard);
                bundle.putString(CustomConfig.AUTHENTICATION_TYPE, CustomConfig.ID_USER_INFO);
                ActivityUtils.startActivity(bundle, AuthenticationActivity.class);
                break;
            case R.id.tv_wallet://我的钱包
                ActivityUtils.startActivity(MineWalletActivity.class);
                break;
            case R.id.rl_recommend_friends://邀请好友
                ActivityUtils.startActivity(RecommendFriendsActivity.class);
                break;
            case R.id.tv_income://我的收益
                ActivityUtils.startActivity(MineIncomeActivity.class);
                break;
            case R.id.tv_mine_subscription://信息认购
                ActivityUtils.startActivity(OrderSubActivity.class);
                break;
            case R.id.tv_read_history://查看记录
                ActivityUtils.startActivity(MineOrderActivity.class);
                break;
            case R.id.tv_my_project://我的工程
                ActivityUtils.startActivity(ReleaseListActivity.class);
                break;
            case R.id.tv_my_demand://我的需求
                ActivityUtils.startActivity(MineDemandActivity.class);
                break;
            case R.id.tv_my_collection://我的收藏
                ActivityUtils.startActivity(MineCollectionActivity.class);
                break;
            case R.id.tv_my_material: //预定订单
                ActivityUtils.startActivity(MaterialOrderActivity.class);
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        userInfoVm.userInfo();
    }

    @Subscribe
    public void onEventUpdateUserName(UserNameEvent event) {
        tvUsername.setText(event.username);
        tvUsernameTop.setText(event.username);
    }

    @Subscribe
    public void onEventUpdateAvatar(AvatarEvent event) {
        ImageLoader.load(mContext, new ImageConfig.Builder()
                .url(event.getAvatar())
                .placeholder(R.drawable.default_head)
                .errorPic(R.drawable.default_head)
                .imageView(ivUser)
                .build());
    }

    @Subscribe
    public void onEventRechargeSuccess(RechargeSuccessEvent event) {
        userInfoVm.userInfo();
    }
}
