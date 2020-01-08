package com.shuangduan.zcy.view.mine.demand;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.FindMineNeedAdapter;
import com.shuangduan.zcy.base.BaseLazyFragment;
import com.shuangduan.zcy.factory.EmptyViewFactory;
import com.shuangduan.zcy.weight.DividerItemDecoration;

import butterknife.BindView;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.view.mine.demand
 * @ClassName: FindMineLogisticsFragment
 * @Description: 我的需求，找物流
 * @Author: 鹿鸿祥
 * @CreateDate: 2020/1/8 16:47
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2020/1/8 16:47
 * @UpdateRemark: 更新说明
 * @Version: 1.1.0
 */
public class FindMineLogisticsFragment extends BaseLazyFragment implements EmptyViewFactory.EmptyViewCallBack {

    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;
    private FindMineNeedAdapter mAdapter;

    public static FindMineLogisticsFragment newInstance() {
        Bundle args = new Bundle();
        FindMineLogisticsFragment fragment = new FindMineLogisticsFragment();
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
        View emptyView = createEmptyView(R.drawable.icon_empty_project, R.string.empty_mine_logistics_info, R.string.go_release, this);

        rv.setLayoutManager(new LinearLayoutManager(mContext));
        rv.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL_LIST, R.drawable.divider_15));
        mAdapter = new FindMineNeedAdapter(R.layout.item_demand_substance_need, null);
//        mAdapter.setEmptyView(R.layout.layout_loading, rv);
        mAdapter.setEmptyView(emptyView);
        rv.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener((adapter, view, position) -> {

        });

        refresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {

            }
        });
    }

    @Override
    protected void initDataFromService() {

    }

    @Override
    public void onEmptyClick() {

    }
}
