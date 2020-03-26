package com.shuangduan.zcy.view.income;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.BarUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.IncomeRecordAdapter;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.vm.IncomePeopleVm;
import com.shuangduan.zcy.weight.DividerItemDecoration;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view.people
 * @class describe  收益记录
 * @time 2019/8/14 11:31
 * @change
 * @chang time
 * @class describe
 */
public class IncomeRecordActivity extends BaseActivity {
    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;
    private IncomePeopleVm incomePeopleVm;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_income_record;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(getString(R.string.income_record));

        View emptyView = emptyViewFactory.createEmptyView(R.drawable.icon_empty_project, R.string.empty_income_info, 0,R.color.colorBgDark, null);

        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST, R.drawable.divider_15));
        IncomeRecordAdapter adapter = new IncomeRecordAdapter(R.layout.item_income_record, null);
        adapter.setEmptyView(R.layout.layout_loading, rv);
        rv.setAdapter(adapter);

        incomePeopleVm = getViewModel(IncomePeopleVm.class);
        incomePeopleVm.uid = getIntent().getIntExtra(CustomConfig.UID, 0);
        incomePeopleVm.recordLiveData.observe(this, incomeRecordBean -> {
            if (incomeRecordBean.getPage() == 1) {
                adapter.setNewData(incomeRecordBean.getList());
                adapter.setEmptyView(emptyView);
            }else {
                adapter.addData(incomeRecordBean.getList());
            }
            setNoMore(incomeRecordBean.getPage(), incomeRecordBean.getCount());
        });

        refresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                incomePeopleVm.getMoreRecord();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                incomePeopleVm.getRecord();
            }
        });

        incomePeopleVm.getRecord();
    }

    private void setNoMore(int page, int count){
        if (page == 1){
            if (page * 10 >= count){
                if (refresh.getState() == RefreshState.None){
                    refresh.setNoMoreData(true);
                }else {
                    refresh.finishRefreshWithNoMoreData();
                }
            }else {
                refresh.finishRefresh();
            }
        }else {
            if (page * 10 >= count){
                refresh.finishLoadMoreWithNoMoreData();
            }else {
                refresh.finishLoadMore();
            }
        }
    }

    @OnClick(R.id.iv_bar_back)
    void onClick(){finish();}
}
