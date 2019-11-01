package com.shuangduan.zcy.adminManage.view.turnover;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;

import com.blankj.utilcode.util.BarUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.adminManage.view.turnover
 * @ClassName: TurnoverAddActivity
 * @Description: 周转材料添加
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/11/1 9:05
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/11/1 9:05
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class TurnoverAddActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @Override
    protected int initLayoutRes() {
        return R.layout.activity_turnover_add;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(R.string.admin_turnover_material_add);
    }

    @OnClick(R.id.iv_bar_back)
    void onClick() {
        finish();
    }
}
