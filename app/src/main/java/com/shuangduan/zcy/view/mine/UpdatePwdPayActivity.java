package com.shuangduan.zcy.view.mine;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.vm.UpdatePwdPayVm;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view.mine
 * @class describe  修改支付密码
 * @time 2019/8/13 18:05
 * @change
 * @chang time
 * @class describe
 */
public class UpdatePwdPayActivity extends BaseActivity {
    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.edt_pwd_old)
    AppCompatEditText edtPwdOld;
    @BindView(R.id.edt_pwd_new)
    AppCompatEditText edtPwdNew;
    private UpdatePwdPayVm updatePwdPayVm;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_update_pwd_pay;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(getString(R.string.update_pwd));

        updatePwdPayVm = ViewModelProviders.of(this).get(UpdatePwdPayVm.class);
        updatePwdPayVm.updatePwdLiveData.observe(this, o -> {
            ToastUtils.showShort(getString(R.string.pwd_pay_update_success));
            finish();
        });
        updatePwdPayVm.pageStateLiveData.observe(this, s -> {
            switch (s){
                case PageState.PAGE_LOADING:
                    showLoading();
                    break;
                default:
                    hideLoading();
                    break;
            }
        });
    }

    @OnClick({R.id.iv_bar_back, R.id.tv_save})
    void onClick(View view){
        switch (view.getId()){
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.tv_save:
                if (TextUtils.isEmpty(edtPwdOld.getText())){
                    ToastUtils.showShort(getString(R.string.hint_mobile_code));
                    return;
                }
                if (TextUtils.isEmpty(edtPwdNew.getText())){
                    ToastUtils.showShort(getString(R.string.hint_SMS_verification_code));
                    return;
                }
                updatePwdPayVm.updatePwd(edtPwdOld.getText().toString(), edtPwdNew.getText().toString());
                break;
        }
    }
}
