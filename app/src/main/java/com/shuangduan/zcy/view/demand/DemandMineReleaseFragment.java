package com.shuangduan.zcy.view.demand;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.DemandReleaseAdapter;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseLazyFragment;
import com.shuangduan.zcy.model.bean.DemandRelationshipBean;
import com.shuangduan.zcy.vm.DemandRelationshipVm;
import com.shuangduan.zcy.weight.DividerItemDecoration;

import butterknife.BindView;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view.mine
 * @class describe  我的需求，找关系, 我发布的
 * @time 2019/8/12 16:49
 * @change
 * @chang time
 * @class describe
 */
public class DemandMineReleaseFragment extends BaseLazyFragment {
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;
    private DemandRelationshipVm demandRelationshipVm;

    public static DemandMineReleaseFragment newInstance() {

        Bundle args = new Bundle();

        DemandMineReleaseFragment fragment = new DemandMineReleaseFragment();
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
        rv.setLayoutManager(new LinearLayoutManager(mContext));
        rv.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL_LIST, R.drawable.divider_15));
        DemandReleaseAdapter releaseAdapter = new DemandReleaseAdapter(R.layout.item_demand_relationship_release, null);
        releaseAdapter.setEmptyView(R.layout.layout_loading, rv);
        rv.setAdapter(releaseAdapter);
        releaseAdapter.setOnItemClickListener((adapter, view, position) -> {
            DemandRelationshipBean.ListBean listBean = releaseAdapter.getData().get(position);
            Bundle bundle = new Bundle();
            bundle.putInt(CustomConfig.DEMAND_ID, listBean.getId());
            ActivityUtils.startActivity(bundle, FindRelationshipReleaseDetailActivity.class);
        });

        demandRelationshipVm = ViewModelProviders.of(mActivity).get(DemandRelationshipVm.class);
        demandRelationshipVm.releaseLiveData.observe(this, demandRelationshipBean -> {
            isInited = true;
            if (demandRelationshipBean.getPage() == 1) {
                releaseAdapter.setNewData(demandRelationshipBean.getList());
                releaseAdapter.setEmptyView(R.layout.layout_empty, rv);
            }else {
                releaseAdapter.addData(demandRelationshipBean.getList());
            }
            setNoMore(demandRelationshipBean.getPage(), demandRelationshipBean.getCount());
        });

        refresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                demandRelationshipVm.getMoreReleaseRelationship();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                demandRelationshipVm.getReleaseRelationship();
            }
        });
    }

    @Override
    protected void initDataFromService() {
        demandRelationshipVm.getReleaseRelationship();
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
