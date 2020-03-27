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
import com.shuangduan.zcy.adapter.FindSubstanceFragmentAdapter;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseLazyFragment;
import com.shuangduan.zcy.model.bean.DemandSubstanceBean;
import com.shuangduan.zcy.utils.AnimationUtils;
import com.shuangduan.zcy.view.mine.demand.FindSubstanceDetailActivity;
import com.shuangduan.zcy.vm.DemandSubstanceVm;
import com.shuangduan.zcy.weight.DividerItemDecoration;

import java.util.Objects;

import butterknife.BindView;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view.mine
 * @class 需求资讯-找物资
 * @time 2019/8/13 10:42
 * @change
 * @chang time
 * @class describe
 */
public class FindSubstanceFragment extends BaseLazyFragment {
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;
    private ImageView ivRelease;
    private DemandSubstanceVm demandSubstanceVm;

    public static FindSubstanceFragment newInstance() {
        Bundle args = new Bundle();
        FindSubstanceFragment fragment = new FindSubstanceFragment();
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
//        tvBarTitle.setText(R.string.find_substance);

        View emptyView = createEmptyView(R.drawable.icon_empty_project, R.string.empty_substance_info, 0, null);


        rv.setLayoutManager(new LinearLayoutManager(mContext));
        rv.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL_LIST, R.drawable.divider_15));
        FindSubstanceFragmentAdapter substanceAdapter = new FindSubstanceFragmentAdapter(R.layout.item_demand_substance, null);
        substanceAdapter.setEmptyView(R.layout.layout_loading, rv);
        rv.setAdapter(substanceAdapter);
        substanceAdapter.setOnItemClickListener((adapter, view, position) -> {
            DemandSubstanceBean.ListBean listBean = substanceAdapter.getData().get(position);
            Bundle bundle = new Bundle();
            bundle.putInt(CustomConfig.DEMAND_ID, listBean.getId());
            ActivityUtils.startActivity(bundle, FindSubstanceDetailActivity.class);
        });

        demandSubstanceVm = mActivity.getViewModel(DemandSubstanceVm.class);
        demandSubstanceVm.substanceLiveData.observe(this, demandSubstanceBean -> {
            isInited = true;
            if (demandSubstanceBean.getPage() == 1) {
                substanceAdapter.setNewData(demandSubstanceBean.getList());
                substanceAdapter.setEmptyView(emptyView);
            }else {
                substanceAdapter.addData(demandSubstanceBean.getList());
            }
            setNoMore(demandSubstanceBean.getPage(), demandSubstanceBean.getCount());
        });

        refresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                demandSubstanceVm.getMoreSubstance();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                demandSubstanceVm.getSubstance();
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
        demandSubstanceVm.getSubstance();
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
