package com.shuangduan.zcy.view.income;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
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
import com.shuangduan.zcy.adapter.IncomePeopleAdapter;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.factory.EmptyViewFactory;
import com.shuangduan.zcy.model.bean.IncomePeopleBean;
import com.shuangduan.zcy.view.mine.RecommendFriendsActivity;
import com.shuangduan.zcy.vm.IncomePeopleVm;
import com.shuangduan.zcy.weight.DividerItemDecoration;

import butterknife.BindView;
import butterknife.OnClick;

import static com.shuangduan.zcy.app.CustomConfig.FIRST_DEGREE;
import static com.shuangduan.zcy.app.CustomConfig.SECOND_DEGREE;
import static com.shuangduan.zcy.app.CustomConfig.SEVEN_DEGREE;
import static com.shuangduan.zcy.app.CustomConfig.THREE_DEGREE;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view.income
 * @class describe  关系收益
 * @time 2019/8/9 14:37
 * @change
 * @chang time
 * @class describe
 */
public class IncomePeopleActivity extends BaseActivity implements BaseQuickAdapter.OnItemChildClickListener, EmptyViewFactory.EmptyViewCallBack {
    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;
    private IncomePeopleVm incomePeopleVm;
    private IncomePeopleAdapter incomePeopleAdapter;
    private View emptyView;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_income_release;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);

        incomePeopleVm = ViewModelProviders.of(this).get(IncomePeopleVm.class);
        incomePeopleVm.type = getIntent().getIntExtra(CustomConfig.PEOPLE_DEGREE, 0);

        switch (incomePeopleVm.type){
            case FIRST_DEGREE://熟识关系
                tvBarTitle.setText("熟识关系");
                emptyView = emptyViewFactory.createEmptyView(R.drawable.icon_empty_contacts, R.string.empty_recommend_friends_info, R.string.to_recommend, this);
                break;
            case SECOND_DEGREE://相识关系
                tvBarTitle.setText("相识关系");
                emptyView = emptyViewFactory.createEmptyView(R.drawable.icon_empty_contacts, R.string.empty_recommend_friends_no, 0, null);
                break;
            case THREE_DEGREE://初识关系
                tvBarTitle.setText("初识关系");
                emptyView = emptyViewFactory.createEmptyView(R.drawable.icon_empty_contacts, R.string.empty_recommend_friends_no1, 0, null);
                break;
            case SEVEN_DEGREE://全部关系
                tvBarTitle.setText(getString(R.string.income_people));
                emptyView = emptyViewFactory.createEmptyView(R.drawable.icon_empty_contacts, R.string.empty_people_income_info, R.string.to_recommend, this);
                break;
        }

        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST, R.drawable.divider_15));
        incomePeopleAdapter = new IncomePeopleAdapter(R.layout.item_income_people, null, incomePeopleVm.type);
        incomePeopleAdapter.setEmptyView(R.layout.layout_loading, rv);
        rv.setAdapter(incomePeopleAdapter);

        incomePeopleAdapter.setOnItemChildClickListener(this);
        incomePeopleAdapter.setOnItemClickListener((helper, view, position) -> {
            IncomePeopleBean.ListBean listBean = incomePeopleAdapter.getData().get(position);
            Bundle bundle = new Bundle();
            bundle.putInt(CustomConfig.UID, listBean.getUser_id());
            ActivityUtils.startActivity(bundle, PeopleInfoActivity.class);
        });

        incomePeopleVm.liveData.observe(this, incomePeopleBean -> {
            if (incomePeopleBean.getPage() == 1) {
                incomePeopleAdapter.setNewData(incomePeopleBean.getList());
                incomePeopleAdapter.setEmptyView(emptyView);
            } else {
                incomePeopleAdapter.addData(incomePeopleBean.getList());
            }
            setNoMore(incomePeopleBean.getPage(), incomePeopleBean.getCount());
        });

        refresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                incomePeopleVm.getMoreData();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                incomePeopleVm.getData();
            }
        });

        incomePeopleVm.getData();
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

    @OnClick({R.id.iv_bar_back})
    void onClick() {
        finish();
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        IncomePeopleBean.ListBean listBean = incomePeopleAdapter.getData().get(position);
        Bundle bundle = new Bundle();
        bundle.putInt(CustomConfig.UID, listBean.getUser_id());
        ActivityUtils.startActivity(bundle, PeopleInfoActivity.class);
    }

    @Override
    public void onEmptyClick() {
        ActivityUtils.startActivity(RecommendFriendsActivity.class);
    }
}
