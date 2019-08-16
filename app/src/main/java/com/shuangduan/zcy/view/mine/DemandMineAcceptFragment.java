package com.shuangduan.zcy.view.mine;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.ProjectOrderAdapter;
import com.shuangduan.zcy.base.BaseLazyFragment;
import com.shuangduan.zcy.vm.OrderVm;
import com.shuangduan.zcy.weight.DividerItemDecoration;

import butterknife.BindView;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view.mine
 * @class describe  我的需求，找关系, 我接受的
 * @time 2019/8/12 16:49
 * @change
 * @chang time
 * @class describe
 */
public class DemandMineAcceptFragment extends BaseLazyFragment {
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;
    private OrderVm orderVm;

    public static DemandMineAcceptFragment newInstance() {

        Bundle args = new Bundle();

        DemandMineAcceptFragment fragment = new DemandMineAcceptFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_project_info;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        rv.setLayoutManager(new LinearLayoutManager(mContext));
        rv.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL_LIST, R.drawable.divider_15));
        ProjectOrderAdapter adapter = new ProjectOrderAdapter(R.layout.item_order_locus, null);
        adapter.setEmptyView(R.layout.layout_loading, rv);
        rv.setAdapter(adapter);

        orderVm = ViewModelProviders.of(mActivity).get(OrderVm.class);
        orderVm.locusLiveData.observe(this, orderListBean -> {
            isInited = true;
            if (orderListBean.getPage() == 1) {
                adapter.setNewData(orderListBean.getList());
                adapter.setEmptyView(R.layout.layout_empty, rv);
            }else {
                adapter.addData(orderListBean.getList());
            }
            setNoMore(orderListBean.getPage(), orderListBean.getCount());
        });

        refresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                orderVm.getMoreLocusOrder();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                orderVm.getLocusOrder();
            }
        });
    }

    @Override
    protected void initDataFromService() {
        orderVm.getLocusOrder();
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
}
