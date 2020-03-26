package com.shuangduan.zcy.view.mine.collection;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
;
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
import com.shuangduan.zcy.factory.EmptyViewFactory;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.model.bean.RecruitBean;
import com.shuangduan.zcy.view.recruit.RecruitActivity;
import com.shuangduan.zcy.view.recruit.RecruitDetailActivity;
import com.shuangduan.zcy.vm.MineCollectionVm;
import com.shuangduan.zcy.weight.DividerItemDecoration;

import butterknife.BindView;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view.mine
 * @class 我的收藏-招采信息
 * @time 2019/8/5 13:39
 * @change
 * @chang time
 * @class describe
 */
public class RecruitCollectFragment extends BaseLazyFragment implements EmptyViewFactory.EmptyViewCallBack {
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
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        View emptyView = createEmptyView(R.drawable.icon_empty_project, R.string.empty_recruit_collect_info, R.string.to_look_over, this);

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

        mineCollectionVm = mActivity.getViewModel(MineCollectionVm.class);
        mineCollectionVm.recruitCollectLiveData.observe(this, recruitBean -> {
            if (recruitBean.getPage() == 1) {
                recruitAdapter.setNewData(recruitBean.getList());
                recruitAdapter.setEmptyView(emptyView);
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

        mineCollectionVm.pageStateLiveData.observe(this, s -> {
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

    @Override
    public void onEmptyClick() {
        ActivityUtils.startActivity(RecruitActivity.class);
    }
}
