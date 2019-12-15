package com.shuangduan.zcy.view.mine.demand;

import android.os.Bundle;
import android.view.View;

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
import com.shuangduan.zcy.adapter.DemandMineBuyerAdapter;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseLazyFragment;
import com.shuangduan.zcy.factory.EmptyViewFactory;
import com.shuangduan.zcy.model.bean.DemandBuyerBean;
import com.shuangduan.zcy.view.demand.DemandReleaseActivity;
import com.shuangduan.zcy.vm.DemandBuyerVm;
import com.shuangduan.zcy.weight.DividerItemDecoration;

import butterknife.BindView;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view.mine
 * @class 我的需求，找买家
 * @time 2019/8/13 10:42
 * @change
 * @chang time
 * @class describe
 */
public class FindMineBuyerFragment extends BaseLazyFragment implements EmptyViewFactory.EmptyViewCallBack {
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;
    private DemandBuyerVm demandBuyerVm;

    public static FindMineBuyerFragment newInstance() {
        Bundle args = new Bundle();
        FindMineBuyerFragment fragment = new FindMineBuyerFragment();
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
        View emptyView = createEmptyView(R.drawable.icon_empty_project, R.string.empty_mine_buyer_info, R.string.go_release, this);
        rv.setLayoutManager(new LinearLayoutManager(mContext));
        rv.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL_LIST, R.drawable.divider_15));
        DemandMineBuyerAdapter buyerAdapter = new DemandMineBuyerAdapter(R.layout.item_demand_buyer_need, null);
        buyerAdapter.setEmptyView(R.layout.layout_loading, rv);
        rv.setAdapter(buyerAdapter);
        buyerAdapter.setOnItemClickListener((adapter1, view, position) -> {
            DemandBuyerBean.ListBean listBean = buyerAdapter.getData().get(position);
            Bundle bundle = new Bundle();
            bundle.putInt(CustomConfig.DEMAND_ID, listBean.getId());
            bundle.putInt("type",1);
            ActivityUtils.startActivity(bundle, FindBuyerDetailActivity.class);
        });

        demandBuyerVm = ViewModelProviders.of(mActivity).get(DemandBuyerVm.class);
        demandBuyerVm.isMy = 1;
        demandBuyerVm.buyerLiveData.observe(this, demandBuyerBean -> {
            isInited = true;
            if (demandBuyerBean.getPage() == 1) {
                buyerAdapter.setNewData(demandBuyerBean.getList());
                buyerAdapter.setEmptyView(emptyView);
            } else {
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

    @Override
    public void onEmptyClick() {
        Bundle bundle = new Bundle();
        bundle.putString("type", "2");
        ActivityUtils.startActivity(bundle, DemandReleaseActivity.class);
    }
}
