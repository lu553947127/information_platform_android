package com.shuangduan.zcy.view.projectinfo;

import android.os.Bundle;

import com.shuangduan.zcy.R;
import com.shuangduan.zcy.base.BaseFragment;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view.projectinfo
 * @class describe  轨迹
 * @time 2019/7/15 15:32
 * @change
 * @chang time
 * @class describe
 */
public class ProjectLocusFragment extends BaseFragment {
    public static ProjectLocusFragment newInstance() {

        Bundle args = new Bundle();

        ProjectLocusFragment fragment = new ProjectLocusFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_project_locus;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {

    }

    @Override
    protected void initDataFromService() {

    }
}
