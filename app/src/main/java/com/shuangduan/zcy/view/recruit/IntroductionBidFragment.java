package com.shuangduan.zcy.view.recruit;

import android.os.Bundle;

import com.shuangduan.zcy.R;
import com.shuangduan.zcy.base.BaseFragment;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view.recruit
 * @class describe  招标简介
 * @time 2019/7/12 17:12
 * @change
 * @chang time
 * @class describe
 */
public class IntroductionBidFragment extends BaseFragment {

    public static IntroductionBidFragment newInstance() {
        
        Bundle args = new Bundle();
        
        IntroductionBidFragment fragment = new IntroductionBidFragment();
        fragment.setArguments(args);
        return fragment;
    }
    
    @Override
    protected int initLayout() {
        return R.layout.fragment_introduction_bid;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {

    }

    @Override
    protected void initDataFromService() {

    }
}
