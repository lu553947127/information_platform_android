package com.shuangduan.zcy.view.income;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.StringUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.rongyun.view.IMAddFriendActivity;
import com.shuangduan.zcy.utils.image.ImageConfig;
import com.shuangduan.zcy.utils.image.ImageLoader;
import com.shuangduan.zcy.vm.IncomePeopleVm;
import com.shuangduan.zcy.weight.CircleImageView;

import butterknife.BindView;
import butterknife.OnClick;
import io.rong.imkit.RongIM;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view.people
 * @class describe  邀请好友的个人详情
 * @time 2019/8/14 10:48
 * @change
 * @chang time
 * @class describe
 */
public class PeopleInfoActivity extends BaseActivity {
    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_user)
    CircleImageView ivUser;
    @BindView(R.id.tv_name)
    AppCompatTextView tvName;
    @BindView(R.id.fl_mobile)
    FrameLayout flMobile;
    @BindView(R.id.tv_mobile)
    AppCompatTextView tvMobile;
    @BindView(R.id.tv_company)
    AppCompatTextView tvCompany;
    @BindView(R.id.tv_office)
    AppCompatTextView tvOffice;
    @BindView(R.id.tv_business_area)
    AppCompatTextView tvBusinessArea;
    @BindView(R.id.tv_business_exp)
    AppCompatTextView tvBusinessExp;
    @BindView(R.id.tv_locus_num)
    AppCompatTextView tvLocusNum;
    @BindView(R.id.tv_income_amount)
    AppCompatTextView tvIncomeAmount;
    @BindView(R.id.tv_add_friend)
    TextView tvAddFriend;
    @BindView(R.id.iv_sgs)
    AppCompatImageView ivSgs;
    @BindView(R.id.cb_state)
    CheckBox cbState;

    private IncomePeopleVm incomePeopleVm;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_people_info;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(getString(R.string.base_info));
        int uid = getIntent().getIntExtra(CustomConfig.UID, 0);

        incomePeopleVm = getViewModel(IncomePeopleVm.class);
        incomePeopleVm.uid = uid;
        incomePeopleVm.detailLiveData.observe(this, peopleDetailBean -> {
            tvName.setText(peopleDetailBean.getUsername());
            flMobile.setVisibility(StringUtils.isTrimEmpty(peopleDetailBean.getTel()) ? View.GONE : View.VISIBLE);
            tvMobile.setText(peopleDetailBean.getTel());
            tvCompany.setText(peopleDetailBean.getCompany());
            tvOffice.setText(peopleDetailBean.getPosition());
            tvBusinessArea.setText(peopleDetailBean.getBusiness_city());
            if (peopleDetailBean.getExperience() >= 1 && peopleDetailBean.getExperience() <= 4)
                tvBusinessExp.setText(getResources().getStringArray(R.array.experience_list)[peopleDetailBean.getExperience() - 1] + "年");
            tvLocusNum.setText(peopleDetailBean.getCount() + "条");
            tvIncomeAmount.setText(String.format(getString(R.string.format_amount_people), peopleDetailBean.getPrice()));
            //身份认证显示状态
            ivSgs.setVisibility(peopleDetailBean.getCardStatus() == 2 ? View.VISIBLE : View.INVISIBLE);
            cbState.setChecked(peopleDetailBean.getCardStatus() == 2);
            cbState.setText(peopleDetailBean.getCardStatus() == 2 ? R.string.real_name : R.string.un_real_name);

            ImageLoader.load(this, new ImageConfig.Builder()
                    .url(peopleDetailBean.getImage())
                    .placeholder(R.drawable.default_head)
                    .errorPic(R.drawable.default_head)
                    .imageView(ivUser)
                    .build());
            if (peopleDetailBean.getApply_status() != null && peopleDetailBean.getApply_status().equals("1")) {
                tvAddFriend.setText(getString(R.string.im_add_friend));
            } else {
                tvAddFriend.setText(getString(R.string.im_send_message));
            }
        });

        incomePeopleVm.pageStateLiveData.observe(this, s -> {
            switch (s) {
                case PageState.PAGE_LOADING:
                    showLoading();
                    break;
                default:
                    hideLoading();
                    break;
            }
        });

        incomePeopleVm.getDetail();
    }

    @OnClick({R.id.iv_bar_back, R.id.fl_income_record, R.id.tv_add_friend})
    void onClick(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.fl_income_record:
                bundle.putInt(CustomConfig.UID, incomePeopleVm.uid);
                ActivityUtils.startActivity(bundle, IncomeRecordActivity.class);
                break;
            case R.id.tv_add_friend:
                incomePeopleVm.detailLiveData.observe(this, peopleDetailBean -> {
                    if (peopleDetailBean.getApply_status() != null && peopleDetailBean.getApply_status().equals("1")) {
                        bundle.putInt(CustomConfig.FRIEND_DATA, 0);
                        bundle.putString("id", String.valueOf(peopleDetailBean.getId()));
                        bundle.putString("name", peopleDetailBean.getUsername());
                        bundle.putString("msg", peopleDetailBean.getCompany());
                        bundle.putString("image", peopleDetailBean.getImage());
                        ActivityUtils.startActivity(bundle, IMAddFriendActivity.class);
                    } else {
                        RongIM.getInstance().startPrivateChat(PeopleInfoActivity.this, String.valueOf(peopleDetailBean.getId())
                                , peopleDetailBean.getUsername());
                    }
                });
                break;
        }
    }
}
