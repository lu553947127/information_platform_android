package com.shuangduan.zcy.view.income;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.IncomeLocusAdapter;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.factory.EmptyViewFactory;
import com.shuangduan.zcy.view.projectinfo.ProjectInfoListActivity;
import com.shuangduan.zcy.view.release.ReleaseProjectActivity;
import com.shuangduan.zcy.vm.IncomeLocusVm;
import com.shuangduan.zcy.weight.DividerItemDecoration;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view.income
 * @class describe  发布动态收益
 * @time 2019/8/9 14:37
 * @change
 * @chang time
 * @class describe
 */
public class IncomeLocusActivity extends BaseActivity implements EmptyViewFactory.EmptyViewCallBack {
    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;
    private IncomeLocusVm incomeReleaseVm;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_income_release;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        View emptyView = emptyViewFactory.createEmptyView(R.drawable.icon_empty_income, R.string.empty_dynamics_income_info, R.string.to_release, this);

        tvBarTitle.setText(getString(R.string.income_locus));

        incomeReleaseVm = ViewModelProviders.of(this).get(IncomeLocusVm.class);

        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST, R.drawable.divider_15));
        IncomeLocusAdapter adapter = new IncomeLocusAdapter(R.layout.item_income_release, null);
        adapter.setEmptyView(R.layout.layout_loading, rv);
        rv.setAdapter(adapter);

        incomeReleaseVm.liveData.observe(this, incomeLocusBean -> {
            if (incomeLocusBean.getPage() == 1) {
                adapter.setNewData(incomeLocusBean.getList());
                adapter.setEmptyView(emptyView);
            } else {
                adapter.addData(incomeLocusBean.getList());
            }
            setNoMore(incomeLocusBean.getPage(), incomeLocusBean.getCount());
        });

        refresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                incomeReleaseVm.getMoreData();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                incomeReleaseVm.getData();
            }
        });

        incomeReleaseVm.getData();
    }

    private void setNoMore(int page, int count) {
        if (page == 1) {
            if (page * 10 >= count) {
                if (refresh.getState() == RefreshState.None) {
                    refresh.setNoMoreData(true);
                } else {
                    refresh.finishRefreshWithNoMoreData();
                }
            } else {
                refresh.finishRefresh();
            }
        } else {
            if (page * 10 >= count) {
                refresh.finishLoadMoreWithNoMoreData();
            } else {
                refresh.finishLoadMore();
            }
        }
    }

    @OnClick({R.id.iv_bar_back})
    void onClick() {
        finish();
    }

    @Override
    public void onEmptyClick() {
        Bundle bundle = new Bundle();
        bundle.putInt(CustomConfig.RELEASE_TYPE, 1);
        ActivityUtils.startActivity(bundle, ReleaseProjectActivity.class);
    }
}
