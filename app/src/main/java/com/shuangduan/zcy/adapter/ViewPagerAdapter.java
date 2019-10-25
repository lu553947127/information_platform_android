package com.shuangduan.zcy.adapter;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

/**
 * <pre>
 *     author : nwq
 *     time   : 2018/04/03
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private Fragment[] fragments;
    private String[] titles;

    public ViewPagerAdapter(FragmentManager fm, Fragment[] fragments) {
        super(fm);
        this.fragments = fragments;
    }

    public ViewPagerAdapter(FragmentManager fm, Fragment[] fragments, String[] titles) {
        super(fm);
        this.fragments = fragments;
        this.titles = titles;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    @Override
    public int getCount() {
        return fragments.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (titles != null){
            return titles[position];
        }
        return super.getPageTitle(position);
    }
}
