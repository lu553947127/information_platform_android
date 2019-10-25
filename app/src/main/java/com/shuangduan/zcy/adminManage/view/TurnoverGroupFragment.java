package com.shuangduan.zcy.adminManage.view;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adminManage.adapter.TurnoverAdapter;
import com.shuangduan.zcy.adminManage.vm.TurnoverVm;
import com.shuangduan.zcy.base.BaseLazyFragment;
import com.shuangduan.zcy.utils.AnimationUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.view.adminManage
 * @ClassName: TurnoverGroupFragment
 * @Description: 周转材料集团列表
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/10/23 15:01
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/10/23 15:01
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class TurnoverGroupFragment extends BaseLazyFragment {

    @BindView(R.id.tv_name)
    AppCompatTextView tvName;
    @BindView(R.id.tv_grounding)
    AppCompatTextView tvGrounding;
    @BindView(R.id.tv_use_statue)
    AppCompatTextView tvUseStatue;
    @BindView(R.id.tv_depositing_place)
    AppCompatTextView tvDepositingPlace;
    @BindView(R.id.iv_add)
    ImageView ivAdd;
    @BindView(R.id.rv)
    RecyclerView recyclerView;
    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;
    private TurnoverVm turnoverVm;

    public static TurnoverGroupFragment newInstance() {
        Bundle args = new Bundle();
        TurnoverGroupFragment fragment = new TurnoverGroupFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_turnover_group;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        tvName.setText("材料名称");
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        TurnoverAdapter turnoverAdapter = new TurnoverAdapter(R.layout.item_turnover, null);
        recyclerView.setAdapter(turnoverAdapter);

        turnoverVm = ViewModelProviders.of(this).get(TurnoverVm.class);
        turnoverVm.turnoverLiveData.observe(this,turnoverBean -> {
            if (turnoverBean.getPage() == 1) {
                turnoverAdapter.setNewData(turnoverBean.getList());
                turnoverAdapter.setEmptyView(R.layout.layout_empty_top, recyclerView);
            } else {
                turnoverAdapter.addData(turnoverAdapter.getData().size(), turnoverBean.getList());
            }
            setNoMore(turnoverBean.getPage(), turnoverBean.getCount());
        });
        refresh.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);
        refresh.setOnMultiPurposeListener(new SimpleMultiPurposeListener(){
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                turnoverVm.constructionList(1,0,0,0,0,0,0,0);
            }

            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                turnoverVm.constructionListMore(1,0,0,0,0,0,0,0);
            }
        });
        //滑动监听
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                AnimationUtils.listScrollAnimation(ivAdd,dy);
            }
        });
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

    @Override
    protected void initDataFromService() {
        turnoverVm.constructionList(1,0,0,0,0,0,0,0);
    }

    @OnClick({R.id.tv_name,R.id.tv_grounding,R.id.tv_use_statue,R.id.tv_depositing_place})
    void onClick(View view){
        switch (view.getId()) {
            case R.id.tv_name://选择材料名称
                break;
            case R.id.tv_grounding://是否上架

                break;
            case R.id.tv_use_statue://使用状态
                break;
            case R.id.tv_depositing_place://存放地点
                break;
        }
    }
}
