package com.shuangduan.zcy.view.mine.set;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;

import com.blankj.utilcode.util.BarUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.base.BaseActivity;

import butterknife.BindView;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.view.mine
 * @ClassName: ReceivingAddressActivity
 * @Description: 收货地址页面
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/12/15 15:14
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/12/15 15:14
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class ReceivingAddressActivity extends BaseActivity {
    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_receiving_address;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText("收货地址");
    }
}
