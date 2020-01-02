package com.shuangduan.zcy.view.design;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.shuangduan.zcy.R;
import com.shuangduan.zcy.base.BaseActivity;

public class SmartDesignActivity extends BaseActivity {

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

    }
}
