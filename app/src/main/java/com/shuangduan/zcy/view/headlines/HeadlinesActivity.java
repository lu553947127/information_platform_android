package com.shuangduan.zcy.view.headlines;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.HeadlinesListAdapter;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.model.bean.HeadlinesBean;
import com.shuangduan.zcy.vm.HeadlinesVm;
import com.shuangduan.zcy.weight.DividerItemDecoration;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view.headlines
 * @class describe  基建头条
 * @time 2019/8/15 14:58
 * @change
 * @chang time
 * @class describe
 */
public class HeadlinesActivity extends BaseActivity {
    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_action)
    RecyclerView rvAction;
    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_headlines;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(getString(R.string.headlines));

        rvAction.setLayoutManager(new LinearLayoutManager(this));
        rvAction.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST, R.drawable.divider_15));
        HeadlinesListAdapter headlinesCategoryAdapter = new HeadlinesListAdapter(R.layout.item_headlines_action, null);
        headlinesCategoryAdapter.setEmptyView(R.layout.layout_loading, rvAction);
        rvAction.setAdapter(headlinesCategoryAdapter);
        headlinesCategoryAdapter.setOnItemClickListener((adapter, view, position) -> {
            HeadlinesBean.ListBean listBean = headlinesCategoryAdapter.getData().get(position);
            Bundle bundle = new Bundle();
            bundle.putInt(CustomConfig.HEADLINES_ID, listBean.getId());
            ActivityUtils.startActivity(bundle, HeadlinesDetailActivity.class);
        });

        HeadlinesVm headlinesVm = ViewModelProviders.of(this).get(HeadlinesVm.class);
        headlinesVm.headlinesLiveData.observe(this, headlinesBean -> {
            if (headlinesBean.getPage() == 1) {
                headlinesCategoryAdapter.setNewData(headlinesBean.getList());
                headlinesCategoryAdapter.setEmptyView(R.layout.layout_empty, rvAction);
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

    @OnClick(R.id.iv_bar_back)
    void onClick(){finish();}
}
