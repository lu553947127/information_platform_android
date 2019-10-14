package com.shuangduan.zcy.view.mine;

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
import com.shuangduan.zcy.adapter.SubOrderAdapter;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.factory.EmptyViewFactory;
import com.shuangduan.zcy.view.projectinfo.ProjectInfoListActivity;
import com.shuangduan.zcy.vm.OrderVm;
import com.shuangduan.zcy.weight.DividerItemDecoration;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view.mine
 * @class describe  我的认购
 * @time 2019/8/13 13:24
 * @change
 * @chang time
 * @class describe
 */
public class OrderSubActivity extends BaseActivity implements EmptyViewFactory.EmptyViewCallBack {

    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;
    private OrderVm orderVm;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_order_sub;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);

        View emptyView = emptyViewFactory.createEmptyView(R.drawable.icon_empty_deal, R.string.empty_mine_sub_info, R.string.to_subscribe, this);

        tvBarTitle.setText(getString(R.string.subscribe));

        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST, R.drawable.divider_15));
        SubOrderAdapter adapter = new SubOrderAdapter(R.layout.item_order_sub, null);
        adapter.setEmptyView(R.layout.layout_loading, rv);
        rv.setAdapter(adapter);

        orderVm = ViewModelProviders.of(this).get(OrderVm.class);
        orderVm.subLiveData.observe(this, orderSubBean -> {
            if (orderSubBean.getPage() == 1) {
                adapter.setNewData(orderSubBean.getList());
                adapter.setEmptyView(emptyView);
            } else {
                adapter.addData(orderSubBean.getList());
            }
            setNoMore(orderSubBean.getPage(), orderSubBean.getCount());
        });

        refresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                orderVm.getMoreSubOrder();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                orderVm.getSubOrder();
            }
        });

        orderVm.getSubOrder();
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

    @OnClick(R.id.iv_bar_back)
    void onClick() {
        finish();
    }

    @Override
    public void onEmptyClick() {
        ActivityUtils.startActivity(ProjectInfoListActivity.class);
    }
}
