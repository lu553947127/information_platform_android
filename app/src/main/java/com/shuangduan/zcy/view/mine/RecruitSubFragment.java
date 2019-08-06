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
import com.shuangduan.zcy.adapter.ProjectSubAdapter;
import com.shuangduan.zcy.adapter.RecruitAdapter;
import com.shuangduan.zcy.adapter.RecruitSubAdapter;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseLazyFragment;
import com.shuangduan.zcy.model.bean.ProjectSubBean;
import com.shuangduan.zcy.model.bean.RecruitBean;
import com.shuangduan.zcy.model.bean.RecruitSubBean;
import com.shuangduan.zcy.view.projectinfo.ProjectDetailActivity;
import com.shuangduan.zcy.view.recruit.RecruitDetailActivity;
import com.shuangduan.zcy.vm.MineSubVm;
import com.shuangduan.zcy.weight.DividerItemDecoration;

import butterknife.BindView;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view.mine
 * @class describe  招采信息，订阅列表
 * @time 2019/8/2 17:55
 * @change
 * @chang time
 * @class describe
 */
public class RecruitSubFragment extends BaseLazyFragment {
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;
    private MineSubVm mineSubVm;

    public static RecruitSubFragment newInstance() {

        Bundle args = new Bundle();

        RecruitSubFragment fragment = new RecruitSubFragment();
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
        RecruitSubAdapter recruitAdapter = new RecruitSubAdapter(R.layout.item_recruit, null);
        recruitAdapter.setEmptyView(R.layout.layout_loading, rv);
        rv.setAdapter(recruitAdapter);
        recruitAdapter.setOnItemClickListener((adapter, view, position) -> {
            RecruitSubBean.ListBean listBean = recruitAdapter.getData().get(position);
            Bundle bundle = new Bundle();
            bundle.putInt(CustomConfig.RECRUIT_ID, listBean.getType_id());
            ActivityUtils.startActivity(bundle, RecruitDetailActivity.class);
        });

        mineSubVm = ViewModelProviders.of(this).get(MineSubVm.class);
        mineSubVm.myRecruit();
        mineSubVm.recruitLiveData.observe(this, recruitSubBean -> {
            if (recruitSubBean.getPage() == 1) {
                recruitAdapter.setNewData(recruitSubBean.getList());
                recruitAdapter.setEmptyView(R.layout.layout_empty, rv);
            }else {
                recruitAdapter.addData(recruitSubBean.getList());
            }
            setNoMore(recruitSubBean.getPage(), recruitSubBean.getCount());
        });

        refresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mineSubVm.refreshMyRecruit();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mineSubVm.myRecruit();
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
