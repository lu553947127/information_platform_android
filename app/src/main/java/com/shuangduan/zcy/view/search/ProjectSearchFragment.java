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
import com.shuangduan.zcy.adapter.ProjectInfoAdapter;
import com.shuangduan.zcy.adapter.ProjectInfoSearchAdapter;
import com.shuangduan.zcy.adapter.RecruitSubAdapter;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseLazyFragment;
import com.shuangduan.zcy.model.bean.ProjectInfoBean;
import com.shuangduan.zcy.model.bean.RecruitSubBean;
import com.shuangduan.zcy.view.projectinfo.ProjectDetailActivity;
import com.shuangduan.zcy.view.recruit.RecruitDetailActivity;
import com.shuangduan.zcy.vm.MineSubVm;
import com.shuangduan.zcy.vm.SearchVm;
import com.shuangduan.zcy.weight.DividerItemDecoration;

import butterknife.BindView;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view.search
 * @class describe
 * @time 2019/8/15 13:49
 * @change
 * @chang time
 * @class describe
 */
public class ProjectSearchFragment extends BaseLazyFragment {
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;
    private SearchVm searchVm;

    public static ProjectSearchFragment newInstance() {

        Bundle args = new Bundle();

        ProjectSearchFragment fragment = new ProjectSearchFragment();
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
        ProjectInfoSearchAdapter projectInfoAdapter = new ProjectInfoSearchAdapter(R.layout.item_project_info, null);
        projectInfoAdapter.setEmptyView(R.layout.layout_loading, rv);
        rv.setAdapter(projectInfoAdapter);
        projectInfoAdapter.setOnItemClickListener((adapter, view, position) -> {
            ProjectInfoBean.ListBean listBean = projectInfoAdapter.getData().get(position);
            Bundle bundle = new Bundle();
            bundle.putInt(CustomConfig.PROJECT_ID, listBean.getId());
            ActivityUtils.startActivity(bundle, ProjectDetailActivity.class);
        });

        searchVm = ViewModelProviders.of(mActivity).get(SearchVm.class);
        searchVm.searchProjectLiveData.observe(this, searchProjectBean -> {
            projectInfoAdapter.setKeyword(searchVm.keyword);
            if (searchProjectBean.getPage() == 1) {
                projectInfoAdapter.setNewData(searchProjectBean.getList());
                projectInfoAdapter.setEmptyView(R.layout.layout_empty, rv);
            }else {
                projectInfoAdapter.addData(searchProjectBean.getList());
            }
            setNoMore(searchProjectBean.getPage(), searchProjectBean.getCount());
        });
        refresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                searchVm.searchMoreProject();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                searchVm.searchProject();
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
