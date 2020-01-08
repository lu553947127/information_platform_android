package com.shuangduan.zcy.view.mine.demand;

import android.os.Bundle;

import com.shuangduan.zcy.R;
import com.shuangduan.zcy.base.BaseLazyFragment;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.view.mine.demand
 * @ClassName: FindMineBaseFragment
 * @Description: 我的需求，找基地
 * @Author: 鹿鸿祥
 * @CreateDate: 2020/1/8 16:46
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2020/1/8 16:46
 * @UpdateRemark: 更新说明
 * @Version: 1.1.0
 */
public class FindMineBaseFragment extends BaseLazyFragment {

    public static FindMineBaseFragment newInstance() {
        Bundle args = new Bundle();
        FindMineBaseFragment fragment = new FindMineBaseFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_order_recruit;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {

    }

    @Override
    protected void initDataFromService() {

    }
}
