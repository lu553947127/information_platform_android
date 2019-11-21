package com.shuangduan.zcy.view.headlines;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.view.headlines
 * @ClassName: BaseFragmentRefreshAdapter
 * @Description: FragmentPagerAdapter不会销毁Fragment，也不会销毁FragmentManager中缓存的Fragment,FragmentStatePagerAdapter可实现整体刷新
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/11/21 9:32
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/11/21 9:32
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class BaseFragmentRefreshAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> mFragmentList;
    private String[] mTitles;

    public BaseFragmentRefreshAdapter(FragmentManager fm) {
        this(fm, null, null);
    }

    public BaseFragmentRefreshAdapter(FragmentManager fm, List<Fragment> fragmentList, String[] mTitles) {
        super(fm);
        if (fragmentList == null) {
            fragmentList = new ArrayList<>();
        }
        this.mFragmentList = fragmentList;
        this.mTitles = mTitles;
    }

    public void add(Fragment fragment) {
        if (isEmpty()) {
            mFragmentList = new ArrayList<>();
        }
        mFragmentList.add(fragment);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return isEmpty() ? null : mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return isEmpty() ? 0 : mFragmentList.size();
    }

    public boolean isEmpty() {
        return mFragmentList == null;

    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
