package com.shuangduan.zcy.view.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.SPUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.view.login.MobileVerificationActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 宁文强 QQ:858777523
 * @name ZICAICloudPlatform
 * @class name：com.example.zicaicloudplatform.view.activity
 * @class describe  用户信息当前信息
 * @time 2019/7/8 11:36
 * @change
 * @chang time
 * @class describe
 */
public class UpdateResultActivity extends BaseActivity {
    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.tv_bar_right)
    AppCompatTextView tvBarRight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_phone)
    ImageView ivPhone;
    @BindView(R.id.tv_phone)
    TextView tvPhone;

    private String type;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_update_result;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        type = getIntent().getStringExtra(CustomConfig.UPDATE_TYPE);
        tvBarRight.setText(getString(R.string.modify));

        switch (type){
            case CustomConfig.updateTypePhone:
                tvBarTitle.setText(getString(R.string.phone));
                ivPhone.setImageResource(R.drawable.icon_phone);
                tvPhone.setText(String.format(getString(R.string.format_mobile), SPUtils.getInstance().getString(SpConfig.MOBILE)));
                break;
            case CustomConfig.updateTypeEmail:
                tvBarTitle.setText(getString(R.string.email));
                ivPhone.setImageResource(R.drawable.icon_email);
                tvPhone.setText(String.format(getString(R.string.format_email), SPUtils.getInstance().getString(SpConfig.EMAIL)));
                break;
        }
    }

    @OnClick({R.id.iv_bar_back, R.id.tv_bar_right})
    void onClick(View view){
        switch (view.getId()){
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.tv_bar_right:
                switch (type){
                    case CustomConfig.updateTypePhone:
                        ActivityUtils.startActivity(MobileVerificationActivity.class);
                        break;
                    case CustomConfig.updateTypeEmail:
                        ActivityUtils.startActivity(MobileVerificationActivity.class);
                        break;
                }
                finish();
                break;
        }
    }
}
