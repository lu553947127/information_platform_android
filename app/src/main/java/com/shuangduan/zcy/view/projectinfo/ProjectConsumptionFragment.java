package com.shuangduan.zcy.view.projectinfo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.ConsumptionAdapter;
import com.shuangduan.zcy.base.BaseLazyFragment;
import com.shuangduan.zcy.vm.ProjectDetailVm;
import com.shuangduan.zcy.weight.DividerItemDecoration;

import butterknife.BindView;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view.projectinfo
 * @class describe  消费列表
 * @time 2019/7/15 15:36
 * @change
 * @chang time
 * @class describe
 */
public class ProjectConsumptionFragment extends BaseLazyFragment {
    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;
    @BindView(R.id.rv_consumption)
    RecyclerView rvConsumption;
    private ProjectDetailVm projectDetailVm;
    private ConsumptionAdapter consumptionAdapter;

    public static ProjectConsumptionFragment newInstance() {

        Bundle args = new Bundle();

        ProjectConsumptionFragment fragment = new ProjectConsumptionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_consumption;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        rvConsumption.setLayoutManager(new LinearLayoutManager(mContext));
        rvConsumption.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL_LIST, R.drawable.divider_15));
        consumptionAdapter = new ConsumptionAdapter(R.layout.item_consumption, null);
        consumptionAdapter.setEmptyView(R.layout.layout_loading_top, rvConsumption);
        rvConsumption.setAdapter(consumptionAdapter);

        refresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                projectDetailVm.getMoreConsume();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                projectDetailVm.getConsume();
            }
        });

        projectDetailVm = ViewModelProviders.of(mActivity).get(ProjectDetailVm.class);
        projectDetailVm.consumeLiveData.observe(this, consumeBean -> {
            isInited = true;
            if (consumeBean.getPage() == 1) {
                consumptionAdapter.setNewData(consumeBean.getList());
                setEmpty(consumptionAdapter);
            }else {
                consumptionAdapter.addData(consumeBean.getList());
            }
            setNoMore(consumeBean.getPage(), consumeBean.getCount());
        });
    }

    @Override
    protected void initDataFromService() {
        projectDetailVm.getConsume();
    }

    private void setEmpty(ConsumptionAdapter consumptionAdapter) {
        View empty = LayoutInflater.from(mContext).inflate(R.layout.layout_empty_top, null);
        TextView tvTip = empty.findViewById(R.id.tv_tip);
        tvTip.setText(getString(R.string.empty_consume));
        consumptionAdapter.setEmptyView(empty);
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
