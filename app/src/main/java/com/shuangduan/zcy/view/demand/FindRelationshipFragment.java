package com.shuangduan.zcy.view.demand;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.shuangduan.zcy.adapter.FindRelationshipFragmentAdapter;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseLazyFragment;
import com.shuangduan.zcy.model.bean.DemandRelationshipBean;
import com.shuangduan.zcy.utils.AnimationUtils;
import com.shuangduan.zcy.vm.DemandRelationshipVm;
import com.shuangduan.zcy.weight.DividerItemDecoration;

import java.util.Objects;

import butterknife.BindView;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view.mine
 * @class 需求资讯-找资源
 * @time 2019/8/13 10:42
 * @change
 * @chang time
 * @class describe
 */
public class FindRelationshipFragment extends BaseLazyFragment {
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;
    private ImageView ivRelease;
    private DemandRelationshipVm demandRelationshipVm;

    public static FindRelationshipFragment newInstance() {
        Bundle args = new Bundle();
        FindRelationshipFragment fragment = new FindRelationshipFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_order_recruit;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
//        TextView tvBarTitle = Objects.requireNonNull(getActivity()).findViewById(R.id.tv_bar_title);
//        tvBarTitle.setText(R.string.find_relationship);

        View emptyView = createEmptyView(R.drawable.icon_empty_project, R.string.empty_pull_strings_info, 0, null);

        rv.setLayoutManager(new LinearLayoutManager(mContext));
        rv.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL_LIST, R.drawable.divider_15));
        FindRelationshipFragmentAdapter relationshipAdapter = new FindRelationshipFragmentAdapter(R.layout.item_demand_relationship, null);
        relationshipAdapter.setEmptyView(R.layout.layout_loading, rv);
        rv.setAdapter(relationshipAdapter);
        relationshipAdapter.setOnItemClickListener((adapter, view, position) -> {
            DemandRelationshipBean.ListBean listBean = relationshipAdapter.getData().get(position);
            Bundle bundle = new Bundle();
            bundle.putInt(CustomConfig.DEMAND_ID, listBean.getId());
            ActivityUtils.startActivity(bundle, FindRelationshipDetailActivity.class);
        });

        demandRelationshipVm = mActivity.getViewModel(DemandRelationshipVm.class);
        demandRelationshipVm.relationshipLiveData.observe(this, relationshipBean -> {
            isInited = true;
            if (relationshipBean.getPage() == 1) {
                relationshipAdapter.setNewData(relationshipBean.getList());
                relationshipAdapter.setEmptyView(emptyView);
            } else {
                relationshipAdapter.addData(relationshipBean.getList());
            }
            setNoMore(relationshipBean.getPage(), relationshipBean.getCount());
        });

        refresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                demandRelationshipVm.getMoreRelationship();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                demandRelationshipVm.getRelationship();
            }
        });

//        ivRelease = (ImageView) mActivity.findViewById(R.id.iv_release);
//        //滑动监听
//        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                AnimationUtils.listScrollAnimation(ivRelease,dy);
//            }
//        });
    }

    @Override
    protected void initDataFromService() {
        demandRelationshipVm.getRelationship();
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
