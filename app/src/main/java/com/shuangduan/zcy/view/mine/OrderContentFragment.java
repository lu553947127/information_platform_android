package com.shuangduan.zcy.view.mine;

import android.os.Bundle;

import com.shuangduan.zcy.R;
import com.shuangduan.zcy.base.BaseLazyFragment;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view.mine
 * @class describe  概况订单
 * @time 2019/8/12 16:49
 * @change
 * @chang time
 * @class describe
 */
public class OrderContentFragment extends BaseLazyFragment {
    public static OrderContentFragment newInstance() {

        Bundle args = new Bundle();

        OrderContentFragment fragment = new OrderContentFragment();
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
