package com.shuangduan.zcy.view.mine.demand;

import android.os.Bundle;

import com.shuangduan.zcy.R;
import com.shuangduan.zcy.base.BaseActivity;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.view.mine.demand
 * @ClassName: FindLogisticsDetailActivity
 * @Description: 个人中心找物流详情
 * @Author: 徐玉
 * @CreateDate: 2020/1/10 14:22
 * @UpdateUser:
 * @UpdateDate:
 * @UpdateRemark:
 * @Version: 1.0
 */
public class FindLogisticsDetailActivity extends BaseActivity {
    @Override
    protected int initLayoutRes() {
        return R.layout.activity_find_logistics_detail;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {

    }
}
