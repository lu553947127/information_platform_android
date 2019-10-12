package com.shuangduan.zcy.view.demand;

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
import com.shuangduan.zcy.adapter.DemandAcceptAdapter;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.base.BaseLazyFragment;
import com.shuangduan.zcy.factory.EmptyViewFactory;
import com.shuangduan.zcy.model.bean.DemandRelationshipBean;
import com.shuangduan.zcy.view.release.ReleaseListActivity;
import com.shuangduan.zcy.vm.DemandRelationshipVm;
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
public class DemandMineAcceptFragment extends BaseLazyFragment implements EmptyViewFactory.EmptyViewCallBack {
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;
    private DemandRelationshipVm demandRelationshipVm;

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
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {

        View emptyView = createEmptyView(R.drawable.icon_empty_project, R.string.empty_mine_order_info, R.string.pull_strings, this);

        rv.setLayoutManager(new LinearLayoutManager(mContext));
        rv.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL_LIST, R.drawable.divider_15));
        DemandAcceptAdapter acceptAdapter = new DemandAcceptAdapter(R.layout.item_demand_relationship_release, null);
        acceptAdapter.setEmptyView(R.layout.layout_loading, rv);
        rv.setAdapter(acceptAdapter);
        acceptAdapter.setOnItemClickListener((adapter, view, position) -> {
            DemandRelationshipBean.ListBean listBean = acceptAdapter.getData().get(position);
            Bundle bundle = new Bundle();
            bundle.putInt(CustomConfig.DEMAND_ID, listBean.getId());
            ActivityUtils.startActivity(bundle, FindRelationshipAcceptDetailActivity.class);
        });

        demandRelationshipVm = ViewModelProviders.of(mActivity).get(DemandRelationshipVm.class);
        demandRelationshipVm.acceptLiveData.observe(this, demandRelationshipBean -> {
            isInited = true;
            if (demandRelationshipBean.getPage() == 1) {
                acceptAdapter.setNewData(demandRelationshipBean.getList());
                acceptAdapter.setEmptyView(emptyView);
            } else {
                acceptAdapter.addData(demandRelationshipBean.getList());
            }
            setNoMore(demandRelationshipBean.getPage(), demandRelationshipBean.getCount());
        });

        refresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                demandRelationshipVm.getMoreAcceptRelationship();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                demandRelationshipVm.getAcceptRelationship();
            }
        });
    }

    @Override
    protected void initDataFromService() {
        demandRelationshipVm.getAcceptRelationship();
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
        ActivityUtils.startActivity(DemandActivity.class);
    }
}
