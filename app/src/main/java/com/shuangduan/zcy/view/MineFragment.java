package com.shuangduan.zcy.view;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.widget.AppCompatTextView;
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
import com.shuangduan.zcy.utils.BarUtils;
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
import com.shuangduan.zcy.view.mine.PwdPayActivity;
import com.shuangduan.zcy.view.mine.ReadHistoryActivity;
import com.shuangduan.zcy.view.mine.RecommendFriendsActivity;
import com.shuangduan.zcy.view.mine.SetActivity;
import com.shuangduan.zcy.view.mine.TransRecordActivity;
import com.shuangduan.zcy.view.mine.UserInfoActivity;
import com.shuangduan.zcy.view.recharge.RechargeActivity;
import com.shuangduan.zcy.vm.UserInfoVm;
import com.shuangduan.zcy.weight.CircleImageView;

import org.greenrobot.eventbus.Subscribe;

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
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.setStatusBarColor(fakeStatusBar, R.drawable.shape_bg_mine);
        userInfoVm = ViewModelProviders.of(mActivity).get(UserInfoVm.class);
        userInfoVm.getInfoLiveData.observe(this, userInfoBean -> {
            SPUtils.getInstance().put(SpConfig.USERNAME, userInfoBean.getUsername());
            tvUsername.setText(userInfoBean.getUsername());
            tvNumOfPeople.setText(String.format(getString(R.string.format_num_of_people), userInfoBean.getCount()));
            tvBalance.setText(String.format(getString(R.string.format_balance), userInfoBean.getCoin()));
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
                    showLoading();
                    break;
                default:
                    hideLoading();
                    break;
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

    @OnClick({R.id.tv_username, R.id.iv_user, R.id.tv_my_subscription, R.id.fl_order, R.id.fl_income, R.id.tv_balance, R.id.tv_my_demand, R.id.tv_transaction_record,
            R.id.tv_agreement_manage, R.id.tv_pwd_pay, R.id.tv_recharge,
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

}
