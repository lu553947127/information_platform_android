package com.shuangduan.zcy.view.material;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;

import com.blankj.utilcode.util.BarUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view.material
 * @class describe  物质预定成功
 * @time 2019/8/24 17:08
 * @change
 * @chang time
 * @class describe
 */
public class MaterialOrderSuccessActivity extends BaseActivity {
    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_material_order_success;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText("完成订单");
    }

    @OnClick({R.id.iv_bar_back, R.id.tv_done})
    void onClick(View view){
        switch (view.getId()){
            case R.id.iv_bar_back:
            case R.id.tv_done:
                finish();
                break;
        }
    }
}
