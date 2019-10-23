package com.shuangduan.zcy.view.adminManage;

import android.os.Bundle;
import android.view.View;

import com.shuangduan.zcy.R;
import com.shuangduan.zcy.base.BaseFragment;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.view.adminManage
 * @ClassName: TurnoverGroupFragment
 * @Description: 周转材料集团列表
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/10/23 15:01
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/10/23 15:01
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class TurnoverGroupFragment extends BaseFragment {

    public static TurnoverGroupFragment newInstance() {
        Bundle args = new Bundle();
        TurnoverGroupFragment fragment = new TurnoverGroupFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_turnover_material;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState, View view) {

    }

    @Override
    protected void initDataFromService() {

    }
}
