package com.shuangduan.zcy.view.income;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.BarUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.IncomePeopleAdapter;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.model.bean.IncomePeopleBean;
import com.shuangduan.zcy.vm.IncomePeopleVm;
import com.shuangduan.zcy.weight.DividerItemDecoration;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view.income
 * @class describe  人脉收益
 * @time 2019/8/9 14:37
 * @change
 * @chang time
 * @class describe
 */
public class IncomePeopleActivity extends BaseActivity {
    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;
    private IncomePeopleVm incomeReleaseVm;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_income_release;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(getString(R.string.income_people));

        incomeReleaseVm = ViewModelProviders.of(this).get(IncomePeopleVm.class);

        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST, R.drawable.divider_15));
        IncomePeopleAdapter adapter = new IncomePeopleAdapter(R.layout.item_income_people, null);
        adapter.setEmptyView(R.layout.layout_loading, rv);
        rv.setAdapter(adapter);
        adapter.setOnItemClickListener((helper, view, position) -> {
            IncomePeopleBean.ListBean listBean = adapter.getData().get(position);
            Bundle bundle = new Bundle();
//            bundle.putInt(CustomConfig.PROJECT_ID, listBean.getType_id());
//            ActivityUtils.startActivity(bundle, ProjectDetailActivity.class);
        });

        incomeReleaseVm.liveData.observe(this, incomePeopleBean -> {
            if (incomePeopleBean.getPage() == 1) {
                adapter.setNewData(incomePeopleBean.getList());
                adapter.setEmptyView(R.layout.layout_empty, rv);
            }else {
                adapter.addData(incomePeopleBean.getList());
            }
            setNoMore(incomePeopleBean.getPage(), incomePeopleBean.getCount());
        });

        refresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                incomeReleaseVm.getMoreData();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                incomeReleaseVm.getData();
            }
        });

        incomeReleaseVm.getData();
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

    @OnClick({R.id.iv_bar_back})
    void onClick(){
        finish();
    }
}
