package com.shuangduan.zcy.adapter;

import android.content.Context;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

/**
 * @author 鹿鸿祥 QQ:553947127
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.adapter
 * @class describe
 * @time 2019/9/11 14:14
 * @change
 * @chang time
 * @class describe
 */
public class FragmentAdapter extends FragmentPagerAdapter {

    Context context;
    private List<Fragment> fragment;

    public FragmentAdapter(FragmentManager fm, Context context, List<Fragment> fragment) {
        super(fm);
        this.context = context;
        this.fragment = fragment;
    }



    @Override
    public Fragment getItem(int position) {
        return fragment.get(position);
    }
    @Override
    public int getCount() {
        return fragment.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);
    }

}
