package com.shuangduan.zicaicloudplatform.view.fragment;

import android.os.Bundle;

import com.blankj.utilcode.util.BarUtils;
import com.shuangduan.zicaicloudplatform.R;
import com.shuangduan.zicaicloudplatform.base.BaseFragment;

/**
 * @author 宁文强 QQ:858777523
 * @name ZICAICloudPlatform
 * @class name：com.example.zicaicloudplatform.view.fragment
 * @class describe
 * @time 2019/7/5 13:26
 * @change
 * @chang time
 * @class describe
 */
public class HomeFragment extends BaseFragment {

    public static HomeFragment newInstance() {

        Bundle args = new Bundle();

        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_home;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            BarUtils.setStatusBarLightMode(mActivity, true);
        }
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.setStatusBarLightMode(mActivity, true);
        BarUtils.setStatusBarColor(mActivity, getResources().getColor(android.R.color.transparent));
    }

    @Override
    protected void initDataFromService() {

    }
}
