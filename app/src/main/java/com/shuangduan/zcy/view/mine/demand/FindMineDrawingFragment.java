package com.shuangduan.zcy.view.mine.demand;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.FindMineNeedAdapter;
import com.shuangduan.zcy.base.BaseLazyFragment;
import com.shuangduan.zcy.factory.EmptyViewFactory;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.view.demand.FindBluePrintActivity;
import com.shuangduan.zcy.vm.DemandReleaseVm;
import com.shuangduan.zcy.weight.DividerItemDecoration;

import butterknife.BindView;

;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.view.mine.demand
 * @ClassName: FindMineDrawingFragment
 * @Description: 我的需求，找图纸
 * @Author: 鹿鸿祥
 * @CreateDate: 2020/1/8 16:48
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2020/1/8 16:48
 * @UpdateRemark: 更新说明
 * @Version: 1.1.0
 */
public class FindMineDrawingFragment extends BaseLazyFragment implements EmptyViewFactory.EmptyViewCallBack {

    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;
    private FindMineNeedAdapter mAdapter;
    private DemandReleaseVm vm;

    public static FindMineDrawingFragment newInstance() {
        Bundle args = new Bundle();
        FindMineDrawingFragment fragment = new FindMineDrawingFragment();
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
        View emptyView = createEmptyView(R.drawable.icon_empty_project, R.string.empty_mine_blue_print_info, R.string.go_release, this);

        rv.setLayoutManager(new LinearLayoutManager(mContext));
        rv.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL_LIST, R.drawable.divider_15));
        mAdapter = new FindMineNeedAdapter(R.layout.item_demand_substance_need, null, 3);

        rv.setAdapter(mAdapter);


        vm = mActivity.getViewModel(DemandReleaseVm.class);


        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            Bundle bundle = new Bundle();
            bundle.putInt("id",mAdapter.getData().get(position).id);
            ActivityUtils.startActivity(bundle, FindBluePrintDetailActivity.class);
        });

        refresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                vm.moreDrawingList();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                vm.drawingList();
            }
        });

        vm.needLiveData.observe(this, result -> {

            if (result.page == 1) {
                mAdapter.setNewData(result.list);
                mAdapter.setEmptyView(emptyView);
            } else {
                mAdapter.addData(result.list);
            }

            setNoMore(result.page,result.count);
        });


        vm.pageStateLiveData.observe(this, s -> {
            switch (s) {
                case PageState.PAGE_LOADING:
                    showLoading();
                    break;
                default:
                    hideLoading();
                    break;
            }
        });
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
    protected void initDataFromService() {
        vm.drawingList();
    }

    @Override
    public void onEmptyClick() {
        ActivityUtils.startActivity(FindBluePrintActivity.class);
    }
}
