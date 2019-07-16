package com.shuangduan.zcy.view.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.Switch;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zicaicloudplatform.view.activity
 * @class describe  设置
 * @time 2019/7/10 11:54
 * @change
 * @chang time
 * @class describe
 */
public class SetActivity extends BaseActivity {
    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.switch_sound)
    Switch switchSound;
    @BindView(R.id.switch_model)
    Switch switchModel;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_set;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(getString(R.string.set));

        switchSound.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked){

            }else {

            }
        });
        switchModel.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked){

            }else {

            }
        });
    }

    @OnClick({R.id.iv_bar_back, R.id.tv_update_pwd, R.id.tv_about_ours})
    void onClick(View view){
        switch (view.getId()) {
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.tv_update_pwd:
                ActivityUtils.startActivity(UpdatePwdActivity.class);
                break;
            case R.id.tv_about_ours:
                ActivityUtils.startActivity(AboutOursActivity.class);
                break;
        }
    }
}
