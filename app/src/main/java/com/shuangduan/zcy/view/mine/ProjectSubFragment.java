package com.shuangduan.zcy.view.mine;

import android.os.Bundle;
import android.view.View;

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
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseFragment;
import com.shuangduan.zcy.model.bean.ProjectSubBean;
import com.shuangduan.zcy.view.projectinfo.ProjectDetailActivity;
import com.shuangduan.zcy.vm.MineSubVm;
import com.shuangduan.zcy.weight.DividerItemDecoration;

import butterknife.BindView;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view.mine
 * @class describe
 * @time 2019/8/5 8:47
 * @change
 * @chang time
 * @class describe
 */
public class ProjectSubFragment extends BaseFragment {
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;
    private MineSubVm mineSubVm;

    public static ProjectSubFragment newInstance() {

        Bundle args = new Bundle();

        ProjectSubFragment fragment = new ProjectSubFragment();
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
    protected void initDataAndEvent(Bundle savedInstanceState, View v) {

        View emptyView = createEmptyView(R.drawable.icon_empty_subscibe, R.string.empty_project_subscribe_info, 0, null);

        rv.setLayoutManager(new LinearLayoutManager(mContext));
        rv.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL_LIST, R.drawable.divider_15));
        ProjectSubAdapter adapter = new ProjectSubAdapter(R.layout.item_mine_project, null);
        adapter.setEmptyView(R.layout.layout_loading, rv);
        rv.setAdapter(adapter);
        adapter.setOnItemClickListener((helper, view, position) -> {
            ProjectSubBean.ListBean listBean = adapter.getData().get(position);
            Bundle bundle = new Bundle();
            bundle.putInt(CustomConfig.PROJECT_ID, listBean.getType_id());
            bundle.putInt(CustomConfig.LOCATION, 0);
            ActivityUtils.startActivity(bundle, ProjectDetailActivity.class);
        });

        mineSubVm = ViewModelProviders.of(this).get(MineSubVm.class);
        mineSubVm.myProject();
        mineSubVm.projectLiveData.observe(this, projectMineBean -> {
            if (projectMineBean.getPage() == 1) {
                adapter.setNewData(projectMineBean.getList());
                adapter.setEmptyView(emptyView);
            }else {
                adapter.addData(projectMineBean.getList());
            }
            setNoMore(projectMineBean.getPage(), projectMineBean.getCount());
        });

        refresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mineSubVm.moreMyProject();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mineSubVm.myProject();
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
