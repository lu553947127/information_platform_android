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
import com.shuangduan.zcy.vm.UpdatePwdVm;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view.mine
 * @class describe
 * @time 2019/9/2 14:58
 * @change
 * @chang time
 * @class describe
 */
public class UpdatePwdActivity extends BaseActivity {
    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.edt_pwd_old)
    AppCompatEditText edtPwdOld;
    @BindView(R.id.edt_pwd)
    AppCompatEditText edtPwd;
    @BindView(R.id.edt_pwd_again)
    AppCompatEditText edtPwdAgain;
    private UpdatePwdVm updatePwdVm;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_update_pwd;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(getString(R.string.update_pwd));

        updatePwdVm = ViewModelProviders.of(this).get(UpdatePwdVm.class);
        updatePwdVm.resultLiveData.observe(this, o -> {
            ToastUtils.showShort(getString(R.string.pwd_reset_success));
            finish();
        });
        updatePwdVm.pageStateLiveData.observe(this, s -> {
            showPageState(s);
        });
    }

    @OnClick({R.id.iv_bar_back, R.id.tv_confirm})
    void onClick(View view){
        switch (view.getId()){
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.tv_confirm:
                if (TextUtils.isEmpty(edtPwdOld.getText())){
                    ToastUtils.showShort(getString(R.string.hint_mobile_code));
                    return;
                }
                if (TextUtils.isEmpty(edtPwd.getText())){
                    ToastUtils.showShort(getString(R.string.hint_pwd));
                    return;
                }
                if (TextUtils.isEmpty(edtPwdAgain.getText())){
                    ToastUtils.showShort(getString(R.string.hint_new_pwd));
                    return;
                }
                String pwdAgain = edtPwdAgain.getText().toString();
                String pwd = edtPwd.getText().toString();
                if (!pwd.equals(pwdAgain)){
                    ToastUtils.showShort(getString(R.string.pwd_no_same));
                    return;
                }
                updatePwdVm.update(edtPwdOld.getText().toString(), pwd);
                break;
        }
    }
}
