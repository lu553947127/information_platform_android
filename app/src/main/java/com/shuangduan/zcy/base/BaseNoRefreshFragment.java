package com.shuangduan.zcy.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.base
 * @ClassName: BaseNoRefreshFragment
 * @Description: java类作用描述
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/11/30 17:41
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/11/30 17:41
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public abstract class BaseNoRefreshFragment extends BaseFragment implements IView {


    public boolean isVisible;//当从另一个activity回到fragment所在的activity 当fragment回调onResume方法的时候，可以通过这个变量判断fragment是否可见，来决定是否要刷新数据
    private boolean isLoaded;//是否执行了lazyLoad方法


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        isPrepared = true;
        return super.onCreateView(inflater,container,savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initDataAndEvent(savedInstanceState);
        onVisible();
    }

    /*
     * 此方法在viewpager嵌套fragment时会回调
     * 查看FragmentPagerAdapter源码中instantiateItem和setPrimaryItem会调用此方法
     * 在所有生命周期方法前调用
     * 这个基类适用于在viewpager嵌套少量的fragment页面
     * 该方法是第一个回调，可以将数据放在这里处理（viewpager默认会预加载一个页面）
     * 只在fragment可见时加载数据，加快响应速度
     * */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            onVisible();
        }else {
            onInvisible();
        }
    }

    //ViewPager嵌套Fragment卡顿解决方法 避免Viewpager滑动重复加载Fragment Fragment延迟加载
    protected void onVisible() {
        isVisible = true;
        if (!isLoaded && isPrepared && getUserVisibleHint()) {
            isLoaded = true;
            lazyLoad();
        }
    }

    //防止view的重复加载 与FragmentPagerAdapter 中destroyItem方法取消调用父类的效果是一样的
    protected void onInvisible() {
        isVisible = false;
    }

    protected void lazyLoad() {
        if (isPrepared && getUserVisibleHint() && !isInited) {
            initDataFromService();
        }
    }

    protected abstract void initDataAndEvent(Bundle savedInstanceState);

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState, View view) {
        initDataAndEvent(savedInstanceState);
    }

}
