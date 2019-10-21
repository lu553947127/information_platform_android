package com.shuangduan.zcy.view.projectinfo;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.rongyun.view.IMAddFriendActivity;
import com.shuangduan.zcy.utils.image.ImageConfig;
import com.shuangduan.zcy.utils.image.ImageLoader;
import com.shuangduan.zcy.vm.UserInfoVm;
import com.shuangduan.zcy.weight.CircleImageView;

import butterknife.BindView;
import butterknife.OnClick;
import io.rong.imkit.RongIM;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view.projectinfo
 * @class describe  动态信息头像查看详情
 * @time 2019/8/23 10:09
 * @change
 * @chang time
 * @class describe
 */
public class LocusOwnerDetailActivity extends BaseActivity {
    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_user)
    CircleImageView ivUser;
    @BindView(R.id.tv_name)
    AppCompatTextView tvName;
    @BindView(R.id.tv_sex)
    AppCompatTextView tvSex;
    @BindView(R.id.tv_company)
    AppCompatTextView tvCompany;
    @BindView(R.id.tv_office)
    AppCompatTextView tvOffice;
    @BindView(R.id.tv_business_area)
    AppCompatTextView tvBusinessArea;
    @BindView(R.id.tv_business_exp)
    AppCompatTextView tvBusinessExp;
    @BindView(R.id.tv_production)
    TextView tvProduction;
    @BindView(R.id.tv_add_friend)
    TextView tvAddFriend;

    @BindView(R.id.iv_sgs)
    AppCompatImageView ivSgs;

    @BindView(R.id.cb_state)
    CheckBox cbState;

    private UserInfoVm userInfoVm;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_locus_owner_detail;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(getString(R.string.base_info));

        userInfoVm = ViewModelProviders.of(this).get(UserInfoVm.class);
        userInfoVm.uid = getIntent().getIntExtra(CustomConfig.UID, 0);
        userInfoVm.informationLiveData.observe(this, userInfoBean -> {
            tvName.setText(userInfoBean.getUsername());
            tvCompany.setText(userInfoBean.getCompany());
            tvOffice.setText(userInfoBean.getPosition());
            tvBusinessArea.setText(userInfoBean.getBusiness_city());
            tvBusinessExp.setText(userInfoBean.getExperience() > 0 ? getResources().getStringArray(R.array.experience_list)[userInfoBean.getExperience() - 1] + "年" : "");
            tvProduction.setText(userInfoBean.getManaging_products());
            switch (userInfoBean.getSex()) {
                case 0:
                    tvSex.setText(getString(R.string.select_not));
                    break;
                case 1:
                    tvSex.setText(getString(R.string.man));
                    break;
                case 2:
                    tvSex.setText(getString(R.string.woman));
                    break;
            }

            ivSgs.setVisibility(userInfoBean.getCardStatus() == 2 ? View.VISIBLE : View.INVISIBLE);
            cbState.setChecked(userInfoBean.getCardStatus() == 2);
            cbState.setText(userInfoBean.getCardStatus() == 2 ? R.string.real_name : R.string.un_real_name);

            ImageLoader.load(this, new ImageConfig.Builder()
                    .url(userInfoBean.getImage_source())
                    .placeholder(R.drawable.default_head)
                    .errorPic(R.drawable.default_head)
                    .imageView(ivUser)
                    .build());
            if (userInfoBean.getApply_status() != null && userInfoBean.getApply_status().equals("1")) {
                tvAddFriend.setText(getString(R.string.im_add_friend));
            } else {
                tvAddFriend.setText(getString(R.string.im_send_message));
            }
        });
        userInfoVm.pageStateLiveData.observe(this, s -> {
            showPageState(s);
        });
        userInfoVm.information();
    }

    @OnClick({R.id.iv_bar_back, R.id.tv_add_friend})
    void onClick(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.tv_add_friend:
                userInfoVm.informationLiveData.observe(this, userInfoBean -> {
                    if (userInfoBean.getApply_status() != null && userInfoBean.getApply_status().equals("1")) {
                        bundle.putInt(CustomConfig.FRIEND_DATA, 0);
                        bundle.putString("id", String.valueOf(userInfoBean.getId()));
                        bundle.putString("name", userInfoBean.getUsername());
                        bundle.putString("msg", userInfoBean.getCompany());
                        bundle.putString("image", userInfoBean.getImage());
                        ActivityUtils.startActivity(bundle, IMAddFriendActivity.class);
                    } else {
                        RongIM.getInstance().startPrivateChat(LocusOwnerDetailActivity.this, String.valueOf(userInfoBean.getId())
                                , userInfoBean.getUsername());
                    }
                });
                break;
        }
    }
}
