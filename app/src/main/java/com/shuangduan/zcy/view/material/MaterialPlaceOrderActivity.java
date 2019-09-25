package com.shuangduan.zcy.view.material;

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
 * @Package: com.shuangduan.zcy.view.material
 * @ClassName: MaterialPlaceOrderActivity
 * @Description: 物资提交预定页面
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/9/25 13:25
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/9/25 13:25
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class MaterialPlaceOrderActivity extends BaseActivity {

    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_material_place_order;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(getString(R.string.material_place_order));
    }

    @OnClick({R.id.iv_bar_back})
    void onClick(){ finish(); }
}
