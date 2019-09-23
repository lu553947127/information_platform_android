package com.shuangduan.zcy.view;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

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
import com.shuangduan.zcy.model.event.CoinEvent;
import com.shuangduan.zcy.model.event.RechargeSuccessEvent;
import com.shuangduan.zcy.model.event.UserNameEvent;
import com.shuangduan.zcy.utils.BarUtils;
import com.shuangduan.zcy.utils.DensityUtil;
import com.shuangduan.zcy.utils.image.ImageConfig;
import com.shuangduan.zcy.utils.image.ImageLoader;
import com.shuangduan.zcy.view.mine.BalanceActivity;
import com.shuangduan.zcy.view.mine.FeedbackActivity;
import com.shuangduan.zcy.view.mine.HelperActivity;
import com.shuangduan.zcy.view.mine.MineCollectionActivity;
import com.shuangduan.zcy.view.income.MineIncomeActivity;
import com.shuangduan.zcy.view.mine.MineDemandActivity;
import com.shuangduan.zcy.view.mine.MineOrderActivity;
import com.shuangduan.zcy.view.mine.MineSubActivity;
import com.shuangduan.zcy.view.mine.OrderSubActivity;
import com.shuangduan.zcy.view.mine.PwdPayActivity;
import com.shuangduan.zcy.view.mine.ReadHistoryActivity;
import com.shuangduan.zcy.view.mine.RecommendFriendsActivity;
import com.shuangduan.zcy.view.mine.SetActivity;
import com.shuangduan.zcy.view.mine.TransRecordActivity;
import com.shuangduan.zcy.view.mine.UserInfoActivity;
import com.shuangduan.zcy.view.recharge.RechargeActivity;
import com.shuangduan.zcy.vm.UserInfoVm;
import com.shuangduan.zcy.weight.AdaptationScrollView;
import com.shuangduan.zcy.weight.CircleImageView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 宁文强 QQ:858777523
 * @name ZICAICloudPlatform
 * @class name：com.example.zicaicloudplatform.view.fragment
 * @class describe
 * @time 2019/7/5 13:29
 * @change
 * @chang time
 * @class describe
 */
public class MineFragment extends BaseFragment {

