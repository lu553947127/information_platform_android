package com.shuangduan.zcy.view.infrastructure;

import android.os.Bundle;

import com.shuangduan.zcy.R;
import com.shuangduan.zcy.base.BaseLazyFragment;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view.infrastructure
 * @class describe
 * @time 2019/8/6 11:17
 * @change
 * @chang time
 * @class describe
 */
public class LeaseFragment extends BaseLazyFragment {
    public static LeaseFragment newInstance() {

        Bundle args = new Bundle();

        LeaseFragment fragment = new LeaseFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_project_info;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {

    }

    @Override
    protected void initDataFromService() {

    }
}
