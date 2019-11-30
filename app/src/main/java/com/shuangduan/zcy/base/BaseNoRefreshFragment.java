package com.shuangduan.zcy.base;

import android.content.Context;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.shuangduan.zcy.dialog.BaseDialog;
import com.shuangduan.zcy.dialog.LoadDialog;
import com.shuangduan.zcy.factory.EmptyViewFactory;

import org.greenrobot.eventbus.EventBus;

import java.util.Objects;

import butterknife.ButterKnife;
import butterknife.Unbinder;

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
public abstract class BaseNoRefreshFragment extends Fragment implements IView {

    public Context mContext;
    public FragmentActivity mActivity;
    private Unbinder unBinder;
    public Bundle arguments;
    private LoadDialog loadDialog;
    private boolean isPrepared = false;//页面ui初始化完成
    public boolean isInited = false;//数据是否已从服务器拉取，拉取成功后设为true
    private SparseArray<BaseDialog> dialogArray = new SparseArray<>();
    public boolean isVisible;//当从另一个activity回到fragment所在的activity 当fragment回调onResume方法的时候，可以通过这个变量判断fragment是否可见，来决定是否要刷新数据
    private boolean isLoaded;//是否执行了lazyLoad方法

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.mContext = context;
        this.mActivity = (FragmentActivity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        arguments = getArguments();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(initLayout(), container, false);
        unBinder = ButterKnife.bind(this, view);
        if (isUseEventBus()) {
            EventBus.getDefault().register(this);
        }
        isPrepared = true;
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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

    private void lazyLoad() {
        if (isPrepared && getUserVisibleHint() && !isInited) {
            initDataFromService();
        }
    }

    @Override
    public void showLoading() {
        if (loadDialog == null) {
            loadDialog = new LoadDialog(mActivity);
        }
        loadDialog.showDialog();
    }

    @Override
    public void hideLoading() {
        if (loadDialog != null) {
            loadDialog.hideDialog();
        }
    }

    /**
     * 保险起见的dialog关闭，防止内存泄漏
     */
    public void addDialog(BaseDialog dialog) {
        dialogArray.put(dialogArray.size(), dialog);
    }

    @Override
    public void onDestroyView() {
        unBinder.unbind();
        if (isUseEventBus() && EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        if (loadDialog != null) {
            loadDialog.dismiss();
            loadDialog = null;
        }

        if (dialogArray != null) {
            for (int i = 0; i < dialogArray.size(); i++) {
                if (dialogArray.get(i) != null) {
                    dialogArray.get(i).dismiss();
                }
            }
        }
        dialogArray = null;
        super.onDestroyView();
    }

    /**
     * 初始化布局
     *
     * @return 布局
     */
    protected abstract int initLayout();

    /**
     * EventBus开关
     */
    public abstract boolean isUseEventBus();

    /**
     * 初始化数据
     *
     * @param savedInstanceState 数据状态
     */
    protected abstract void initDataAndEvent(Bundle savedInstanceState);

    /**
     * 从服务器获取数据
     */
    protected abstract void initDataFromService();


    public View createEmptyView(int iconRes, int strRes, int btnStrRes, EmptyViewFactory.EmptyViewCallBack callBack) {
        BaseActivity activity = (BaseActivity) getActivity();
        return Objects.requireNonNull(activity).emptyViewFactory.createEmptyView(iconRes, strRes, btnStrRes, callBack);
    }

    public View createEmptyView(int iconRes, int strRes, int btnStrRes, int background, EmptyViewFactory.EmptyViewCallBack callBack) {
        BaseActivity activity = (BaseActivity) getActivity();
        return Objects.requireNonNull(activity).emptyViewFactory.createEmptyView(iconRes, strRes, btnStrRes,background, callBack);
    }
}
