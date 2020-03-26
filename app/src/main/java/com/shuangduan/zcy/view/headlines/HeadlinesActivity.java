package com.shuangduan.zcy.view.headlines;

import android.os.Bundle;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
;
import androidx.viewpager.widget.ViewPager;

import com.blankj.utilcode.util.BarUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.model.bean.HeadlinesGetCategoryBean;
import com.shuangduan.zcy.vm.HeadlinesVm;
import com.shuangduan.zcy.weight.XTabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view.headlines
 * @class describe  基建头条
 * @time 2019/8/15 14:58
 * @change
 * @chang time
 * @class describe
 */
public class HeadlinesActivity extends BaseActivity {
    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tab_layout)
    XTabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    private List<Fragment> mFragments=new ArrayList<>();

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_headlines;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(getString(R.string.headlines));

        HeadlinesVm headlinesVm = getViewModel(HeadlinesVm.class);
        headlinesVm.headlinesGetCategoryLiveData.observe(this, headlinesGetCategoryBean -> {
            headlinesGetCategoryBean.add(0,new HeadlinesGetCategoryBean(0,"全部"));
            String[] titleList = new String[headlinesGetCategoryBean.size()];
            for (int i = 0; i < headlinesGetCategoryBean.size(); i++) {
                titleList[i] = headlinesGetCategoryBean.get(i).getCatname();
                HeadlinesFragment headlinesFragment = new HeadlinesFragment(headlinesGetCategoryBean.get(i).getId());
                mFragments.add(headlinesFragment);
            }
            BaseFragmentRefreshAdapter adapter = new BaseFragmentRefreshAdapter(getSupportFragmentManager(), mFragments, titleList);
            viewPager.setAdapter(adapter);
            tabLayout.setupWithViewPager(viewPager);
        });
        headlinesVm.headlinesGetCategory();
    }

    @OnClick(R.id.iv_bar_back)
    void onClick(){finish();}
}
