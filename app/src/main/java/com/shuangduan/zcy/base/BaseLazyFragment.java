package com.shuangduan.zcy.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.shuangduan.zcy.dialog.LoadDialog;
import com.shuangduan.zcy.model.event.BaseEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * <pre>
 *     author : nwq
 *     time   : 2018/04/03
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public abstract class BaseLazyFragment extends Fragment implements IView {

    public Context mContext;
    public FragmentActivity mActivity;
    private Unbinder unBinder;
    public Bundle arguments;
    private LoadDialog loadDialog;
    private boolean isPrepared = false;
    /**
     * 数据是否已从服务器拉取，拉取成功后设为true
     */
    public boolean isInited = false;

    @Override
    public void onAttach(Context context) {
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
        EventBus.getDefault().register(this);
        isPrepared = true;
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initDataAndEvent(savedInstanceState);
        lazyLoad();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser){
            lazyLoad();
        }
    }

    private void lazyLoad() {
        if (isPrepared && getUserVisibleHint() && !isInited){
            initDataFromService();
        }
    }

    @Override
    public void showLoading() {
        if (loadDialog == null){
            loadDialog = new LoadDialog(mActivity);
        }
        if (!loadDialog.isShowing()){
            loadDialog.showDialog();
        }
    }

    @Override
    public void hideLoading() {
        if (loadDialog != null && loadDialog.isShowing()){
            loadDialog.hideDialog();
        }
    }

    @Override
    public void showContent() {

    }

    @Subscribe()
    public void onNormalEvent(BaseEvent normalEvent){

    }

    @Override
    public void onDestroyView() {
        unBinder.unbind();
        EventBus.getDefault().unregister(this);
        if (loadDialog != null){
            if (loadDialog.isShowing()) loadDialog.hideDialog();
            loadDialog = null;
        }
        super.onDestroyView();
    }

    /**
     * 初始化布局
     * @return 布局
     */
    protected abstract int initLayout();

    /**
     * 初始化数据
     * @param savedInstanceState 数据状态
     */
    protected abstract void initDataAndEvent(Bundle savedInstanceState);

    /**
     * 从服务器获取数据
     */
    protected abstract void initDataFromService();

}
