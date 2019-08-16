package com.shuangduan.zcy.view.people;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.utils.image.ImageConfig;
import com.shuangduan.zcy.utils.image.ImageLoader;
import com.shuangduan.zcy.vm.IncomePeopleVm;
import com.shuangduan.zcy.vm.UserInfoVm;
import com.shuangduan.zcy.weight.CircleImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view.people
 * @class describe  人脉个人详情
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
    private IncomePeopleVm incomePeopleVm;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_people_info;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(getString(R.string.base_info));
        int uid = getIntent().getIntExtra(CustomConfig.UID, 0);

        incomePeopleVm = ViewModelProviders.of(this).get(IncomePeopleVm.class);
        incomePeopleVm.uid = uid;
        incomePeopleVm.detailLiveData.observe(this, peopleDetailBean -> {
            tvName.setText(peopleDetailBean.getUsername());
            tvMobile.setText(peopleDetailBean.getTel());
            tvCompany.setText(peopleDetailBean.getCompany());
            tvOffice.setText(peopleDetailBean.getPosition());
            tvBusinessArea.setText(peopleDetailBean.getBusiness_city());
            if (peopleDetailBean.getExperience() >= 1 && peopleDetailBean.getExperience() <= 4)
                tvBusinessExp.setText(getResources().getStringArray(R.array.experience_list)[peopleDetailBean.getExperience() - 1]);
            tvLocusNum.setText(peopleDetailBean.getCount() + "条");
            tvIncomeAmount.setText(String.format(getString(R.string.format_amount), peopleDetailBean.getPrice()));

            ImageLoader.load(this, new ImageConfig.Builder()
                    .url(peopleDetailBean.getImage())
                    .placeholder(R.drawable.default_head)
                    .errorPic(R.drawable.default_head)
                    .imageView(ivUser)
                    .build());
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

    @OnClick({R.id.iv_bar_back, R.id.fl_income_record})
    void onClick(View view){
        switch (view.getId()){
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.fl_income_record:
                Bundle bundle = new Bundle();
                bundle.putInt(CustomConfig.UID, incomePeopleVm.uid);
                ActivityUtils.startActivity(bundle, IncomeRecordActivity.class);
                break;
        }
    }
}
