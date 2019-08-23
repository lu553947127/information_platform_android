package com.shuangduan.zcy.view.projectinfo;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.blankj.utilcode.util.BarUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.utils.image.ImageConfig;
import com.shuangduan.zcy.utils.image.ImageLoader;
import com.shuangduan.zcy.vm.UserInfoVm;
import com.shuangduan.zcy.weight.CircleImageView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view.projectinfo
 * @class describe  轨迹信息头像查看详情
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
    private UserInfoVm userInfoVm;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_locus_owner_detail;
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
            tvBusinessExp.setText(userInfoBean.getExperience() > 0 ? getResources().getStringArray(R.array.experience_list)[userInfoBean.getExperience() - 1]: "");
            tvProduction.setText(userInfoBean.getManaging_products());
            switch (userInfoBean.getSex()){
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
            ImageLoader.load(this, new ImageConfig.Builder()
                    .url(userInfoBean.getImage_source())
                    .placeholder(R.drawable.default_head)
                    .errorPic(R.drawable.default_head)
                    .imageView(ivUser)
                    .build());
        });
        userInfoVm.pageStateLiveData.observe(this, s -> {
            showPageState(s);
        });
        userInfoVm.information();
    }

    @OnClick({R.id.iv_bar_back})
    void onClick(){ finish(); }
}