    @BindView(R.id.fake_status_bar)
    View fakeStatusBar;
    @BindView(R.id.iv_user)
    CircleImageView ivUser;
    @BindView(R.id.tv_username)
    AppCompatTextView tvUsername;
    @BindView(R.id.tv_member)
    AppCompatTextView tvMember;
    @BindView(R.id.tv_num_of_people)
    AppCompatTextView tvNumOfPeople;
    @BindView(R.id.tv_balance)
    AppCompatTextView tvBalance;
    @BindView(R.id.scroll)
    AdaptationScrollView scrollView;
    @BindView(R.id.rl_toolbar)
    RelativeLayout toolbar;
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
        BarUtils.setStatusBarColor(fakeStatusBar, R.drawable.shape_bg_mine);
        userInfoVm = ViewModelProviders.of(mActivity).get(UserInfoVm.class);
        userInfoVm.getInfoLiveData.observe(this, userInfoBean -> {
            SPUtils.getInstance().put(SpConfig.USERNAME, userInfoBean.getUsername());
            tvUsername.setText(userInfoBean.getUsername());
            tvNumOfPeople.setText(String.format(getString(R.string.format_num_of_people), userInfoBean.getCount()));
            tvBalance.setText(String.format(getString(R.string.format_balance), userInfoBean.getCoin()));
            EventBus.getDefault().post(new CoinEvent( userInfoBean.getCoin()));
            ImageLoader.load(mContext, new ImageConfig.Builder()
                    .url(userInfoBean.getImage_thumbnail())
                    .placeholder(R.drawable.default_head)
                    .errorPic(R.drawable.default_head)
                    .imageView(ivUser)
                    .build());
        });
        userInfoVm.pageStateLiveData.observe(this, s -> {
            switch (s){
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
            private int h = DensityUtil.dp2px(70);
            //设置折叠标题背景颜色
            private int color = ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.colorPrimary)&0x00ffffff;
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (lastScrollY < h) {
                    scrollY = Math.min(h, scrollY);
                    mScrollY_2 = scrollY > h ? h : scrollY;
                    toolbar.setAlpha(1f * mScrollY_2 / h);
                    toolbar.setBackgroundColor(((255 * mScrollY_2 / h) << 24) | color);
                }else {
                    toolbar.setVisibility(View.VISIBLE);
                }
                lastScrollY = scrollY;
            }
        });
    }

    @Override
    protected void initDataFromService() {
        userInfoVm.userInfo();
    }

    @Override
    public boolean isUseEventBus() {
        return true;
    }

    @OnClick({R.id.tv_username, R.id.iv_user, R.id.tv_my_subscription, R.id.fl_order, R.id.fl_income, R.id.tv_balance,
            R.id.tv_my_demand,R.id.tv_mine_subscription,R.id.tv_my_material,
            R.id.tv_transaction_record, R.id.tv_agreement_manage, R.id.tv_pwd_pay, R.id.tv_recharge,
            R.id.tv_read_history, R.id.tv_feedback, R.id.tv_recommend_friends, R.id.tv_my_collection, R.id.tv_set, R.id.tv_helper})
    void onClick(View view){
        switch (view.getId()) {
            case R.id.tv_username:
            case R.id.iv_user:
                Bundle bundle = new Bundle();
                bundle.putInt(CustomConfig.UID, SPUtils.getInstance().getInt(SpConfig.USER_ID));
                ActivityUtils.startActivity(bundle, UserInfoActivity.class);
                break;
            case R.id.tv_balance:
                ActivityUtils.startActivity(BalanceActivity.class);
                break;
            case R.id.tv_recharge:
                ActivityUtils.startActivity(RechargeActivity.class);
                break;
            case R.id.tv_my_demand:
                ActivityUtils.startActivity(MineDemandActivity.class);
                break;
            case R.id.tv_mine_subscription:
                ActivityUtils.startActivity(OrderSubActivity.class);
                break;
            case R.id.tv_my_material:
                break;
            case R.id.tv_transaction_record:
                ActivityUtils.startActivity(TransRecordActivity.class);
                break;
            case R.id.tv_agreement_manage:
                break;
            case R.id.tv_pwd_pay:
                ActivityUtils.startActivity(PwdPayActivity.class);
                break;
            case R.id.fl_order:
                ActivityUtils.startActivity(MineOrderActivity.class);
                break;
            case R.id.fl_income:
                ActivityUtils.startActivity(MineIncomeActivity.class);
                break;
            case R.id.tv_my_subscription:
                ActivityUtils.startActivity(MineSubActivity.class);
                break;
            case R.id.tv_read_history:
                ActivityUtils.startActivity(ReadHistoryActivity.class);
                break;
            case R.id.tv_feedback:
                ActivityUtils.startActivity(FeedbackActivity.class);
                break;
            case R.id.tv_recommend_friends:
                ActivityUtils.startActivity(RecommendFriendsActivity.class);
                break;
            case R.id.tv_my_collection:
                ActivityUtils.startActivity(MineCollectionActivity.class);
                break;
            case R.id.tv_set:
                ActivityUtils.startActivity(SetActivity.class);
                break;
            case R.id.tv_helper:
                ActivityUtils.startActivity(HelperActivity.class);
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        userInfoVm.userInfo();
    }

    @Subscribe
    public void onEventUpdateUserName(UserNameEvent event){
        tvUsername.setText(event.username);
    }

    @Subscribe
    public void onEventUpdateAvatar(AvatarEvent event){
        ImageLoader.load(mContext, new ImageConfig.Builder()
                .url(event.getAvatar())
                .placeholder(R.drawable.default_head)
                .errorPic(R.drawable.default_head)
                .imageView(ivUser)
                .build());
    }

    @Subscribe
    public void onEventRechargeSuccess(RechargeSuccessEvent event){
        userInfoVm.userInfo();
    }

    @Subscribe
    public void onEventCoin(CoinEvent event){
        tvBalance.setText(String.format(getString(R.string.format_balance), event.coin));
    }
}
