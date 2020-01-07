package com.shuangduan.zcy.view.design;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.blankj.utilcode.util.ToastUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.vm.SmartDesignVm;
import com.shuangduan.zcy.weight.XEditText;

import butterknife.BindView;
import butterknife.OnClick;

public class SmartDesignActivity extends BaseActivity {

    @BindView(R.id.et_phone)
    XEditText etPhone;
    @BindView(R.id.et_email)
    XEditText etEmail;
    @BindView(R.id.et_name)
    XEditText etName;
    @BindView(R.id.et_param)
    XEditText etParam;
    private SmartDesignVm vm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        isTranslationBar = true;
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_smart_design;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        vm = ViewModelProviders.of(this).get(SmartDesignVm.class);
        vm.addSmartDesignLiveData.observe(this, item -> {
            ToastUtils.showShort("提交智能设计成功。");
            finish();
        });
    }

    @OnClick({R.id.iv_bar_back, R.id.tv_event})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.tv_event:
                String phone = etPhone.getTrimmedString();
                String email = etEmail.getTrimmedString();
                String name = etName.getTrimmedString();
                String param = etParam.getTrimmedString();
                vm.addSmartDesign(phone, email, name, param);
                break;
        }
    }
}
