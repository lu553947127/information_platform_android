package com.shuangduan.zcy.view.headlines;

import android.os.Bundle;

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
import com.shuangduan.zcy.adapter.HeadlinesListAdapter;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseLazyFragment;
import com.shuangduan.zcy.model.bean.HeadlinesBean;
import com.shuangduan.zcy.vm.HeadlinesVm;
import com.shuangduan.zcy.weight.DividerItemDecoration;

import java.util.Objects;

import butterknife.BindView;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.view.headlines
 * @ClassName: HeadlinesFragment
 * @Description: java类作用描述
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/11/21 9:23
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/11/21 9:23
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class HeadlinesFragment extends BaseLazyFragment {
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;
    private int typeId;
    private HeadlinesVm headlinesVm;

    HeadlinesFragment(int id) {
        typeId=id;
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_head_lines;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getActivity()), DividerItemDecoration.VERTICAL_LIST, R.drawable.divider_15));
        HeadlinesListAdapter headlinesCategoryAdapter = new HeadlinesListAdapter(R.layout.item_headlines_action, null);
        headlinesCategoryAdapter.setEmptyView(R.layout.layout_loading, rv);
        rv.setAdapter(headlinesCategoryAdapter);
        headlinesCategoryAdapter.setOnItemClickListener((adapter, view, position) -> {
            HeadlinesBean.ListBean listBean = headlinesCategoryAdapter.getData().get(position);
            Bundle bundle = new Bundle();
            bundle.putInt(CustomConfig.HEADLINES_ID, listBean.getId());
            ActivityUtils.startActivity(bundle, HeadlinesDetailActivity.class);
        });

        headlinesVm = mActivity.getViewModel(HeadlinesVm.class);
        headlinesVm.headlinesLiveData.observe(this, headlinesBean -> {
            if (headlinesBean.getPage() == 1) {
                headlinesCategoryAdapter.setNewData(headlinesBean.getList());
                headlinesCategoryAdapter.setEmptyView(R.layout.layout_empty, rv);
            }else {
                headlinesCategoryAdapter.addData(headlinesBean.getList());
            }
            setNoMore(headlinesBean.getPage(), headlinesBean.getCount());
        });

        refresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                headlinesVm.getMoreHeadlines();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                headlinesVm.getHeadlines();
            }
        });

    }

    @Override
    protected void initDataFromService() {
        headlinesVm.category_id=typeId;
        headlinesVm.getHeadlines();
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
