package com.shuangduan.zcy.base;

import android.content.Context;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.blankj.utilcode.util.LogUtils;
import com.shuangduan.zcy.dialog.BaseDialog;
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
 *     desc   : 懒加载
 *     version: 1.0
 * </pre>
 */

public abstract class BaseLazyFragment extends Fragment implements IView {

    public Context mContext;
    public FragmentActivity mActivity;
    private Unbinder unBinder;
    public Bundle arguments;
    private LoadDialog loadDialog;
    private boolean isPrepared = false;//页面ui初始化完成
    public boolean isInited = false;//数据是否已从服务器拉取，拉取成功后设为true
    private SparseArray<BaseDialog> dialogArray = new SparseArray<>();

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
        if (isUseEventBus()){
            EventBus.getDefault().register(this);
        }
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
        loadDialog.showDialog();
    }

    @Override
    public void hideLoading() {
        if (loadDialog != null){
            loadDialog.hideDialog();
        }
    }

    /**
     * 保险起见的dialog关闭，防止内存泄漏
     */
    public void addDialog(BaseDialog dialog){
        dialogArray.put(dialogArray.size(), dialog);
    }

    @Override
    public void onDestroyView() {
        unBinder.unbind();
        if (isUseEventBus() && EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this);
        }
        if (loadDialog != null){
            loadDialog.dismiss();
            loadDialog = null;
        }
        for (int i = 0; i < dialogArray.size(); i++) {
            if (dialogArray.get(i) != null){
                dialogArray.get(i).dismiss();
            }
        }
        dialogArray = null;
        super.onDestroyView();
    }

    /**
     * 初始化布局
     * @return 布局
     */
    protected abstract int initLayout();

    /**
     * EventBus开关
     */
    public abstract boolean isUseEventBus();

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
