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
import com.shuangduan.zcy.adapter.FindRelationshipAdapter;
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
 * @class describe  需求广场，找关系
 * @time 2019/8/13 10:42
 * @change
 * @chang time
 * @class describe
 */
public class FindRelationshipFragment extends BaseLazyFragment {
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;
    private DemandRelationshipVm demandRelationshipVm;

    public static FindRelationshipFragment newInstance() {

        Bundle args = new Bundle();

        FindRelationshipFragment fragment = new FindRelationshipFragment();
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
        FindRelationshipAdapter relationshipAdapter = new FindRelationshipAdapter(R.layout.item_demand_relationship, null);
        relationshipAdapter.setEmptyView(R.layout.layout_loading, rv);
        rv.setAdapter(relationshipAdapter);
        relationshipAdapter.setOnItemClickListener((adapter, view, position) -> {
            DemandRelationshipBean.ListBean listBean = relationshipAdapter.getData().get(position);
            Bundle bundle = new Bundle();
            bundle.putInt(CustomConfig.DEMAND_ID, listBean.getId());
            ActivityUtils.startActivity(bundle, FindRelationshipDetailActivity.class);
        });

        demandRelationshipVm = ViewModelProviders.of(mActivity).get(DemandRelationshipVm.class);
        demandRelationshipVm.relationshipLiveData.observe(this, relationshipBean -> {
            isInited = true;
            if (relationshipBean.getPage() == 1) {
                relationshipAdapter.setNewData(relationshipBean.getList());
                relationshipAdapter.setEmptyView(R.layout.layout_empty, rv);
            }else {
                relationshipAdapter.addData(relationshipBean.getList());
            }
            setNoMore(relationshipBean.getPage(), relationshipBean.getCount());
        });

        refresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                demandRelationshipVm.getMoreRelationship();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                demandRelationshipVm.getRelationship();
            }
        });
    }

    @Override
    protected void initDataFromService() {
        demandRelationshipVm.getRelationship();
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
