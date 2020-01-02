package com.shuangduan.zcy.view.design;

import android.os.Bundle;

import com.shuangduan.zcy.R;
import com.shuangduan.zcy.base.BaseActivity;

public class SmartDesignOrderActivity extends BaseActivity {
    @Override
    protected int initLayoutRes() {
        return R.layout.activity_smart_design_order;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {

    }
}
