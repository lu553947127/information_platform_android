package com.shuangduan.zcy.adminManage.view;

import android.os.Bundle;

import com.shuangduan.zcy.R;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseActivity;

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
    @Override
    protected int initLayoutRes() {
        return R.layout.activity_turnover_detail;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        int construction_id = getIntent().getIntExtra(CustomConfig.CONSTRUCTION_ID,0);
    }
}
