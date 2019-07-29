package com.shuangduan.zcy.view.release;

import android.os.Bundle;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.ProjectMineAdapter;
import com.shuangduan.zcy.base.BaseLazyFragment;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.model.bean.ProjectMineBean;
import com.shuangduan.zcy.vm.MineReleaseVm;
import com.shuangduan.zcy.weight.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view.release
 * @class describe  我发布的工程信息列表
 * @time 2019/7/16 20:22
 * @change
 * @chang time
 * @class describe
 */
public class ProjectInfoFragment extends BaseLazyFragment {

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
    protected void initDataAndEvent(Bundle savedInstanceState) {
        rv.setLayoutManager(new LinearLayoutManager(mContext));
        rv.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL_LIST, R.drawable.divider_15));
        ProjectMineAdapter adapter = new ProjectMineAdapter(R.layout.item_mine_project, null);
        rv.setAdapter(adapter);

        mineReleaseVm = ViewModelProviders.of(this).get(MineReleaseVm.class);
        mineReleaseVm.myProject();
        mineReleaseVm.projectLiveData.observe(this, projectMineBean -> {

        });
        mineReleaseVm.pageStateLiveData.observe(this, s -> {
            switch (s){
                case PageState.PAGE_LOADING:
                    showLoading();
                    break;
                    default:
                        refresh.finishRefresh();
                        refresh.finishLoadMore();
                        hideLoading();
                        break;
            }
        });
    }

    @Override
    protected void initDataFromService() {

    }
}
