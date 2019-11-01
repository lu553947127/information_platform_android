package com.shuangduan.zcy.adminManage.view.turnover;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.blankj.utilcode.util.BarUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adminManage.vm.TurnoverDetailVm;
import com.shuangduan.zcy.adminManage.vm.TurnoverVm;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.adminManage.view
 * @ClassName: TurnoverDetailActivity
 * @Description: 周转材料详情
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/10/30 14:48
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/10/30 14:48
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class TurnoverDetailActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;

    private int constructionId;
    private TurnoverDetailVm turnoverDetailVm;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_turnover_detail;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @SuppressLint("NewApi")
    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(R.string.admin_turnover_material_details);

        constructionId = getIntent().getIntExtra(CustomConfig.CONSTRUCTION_ID, 0);

        turnoverDetailVm = ViewModelProviders.of(this).get(TurnoverDetailVm.class);

        turnoverDetailVm.getTurnoverDetail(constructionId);

        turnoverDetailVm.turnoverDetailLiveData.observe(this, turnoverBean -> {

        });
    }

    @OnClick({R.id.iv_bar_back})
    void OnClick(View view) {
        switch (view.getId()) {
            case R.id.iv_bar_back:
                finish();
                break;
        }
    }
}
