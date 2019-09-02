package com.shuangduan.zcy.view.search;

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
import com.shuangduan.zcy.adapter.RecruitSearchAdapter;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseLazyFragment;
import com.shuangduan.zcy.model.bean.RecruitBean;
import com.shuangduan.zcy.view.recruit.RecruitDetailActivity;
import com.shuangduan.zcy.vm.SearchVm;
import com.shuangduan.zcy.weight.DividerItemDecoration;

import butterknife.BindView;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view.search
 * @class describe
 * @time 2019/8/15 13:50
 * @change
 * @chang time
 * @class describe
 */
public class RecruitSearchFragment extends BaseLazyFragment {
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;
    private SearchVm searchVm;

    public static RecruitSearchFragment newInstance() {

        Bundle args = new Bundle();

        RecruitSearchFragment fragment = new RecruitSearchFragment();
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
        RecruitSearchAdapter recruitAdapter = new RecruitSearchAdapter(R.layout.item_recruit, null);
        recruitAdapter.setEmptyView(R.layout.layout_loading, rv);
        rv.setAdapter(recruitAdapter);
        recruitAdapter.setOnItemClickListener((adapter, view, position) -> {
            RecruitBean.ListBean listBean = recruitAdapter.getData().get(position);
            Bundle bundle = new Bundle();
            bundle.putInt(CustomConfig.RECRUIT_ID, listBean.getId());
            ActivityUtils.startActivity(bundle, RecruitDetailActivity.class);
        });

        searchVm = ViewModelProviders.of(mActivity).get(SearchVm.class);
        searchVm.searchRecruitLiveData.observe(this, recruitBean -> {
            recruitAdapter.setKeyword(searchVm.keyword);
            if (recruitBean.getPage() == 1) {
                recruitAdapter.setNewData(recruitBean.getList());
                recruitAdapter.setEmptyView(R.layout.layout_empty, rv);
            }else {
                recruitAdapter.addData(recruitBean.getList());
            }
            setNoMore(recruitBean.getPage(), recruitBean.getCount());
        });

        refresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                searchVm.searchMoreRecruit();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                searchVm.searchRecruit();
            }
        });
    }

    @Override
    protected void initDataFromService() {

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
