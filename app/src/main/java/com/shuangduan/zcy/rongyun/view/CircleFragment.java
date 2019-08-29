package com.shuangduan.zcy.rongyun.view;

import android.os.Bundle;
import android.view.View;

import com.blankj.utilcode.util.ActivityUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.base.BaseFragment;
import com.shuangduan.zcy.utils.BarUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.rongyun.view
 * @class describe  工程圈
 * @time 2019/8/27 15:11
 * @change
 * @chang time
 * @class describe
 */
public class CircleFragment extends BaseFragment {
    @BindView(R.id.fake_status_bar)
    View fakeStatusBar;

    public static CircleFragment newInstance() {

        Bundle args = new Bundle();

        CircleFragment fragment = new CircleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_circle;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.setStatusBarColorRes(fakeStatusBar, getResources().getColor(R.color.colorPrimary));
    }

    @Override
    protected void initDataFromService() {

    }

    @OnClick({R.id.tv_bar_title})
    void onClick(View view){
        switch (view.getId()){
            case R.id.tv_bar_title:
                ActivityUtils.startActivity(IMSearchActivity.class);
                break;
                default:
                    break;
        }
    }
}
