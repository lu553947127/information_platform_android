package com.shuangduan.zcy.weight.selectorview;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shuangduan.zcy.R;

import java.util.List;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.weight.selectorview
 * @class describe
 * @time 2019/7/13 9:59
 * @change
 * @chang time
 * @class describe
 */
public abstract class BaseSelectorView<T extends BaseSelectorBean> extends RecyclerView {
    public List<T> data;
    public BaseSelectorAdapter adapter;

    public BaseSelectorView(@NonNull Context context) {
        this(context, null);
    }

    public BaseSelectorView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseSelectorView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context){
        init();
        setLayoutManager(new LinearLayoutManager(context));
        setAdapter(adapter);
    }

    public void setData(List<T> data) {
        this.data = data;
        if (adapter != null){
            adapter.setNewData(data);
        }
    }

    /**
     * 修改所有数据选中状态
     * @param i
     */
    public void setSelectState(int i){
        for (int j = 0; j < data.size(); j++) {
            data.get(j).setIsSelect(i);
        }
    }

    @Nullable
    @Override
    public BaseSelectorAdapter getAdapter() {
        return adapter;
    }

    protected abstract void init();
}
