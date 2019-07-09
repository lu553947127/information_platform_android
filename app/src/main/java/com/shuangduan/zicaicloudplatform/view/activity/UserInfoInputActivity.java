package com.shuangduan.zicaicloudplatform.view.activity;

import android.view.View;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.shuangduan.zicaicloudplatform.R;
import com.shuangduan.zicaicloudplatform.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 宁文强 QQ:858777523
 * @name ZICAICloudPlatform
 * @class name：com.example.zicaicloudplatform.view.activity
 * @class describe
 * @time 2019/7/5 10:53
 * @change
 * @chang time
 * @class describe
 */
public class UserInfoInputActivity extends BaseActivity {

    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.tv_bar_right)
    AppCompatTextView tvBarRight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.edt_name)
    AppCompatEditText edtName;
    @BindView(R.id.tv_sex)
    AppCompatTextView tvSex;
    @BindView(R.id.edt_id_card)
    AppCompatEditText edtIdCard;
    @BindView(R.id.edt_email)
    AppCompatEditText edtEmail;
    @BindView(R.id.edt_company)
    AppCompatEditText edtCompany;
    @BindView(R.id.edt_office)
    AppCompatEditText edtOffice;
    @BindView(R.id.tv_business_area)
    AppCompatTextView tvBusinessArea;
    @BindView(R.id.tv_business_exp)
    AppCompatTextView tvBusinessExp;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_user_info_input;
    }

    @Override
    protected void initDataAndEvent() {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(getString(R.string.base_info));
        tvBarRight.setText(getString(R.string.save));
    }

    @OnClick({R.id.iv_bar_back, R.id.tv_bar_right, R.id.tv_sex, R.id.tv_business_area, R.id.tv_business_exp})
    void onClick(View view){
        switch (view.getId()){
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.tv_bar_right:
                ActivityUtils.startActivity(MainActivity.class);
                break;
            case R.id.tv_sex:
                break;
            case R.id.tv_business_area:
                break;
            case R.id.tv_business_exp:
                break;
        }
    }

}
