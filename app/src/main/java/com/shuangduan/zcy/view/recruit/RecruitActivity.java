package com.shuangduan.zcy.view.recruit;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.RecruitAdapter;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.model.bean.RecruitBean;
import com.shuangduan.zcy.vm.RecruitVm;
import com.shuangduan.zcy.weight.DividerItemDecoration;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view.recruit
 * @class describe  招采信息列表
 * @time 2019/7/12 8:56
 * @change
 * @chang time
 * @class describe
 */
public class RecruitActivity extends BaseActivity {
    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;
    private RecruitVm recruitVm;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_recruit;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(getResources().getStringArray(R.array.classify)[1]);

        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST, R.drawable.divider_15));
        RecruitAdapter recruitAdapter = new RecruitAdapter(R.layout.item_recruit, null);
        recruitAdapter.setEmptyView(R.layout.layout_loading, rv);
        rv.setAdapter(recruitAdapter);
        recruitAdapter.setOnItemClickListener((adapter, view, position) -> {
            RecruitBean.ListBean listBean = recruitAdapter.getData().get(position);
            Bundle bundle = new Bundle();
            bundle.putInt(CustomConfig.RECRUIT_ID, listBean.getId());
            ActivityUtils.startActivity(bundle, RecruitDetailActivity.class);
        });

        recruitVm = ViewModelProviders.of(this).get(RecruitVm.class);
        recruitVm.recruitMutableLiveData.observe(this, recruitBean -> {
            if (recruitBean.getPage() == 1) {
                recruitAdapter.setNewData(recruitBean.getList());
                recruitAdapter.setEmptyView(R.layout.layout_empty_top, rv);
            }else {
                recruitAdapter.addData(recruitBean.getList());
            }
            setNoMore(recruitBean.getPage(), recruitBean.getCount());
        });
        recruitVm.getRecruit();

        refresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                recruitVm.moreRecruit();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                recruitVm.getRecruit();
            }
        });
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

    @OnClick(R.id.iv_bar_back)
    void onClick(){finish();}
}
