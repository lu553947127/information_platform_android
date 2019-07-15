package com.shuangduan.zcy.view.projectinfo;

import android.os.Bundle;

import com.shuangduan.zcy.R;
import com.shuangduan.zcy.base.BaseFragment;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view.projectinfo
 * @class describe  消费列表
 * @time 2019/7/15 15:36
 * @change
 * @chang time
 * @class describe
 */
public class ProjectConsumptionFragment extends BaseFragment {
    public static ProjectConsumptionFragment newInstance() {

        Bundle args = new Bundle();

        ProjectConsumptionFragment fragment = new ProjectConsumptionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_consumption;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {

    }

    @Override
    protected void initDataFromService() {

    }
}
