package com.shuangduan.zcy.view.mine;

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
import com.shuangduan.zcy.adapter.RecruitAdapter;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseLazyFragment;
import com.shuangduan.zcy.model.bean.RecruitBean;
import com.shuangduan.zcy.view.recruit.RecruitDetailActivity;
import com.shuangduan.zcy.vm.MineCollectionVm;
import com.shuangduan.zcy.weight.DividerItemDecoration;

import butterknife.BindView;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view.mine
 * @class describe
 * @time 2019/8/5 13:39
 * @change
 * @chang time
 * @class describe
 */
public class RecruitCollectFragment extends BaseLazyFragment {
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;
    private MineCollectionVm mineCollectionVm;

    public static RecruitCollectFragment newInstance() {

        Bundle args = new Bundle();

        RecruitCollectFragment fragment = new RecruitCollectFragment();
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
        RecruitAdapter recruitAdapter = new RecruitAdapter(R.layout.item_recruit, null);
        recruitAdapter.setEmptyView(R.layout.layout_loading, rv);
        rv.setAdapter(recruitAdapter);
        recruitAdapter.setOnItemClickListener((adapter, view, position) -> {
            RecruitBean.ListBean listBean = recruitAdapter.getData().get(position);
            Bundle bundle = new Bundle();
            bundle.putInt(CustomConfig.RECRUIT_ID, listBean.getId());
            ActivityUtils.startActivity(bundle, RecruitDetailActivity.class);
        });

        mineCollectionVm = ViewModelProviders.of(this).get(MineCollectionVm.class);
        mineCollectionVm.recruitCollectLiveData.observe(this, recruitBean -> {
            isInited = true;
            if (recruitBean.getPage() == 1) {
                recruitAdapter.setNewData(recruitBean.getList());
                recruitAdapter.setEmptyView(R.layout.layout_empty_top, rv);
            }else {
                recruitAdapter.addData(recruitBean.getList());
            }
            setNoMore(recruitBean.getPage(), recruitBean.getCount());
        });

        refresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mineCollectionVm.moreRecruitCollection();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mineCollectionVm.recruitCollection();
            }
        });
    }

    @Override
    protected void initDataFromService() {
        mineCollectionVm.recruitCollection();
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
