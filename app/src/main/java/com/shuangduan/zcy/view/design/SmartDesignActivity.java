package com.shuangduan.zcy.view.design;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.ViewModelProviders;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.utils.DensityUtil;
import com.shuangduan.zcy.view.demand.FindFoundationActivity;
import com.shuangduan.zcy.vm.SmartDesignVm;
import com.shuangduan.zcy.weight.AdaptationScrollView;
import com.shuangduan.zcy.weight.XEditText;

import butterknife.BindView;
import butterknife.OnClick;

public class SmartDesignActivity extends BaseActivity {
    @BindView(R.id.scroll)
    AdaptationScrollView scrollView;
    @BindView(R.id.rl_toolbar)
    RelativeLayout toolbar;

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
        //滑动布局滑动监听
        scrollView.setOnScrollChangeListener(new AdaptationScrollView.OnScrollChangeListener() {
            private int mScrollY_2 = 0;
            private int lastScrollY = 0;
            private int h = DensityUtil.dp2px(210);
            //设置折叠标题背景颜色
            private int color = ContextCompat.getColor(SmartDesignActivity.this, R.color.colorPrimary) & 0x00ffffff;

            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (lastScrollY < h) {
                    LogUtils.e(lastScrollY);
                    scrollY = Math.min(h, scrollY);
                    mScrollY_2 = scrollY > h ? h : scrollY;
                    toolbar.setAlpha(1f * mScrollY_2 / h);
                    toolbar.setBackgroundColor(((255 * mScrollY_2 / h) << 24) | color);
                } else {
                    toolbar.setVisibility(View.VISIBLE);
                }
                lastScrollY = scrollY;
            }
        });


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
            case R.id.iv_bar_back_new:
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
