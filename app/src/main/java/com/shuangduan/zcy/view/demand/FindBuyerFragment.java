package com.shuangduan.zcy.view.demand;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.DemandBuyerAdapter;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseLazyFragment;
import com.shuangduan.zcy.model.bean.DemandBuyerBean;
import com.shuangduan.zcy.vm.DemandBuyerVm;
import com.shuangduan.zcy.weight.DividerItemDecoration;

import butterknife.BindView;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view.mine
 * @class describe  我的需求，找买家
 * @time 2019/8/13 10:42
 * @change
 * @chang time
 * @class describe
 */
public class FindBuyerFragment extends BaseLazyFragment {
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;
    private DemandBuyerVm demandBuyerVm;

    public static FindBuyerFragment newInstance() {

        Bundle args = new Bundle();

        FindBuyerFragment fragment = new FindBuyerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_order_recruit;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        rv.setLayoutManager(new LinearLayoutManager(mContext));
        rv.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL_LIST, R.drawable.divider_15));
        DemandBuyerAdapter buyerAdapter = new DemandBuyerAdapter(R.layout.item_demand_buyer, null);
        buyerAdapter.setEmptyView(R.layout.layout_loading, rv);
        rv.setAdapter(buyerAdapter);
        buyerAdapter.setOnItemClickListener((adapter1, view, position) -> {
            DemandBuyerBean.ListBean listBean = buyerAdapter.getData().get(position);
            Bundle bundle = new Bundle();
            bundle.putInt(CustomConfig.DEMAND_ID, listBean.getId());
            ActivityUtils.startActivity(bundle, FindBuyerDetailActivity.class);
        });

        demandBuyerVm = ViewModelProviders.of(mActivity).get(DemandBuyerVm.class);
        demandBuyerVm.buyerLiveData.observe(this, demandBuyerBean -> {
            isInited = true;
            if (demandBuyerBean.getPage() == 1) {
                buyerAdapter.setNewData(demandBuyerBean.getList());
                buyerAdapter.setEmptyView(R.layout.layout_empty, rv);
            }else {
                buyerAdapter.addData(demandBuyerBean.getList());
            }
            setNoMore(demandBuyerBean.getPage(), demandBuyerBean.getCount());
        });

        refresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                demandBuyerVm.getMoreBuyer();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                demandBuyerVm.getBuyer();
            }
        });
    }

    @Override
    protected void initDataFromService() {
        demandBuyerVm.getBuyer();
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
