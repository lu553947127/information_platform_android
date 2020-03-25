package com.shuangduan.zcy.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * <pre>
 *     author : nwq
 *     time   : 2018/04/03
 *     desc   : 懒加载
 *     version: 1.0
 * </pre>
 */

public abstract class BaseLazyFragment extends BaseFragment implements IView{


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        isPrepared = true;
        return super.onCreateView(inflater,container,savedInstanceState);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initDataAndEvent(savedInstanceState,view);
        lazyLoad();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            lazyLoad();
        }
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
