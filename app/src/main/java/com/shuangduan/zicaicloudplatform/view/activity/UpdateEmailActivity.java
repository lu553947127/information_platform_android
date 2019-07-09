package com.shuangduan.zicaicloudplatform.view.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.shuangduan.zicaicloudplatform.R;
import com.shuangduan.zicaicloudplatform.app.CustomConfig;
import com.shuangduan.zicaicloudplatform.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 宁文强 QQ:858777523
 * @name ZICAICloudPlatform
 * @class name：com.example.zicaicloudplatform.view.activity
 * @class describe  修改邮箱
 * @time 2019/7/8 10:52
 * @change
 * @chang time
 * @class describe
 */
public class UpdateEmailActivity extends BaseActivity {

    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_update_email;
    }

    @Override
    protected void initDataAndEvent() {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(getString(R.string.update_email));
    }

    @OnClick({R.id.iv_bar_back, R.id.tv_confirm})
    void onClick(View view){
        switch (view.getId()){
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.tv_confirm:
                Bundle bundle = new Bundle();
                bundle.putString(CustomConfig.UPDATE_TYPE, CustomConfig.updateTypeEmail);
                ActivityUtils.startActivity(bundle, UpdateResultActivity.class);
                finish();
                break;
        }
    }
}
