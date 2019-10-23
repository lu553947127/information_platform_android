package com.shuangduan.zcy.view.release;

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
import com.shuangduan.zcy.adapter.ProjectMineAdapter;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseLazyFragment;
import com.shuangduan.zcy.model.bean.ProjectMineBean;
import com.shuangduan.zcy.model.event.ProjectReleaseEvent;
import com.shuangduan.zcy.view.mine.MineProjectActivity;
import com.shuangduan.zcy.vm.MineReleaseVm;
import com.shuangduan.zcy.weight.DividerItemDecoration;

import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view.release
 * @class describe  我发布的工程信息列表
 * @time 2019/7/16 20:22
 * @change
 * @chang time
 * @class describe
 */
public class ProjectInfoFragment extends BaseLazyFragment{

    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;
    private MineReleaseVm mineReleaseVm;

    public static ProjectInfoFragment newInstance() {
        Bundle args = new Bundle();
        ProjectInfoFragment fragment = new ProjectInfoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_project_info;
    }

    @Override
    public boolean isUseEventBus() {
        return true;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        View emptyView = createEmptyView(R.drawable.icon_empty_project, R.string.empty_release_project_info, 0, null);

        rv.setLayoutManager(new LinearLayoutManager(mContext));
        rv.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL_LIST, R.drawable.divider_15));
        ProjectMineAdapter adapter = new ProjectMineAdapter(R.layout.item_mine_project, null);
        adapter.setEmptyView(R.layout.layout_loading, rv);
        rv.setAdapter(adapter);
        adapter.setOnItemClickListener((helper, view, position) -> {
            ProjectMineBean.ListBean listBean = adapter.getData().get(position);
            Bundle bundle = new Bundle();
            bundle.putInt(CustomConfig.PROJECT_ID, listBean.getId());
            bundle.putInt(CustomConfig.LOCATION, 0);
            ActivityUtils.startActivity(bundle, MineProjectActivity.class);
        });

        mineReleaseVm = ViewModelProviders.of(this).get(MineReleaseVm.class);
        mineReleaseVm.projectLiveData.observe(this, projectMineBean -> {
            isInited = true;
            if (projectMineBean.getPage() == 1) {
                adapter.setNewData(projectMineBean.getList());
                adapter.setEmptyView(emptyView);
            } else {
                adapter.addData(projectMineBean.getList());
            }
            setNoMore(projectMineBean.getPage(), projectMineBean.getCount());
        });

        refresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mineReleaseVm.moreMyProject();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mineReleaseVm.myProject();
            }
        });
    }

    @Override
    protected void initDataFromService() {
        mineReleaseVm.myProject();
    }

    @Subscribe
    public void onEventReleaseSuccess(ProjectReleaseEvent event) {
        mineReleaseVm.myProject();
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


}
