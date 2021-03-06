package com.shuangduan.zcy.view.mine.project;

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
import com.shuangduan.zcy.adapter.LocusMineAdapter;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseLazyFragment;
import com.shuangduan.zcy.model.bean.LocusMineBean;
import com.shuangduan.zcy.model.event.LocusRefreshEvent;
import com.shuangduan.zcy.vm.MineReleaseVm;
import com.shuangduan.zcy.weight.DividerItemDecoration;

import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view.release
 * @class 我的工程-动态信息
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
        View emptyView = createEmptyView(R.drawable.icon_empty_project, R.string.empty_release_dynamics_info, 0, null);

        rv.setLayoutManager(new LinearLayoutManager(mContext));
        rv.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL_LIST, R.drawable.divider_15));
        LocusMineAdapter adapter = new LocusMineAdapter(R.layout.item_mine_locus, null);
        adapter.setEmptyView(R.layout.layout_loading, rv);
        rv.setAdapter(adapter);
        adapter.setOnItemClickListener((helper, view, position) -> {
            LocusMineBean.ListBean listBean = adapter.getData().get(position);
            Bundle bundle = new Bundle();
            bundle.putInt(CustomConfig.DYNAMICS_ID, listBean.getId());
            ActivityUtils.startActivity(bundle, MineLocusActivity.class);
        });

        mineReleaseVm = mActivity.getViewModel(MineReleaseVm.class);
        mineReleaseVm.trackLiveData.observe(this, locusMineBean -> {
            isInited = true;
            if (locusMineBean.getPage() == 1) {
                adapter.setNewData(locusMineBean.getList());
                adapter.setEmptyView(emptyView);
            }else {
                adapter.addData(locusMineBean.getList());
            }
            setNoMore(locusMineBean.getPage(), locusMineBean.getCount());
        });

        refresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
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

    @Override
    public boolean isUseEventBus() {
        return true;
    }

    @Subscribe
    public void onEventReleaseSuccess(LocusRefreshEvent event){
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
