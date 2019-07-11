package com.shuangduan.zcy.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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

public abstract class BaseFragment extends Fragment implements IView {

    public Context mContext;
    public Activity mActivity;
    private Unbinder unBinder;
    public Bundle arguments;
    private LoadDialog loadDialog;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.mContext = context;
        this.mActivity = (Activity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        arguments = getArguments();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(initLayout(), container, false);
        unBinder = ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initDataAndEvent(savedInstanceState);
        load();
    }

    private void load() {
        initDataFromService();
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
