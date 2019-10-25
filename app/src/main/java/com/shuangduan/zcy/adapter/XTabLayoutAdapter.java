package com.shuangduan.zcy.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.adapter
 * @ClassName: XTabLayoutAdapter
 * @Description: java类作用描述
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/10/14 14:57
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/10/14 14:57
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class XTabLayoutAdapter extends PagerAdapter {

    private List<View> viewList;
    private List<String> tiltes;

    public XTabLayoutAdapter(List<View> viewList, List<String> tiltes) {
        this.viewList = viewList;
        this.tiltes = tiltes;
    }

    @Override
    public int getCount() {
        //这个方法是返回总共有几个滑动的页面（）
        return viewList == null ? 0 : viewList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        //该方法判断是否由该对象生成界面。
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //这个方法返回一个对象，该对象表明PagerAapter选择哪个对象放在当前的ViewPager中。这里我们返回当前的页面
        container.addView(viewList.get(position));
        return viewList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //这个方法从viewPager中移动当前的view。（划过的时候）
        container.removeView(viewList.get(position));
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return tiltes.get(position);
    }
}
