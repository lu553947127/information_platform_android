package com.shuangduan.zcy.view.mine;

import android.os.Bundle;

import com.shuangduan.zcy.R;
import com.shuangduan.zcy.base.BaseLazyFragment;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view.mine
 * @class describe  招采信息，订阅列表
 * @time 2019/8/2 17:55
 * @change
 * @chang time
 * @class describe
 */
public class RecruitSubFragment extends BaseLazyFragment {
    public static RecruitSubFragment newInstance() {

        Bundle args = new Bundle();

        RecruitSubFragment fragment = new RecruitSubFragment();
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
