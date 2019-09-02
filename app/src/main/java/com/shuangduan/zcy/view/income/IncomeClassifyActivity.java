package com.shuangduan.zcy.view.income;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view.income
 * @class describe  收益分类
 * @time 2019/8/9 14:01
 * @change
 * @chang time
 * @class describe
 */
public class IncomeClassifyActivity extends BaseActivity {
    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_income_classify;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(getString(R.string.income_classify));
    }

    @OnClick({R.id.iv_bar_back, R.id.tv_income_release, R.id.tv_income_locus, R.id.tv_income_msg, R.id.tv_income_product_rights, R.id.tv_income_people})
    void onClick(View view){
        Bundle bundle = new Bundle();
        switch (view.getId()){
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.tv_income_release:
                ActivityUtils.startActivity(IncomeReleaseActivity.class);
                break;
            case R.id.tv_income_locus:
                ActivityUtils.startActivity(IncomeLocusActivity.class);
                break;
            case R.id.tv_income_msg:
                ActivityUtils.startActivity(IncomeMsgActivity.class);
                break;
            case R.id.tv_income_product_rights:
                ActivityUtils.startActivity(IncomeProductRightsActivity.class);
                break;
            case R.id.tv_income_people:
                bundle.putInt(CustomConfig.PEOPLE_DEGREE, CustomConfig.SEVEN_DEGREE);
                ActivityUtils.startActivity(bundle, IncomePeopleActivity.class);
                break;
        }
    }
}
