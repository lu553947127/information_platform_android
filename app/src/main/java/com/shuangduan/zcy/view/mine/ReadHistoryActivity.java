package com.shuangduan.zcy.view.mine;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.BarUtils;
import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadSir;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.ReadHistoryAdapter;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.vm.ReadHistoryVm;
import com.shuangduan.zcy.weight.DividerItemDecoration;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zicaicloudplatform.view.activity
 * @class describe  浏览历史
 * @time 2019/7/10 15:50
 * @change
 * @chang time
 * @class describe
 */
public class ReadHistoryActivity extends BaseActivity {
    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;

    private ReadHistoryAdapter readHistoryAdapter;
    private ReadHistoryVm readHistoryVm;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_read_history;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(getString(R.string.read_history));

        LoadSir.getDefault().register(refresh, (Callback.OnReloadListener) v -> {
            getDataFirst();
        });

        readHistoryVm = ViewModelProviders.of(this).get(ReadHistoryVm.class);
        readHistoryVm.getReadHistoryBeanMutableLiveData().observe(this, readHistoryBeans -> {
            if (readHistoryAdapter == null) {
                rv.setLayoutManager(new LinearLayoutManager(this));
                rv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST, R.drawable.divider_15));
                readHistoryAdapter = new ReadHistoryAdapter(R.layout.item_read_history, readHistoryBeans);
                rv.setAdapter(readHistoryAdapter);
            }else {
                readHistoryAdapter.setNewData(readHistoryBeans);
            }
        });

        refresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {

            }
        });

        refresh.setNoMoreData(true);
        getDataFirst();
    }

    private void getDataFirst(){
        readHistoryVm.getHistory();
    }

    @OnClick(R.id.iv_bar_back)
    void onClick(){
        finish();
    }
}
