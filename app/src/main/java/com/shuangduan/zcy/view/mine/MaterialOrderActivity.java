package com.shuangduan.zcy.view.mine;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.widget.AppCompatTextView;

import com.shuangduan.zcy.R;
import com.shuangduan.zcy.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author xuyu
 * @Package com.shuangduan.zcy.view.mine$
 * @class MaterialOrderActivity$
 * @class 物资预定Activity
 * @time 2019/9/25 13:41
 * @change
 * @class describe
 */
public class MaterialOrderActivity extends BaseActivity {
    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_material_order;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        tvBarTitle.setText(R.string.my_material);
    }

    @OnClick({R.id.iv_bar_back})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_bar_back:
                finish();
                break;
        }
    }
}
