package com.shuangduan.zcy.view.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.widget.AppCompatTextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.base.BaseFragment;
import com.shuangduan.zcy.model.event.UserNameEvent;
import com.shuangduan.zcy.utils.BarUtils;
import com.shuangduan.zcy.view.activity.FeedbackActivity;
import com.shuangduan.zcy.view.activity.HelperActivity;
import com.shuangduan.zcy.view.activity.ReadHistoryActivity;
import com.shuangduan.zcy.view.activity.RecommendFriendsActivity;
import com.shuangduan.zcy.view.activity.SetActivity;
import com.shuangduan.zcy.view.activity.UserInfoActivity;
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
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            com.blankj.utilcode.util.BarUtils.setStatusBarLightMode(mActivity, false);
        }
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        com.blankj.utilcode.util.BarUtils.setStatusBarLightMode(mActivity, false);
        com.blankj.utilcode.util.BarUtils.setStatusBarColor(mActivity, getResources().getColor(android.R.color.transparent));
        BarUtils.setStatusBarColor(fakeStatusBar, R.drawable.shape_bg_mine);


        ivUser.setImageResource(R.drawable.default_head);
        tvUsername.setText("王某某");
        tvMember.setText("初级会员");
        tvNumOfPeople.setText(String.format(getString(R.string.format_num_of_people), 123));
        tvBalance.setText(String.format(getString(R.string.format_balance), 10000));
    }

    @Override
    protected void initDataFromService() {

    }

    @OnClick({R.id.tv_username, R.id.iv_user, R.id.tv_my_subscription, R.id.iv_order, R.id.tv_order, R.id.iv_income, R.id.tv_income,
            R.id.tv_read_history, R.id.tv_feedback, R.id.tv_recommend_friends, R.id.tv_my_collection, R.id.tv_set, R.id.tv_helper})
    void onClick(View view){
        switch (view.getId()) {
            case R.id.tv_username:
            case R.id.iv_user:
                ActivityUtils.startActivity(UserInfoActivity.class);
                break;
            case R.id.iv_order:
            case R.id.tv_order:
                break;
            case R.id.iv_income:
            case R.id.tv_income:
                break;
            case R.id.tv_my_subscription:
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
                break;
            case R.id.tv_set:
                ActivityUtils.startActivity(SetActivity.class);
                break;
            case R.id.tv_helper:
                ActivityUtils.startActivity(HelperActivity.class);
                break;
        }
    }

    @Subscribe()
    void updateUserName(UserNameEvent event){
        tvUsername.setText(event.username);
    }

}
