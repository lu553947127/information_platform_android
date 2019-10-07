package com.shuangduan.zcy.view.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.Switch;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.SPUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.dialog.BaseDialog;
import com.shuangduan.zcy.dialog.CustomDialog;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.utils.LoginUtils;
import com.shuangduan.zcy.view.login.WelcomeActivity;
import com.shuangduan.zcy.vm.ExitVm;

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
    private ExitVm exitVm;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_set;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(getString(R.string.set));

        exitVm = ViewModelProviders.of(this).get(ExitVm.class);
        exitVm.init();

        switchSound.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {

            } else {

            }
        });
        switchModel.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {

            } else {

            }
        });


        exitVm.exitLiveData.observe(this, o -> {
            LoginUtils.getExitLogin();
        });

        exitVm.pageStateLiveData.observe(this, s -> {
            switch (s) {
                case PageState.PAGE_ERROR:
                case PageState.PAGE_SERVICE_ERROR:
                case PageState.PAGE_LOAD_SUCCESS:
                    hideLoading();
                    break;
                case PageState.PAGE_LOADING:
                    showLoading();
                    break;
            }
        });
    }

    @OnClick({R.id.iv_bar_back, R.id.tv_update_pwd, R.id.tv_about_ours, R.id.tv_exit})
    void onClick(View view) {
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
            case R.id.tv_exit:
                new CustomDialog(SetActivity.this)
                        .setTip(getString(R.string.exit_confirm))
                        .setCallBack(new BaseDialog.CallBack() {
                            @Override
                            public void cancel() {
                            }

                            @Override
                            public void ok(String s) {
                                exitVm.exit();
                            }
                        }).showDialog();


                break;
        }
    }
}
