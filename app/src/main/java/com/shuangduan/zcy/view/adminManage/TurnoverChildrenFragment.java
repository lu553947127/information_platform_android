package com.shuangduan.zcy.view.adminManage;

import android.os.Bundle;
import android.view.View;

import com.shuangduan.zcy.R;
import com.shuangduan.zcy.base.BaseFragment;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.view.adminManage
 * @ClassName: TurnoverChildrenFragment
 * @Description: 周转材料子公司列表
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/10/23 15:02
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/10/23 15:02
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class TurnoverChildrenFragment extends BaseFragment {

    public static TurnoverChildrenFragment newInstance() {
        Bundle args = new Bundle();
        TurnoverChildrenFragment fragment = new TurnoverChildrenFragment();
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
