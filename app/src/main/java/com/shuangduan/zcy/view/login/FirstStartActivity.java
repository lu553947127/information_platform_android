package com.shuangduan.zcy.view.login;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SPUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.weight.MaterialIndicator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.view.login
 * @ClassName: FirstStartActivity
 * @Description: 引导页
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/9/17 9:17
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/9/17 9:17
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class FirstStartActivity extends BaseActivity implements View.OnClickListener, ViewPager.OnPageChangeListener{

    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.material_indicator)
    MaterialIndicator materialIndicator;
    private boolean isScrolled = false;
    private List<View> viewList=new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        isTranslationBar = true;
        super.onCreate(savedInstanceState);
        getSwipeBackLayout().setEnableGesture(false);
    }

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_first_start;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @SuppressLint("InflateParams")
    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        LayoutInflater inflater = getLayoutInflater();
        View view1 = inflater.inflate(R.layout.activity_first_start_one, null);
        View view2 = inflater.inflate(R.layout.activity_first_start_two, null);
        View view3 = inflater.inflate(R.layout.activity_first_start_three, null);
        viewList.add(view1);
        viewList.add(view2);
        viewList.add(view3);
        viewPager.setAdapter(new MyPagerAdapter());
        viewPager.addOnPageChangeListener(materialIndicator);
        materialIndicator.setAdapter(Objects.requireNonNull(viewPager.getAdapter()));
        viewPager.setOnPageChangeListener(this);
        view1.findViewById(R.id.iv_next_one).setOnClickListener(this);
        view2.findViewById(R.id.tv_next_two).setOnClickListener(this);
        view3.findViewById(R.id.tv_next_three).setOnClickListener(this);
        view3.findViewById(R.id.rl_experience).setOnClickListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
            case 1:
                materialIndicator.setVisibility(View.VISIBLE);
                break;
            case 2:
                materialIndicator.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        switch (state) {
            case ViewPager.SCROLL_STATE_DRAGGING:
                isScrolled = false;
                break;
            case ViewPager.SCROLL_STATE_SETTLING:
                isScrolled = true;
                break;
            case ViewPager.SCROLL_STATE_IDLE:
                //滑动跳转
                if (viewPager.getCurrentItem() == Objects.requireNonNull(viewPager.getAdapter()).getCount() - 1 && !isScrolled) {
                    SPUtils.getInstance().put(SpConfig.FIRST_APP, 1);
                    ActivityUtils.startActivity(WelcomeActivity.class);
                    finish();
                }
                isScrolled = true;
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_next_one:
                viewPager.setCurrentItem(1);
                break;
            case R.id.tv_next_two:
            case R.id.rl_experience:
            case R.id.tv_next_three:
                SPUtils.getInstance().put(SpConfig.FIRST_APP, 1);
                ActivityUtils.startActivity(WelcomeActivity.class);
                finish();
                break;
        }
    }

    private class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            //这个方法是返回总共有几个滑动的页面（）
            return viewList == null ? 0 : viewList.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            //该方法判断是否由该对象生成界面。
            return view==object;
        }

        @NonNull
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //这个方法返回一个对象，该对象表明PagerAapter选择哪个对象放在当前的ViewPager中。这里我们返回当前的页面
            container.addView(viewList.get(position));
            return viewList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, @NonNull Object object) {
            //这个方法从viewPager中移动当前的view。（划过的时候）
            container.removeView(viewList.get(position));
        }
    }
}
