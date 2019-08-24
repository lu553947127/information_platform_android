package com.shuangduan.zcy.view.release;

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
import com.shuangduan.zcy.adapter.LocusMineAdapter;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseLazyFragment;
import com.shuangduan.zcy.model.bean.LocusMineBean;
import com.shuangduan.zcy.model.event.LocusRefreshEvent;
import com.shuangduan.zcy.view.projectinfo.ProjectDetailActivity;
import com.shuangduan.zcy.vm.MineReleaseVm;
import com.shuangduan.zcy.weight.DividerItemDecoration;

import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view.release
 * @class describe  我发布的轨迹信息
 * @time 2019/7/16 20:24
 * @change
 * @chang time
 * @class describe
 */
public class LocusFragment extends BaseLazyFragment {

    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;
    private MineReleaseVm mineReleaseVm;

    public static LocusFragment newInstance() {

        Bundle args = new Bundle();

        LocusFragment fragment = new LocusFragment();
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
        LocusMineAdapter adapter = new LocusMineAdapter(R.layout.item_mine_locus, null);
        adapter.setEmptyView(R.layout.layout_loading, rv);
        rv.setAdapter(adapter);
        adapter.setOnItemClickListener((helper, view, position) -> {
            LocusMineBean.ListBean listBean = adapter.getData().get(position);
            Bundle bundle = new Bundle();
            bundle.putInt(CustomConfig.PROJECT_ID, listBean.getId());
            bundle.putInt(CustomConfig.LOCATION, 1);
            ActivityUtils.startActivity(bundle, ProjectDetailActivity.class);
        });

        mineReleaseVm = ViewModelProviders.of(this).get(MineReleaseVm.class);
        mineReleaseVm.trackLiveData.observe(this, locusMineBean -> {
            isInited = true;
            if (locusMineBean.getPage() == 1) {
                adapter.setNewData(locusMineBean.getList());
                adapter.setEmptyView(R.layout.layout_empty, rv);
            }else {
                adapter.addData(locusMineBean.getList());
            }
            setNoMore(locusMineBean.getPage(), locusMineBean.getCount());
        });

        refresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mineReleaseVm.projectPage++;
                mineReleaseVm.moreMyProjectTract();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mineReleaseVm.myProjectTract();
            }
        });
    }

    @Override
    protected void initDataFromService() {
        mineReleaseVm.myProjectTract();
    }

    @Subscribe()
    public void releaseSuccess(LocusRefreshEvent event){
        mineReleaseVm.myProjectTract();
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
