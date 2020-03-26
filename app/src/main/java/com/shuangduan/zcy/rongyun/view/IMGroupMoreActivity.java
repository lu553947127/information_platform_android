package com.shuangduan.zcy.rongyun.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.IMGroupListAdapter;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.factory.EmptyViewFactory;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.model.bean.IMGroupListBean;
import com.shuangduan.zcy.view.projectinfo.ProjectInfoListActivity;
import com.shuangduan.zcy.vm.IMAddVm;
import com.shuangduan.zcy.weight.DividerItemDecoration;

import butterknife.BindView;
import butterknife.OnClick;
import io.rong.imkit.RongIM;

/**
 * @author 鹿鸿祥 QQ:553947127
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.rongyun.view
 * @class 搜索群聊更多列表
 * @time 2019/9/9 17:37
 * @change
 * @chang time
 * @class describe
 */
@SuppressLint("Registered")
public class IMGroupMoreActivity extends BaseActivity implements EmptyViewFactory.EmptyViewCallBack {

    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rv)
    RecyclerView rvGroup;
    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;
    private View emptyView;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_im_friend_more;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        emptyView = emptyViewFactory.createEmptyView(R.drawable.icon_empty_circle_contacts, R.string.empty_im_group_info, R.string.to_look_over, this);
        tvBarTitle.setText(getString(R.string.search_group));
        tvTitle.setText(getString(R.string.im_search_group));

        String name = getIntent().getStringExtra("name");

        IMAddVm imAddVm = getViewModel(IMAddVm.class);

        rvGroup.setLayoutManager(new LinearLayoutManager(this));
        rvGroup.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST, R.drawable.divider_15));
        IMGroupListAdapter imGroupListAdapter = new IMGroupListAdapter(R.layout.item_im_group_list, null);
        imGroupListAdapter.setEmptyView(R.layout.layout_loading, rvGroup);
        rvGroup.setAdapter(imGroupListAdapter);

        imGroupListAdapter.setOnItemClickListener((adapter, view, position) -> {
            IMGroupListBean.ListBean listBean = imGroupListAdapter.getData().get(position);
            RongIM.getInstance().startGroupChat(IMGroupMoreActivity.this,listBean.getGroup_id(),listBean.getGroup_name());
        });

        imAddVm.imGroupListData.observe(this,imGroupListBean -> {
            if (imGroupListBean.getPage() == 1) {
                imGroupListAdapter.setNewData(imGroupListBean.getList());
                imGroupListAdapter.setEmptyView(emptyView);
            } else {
                imGroupListAdapter.addData(imGroupListAdapter.getData().size(), imGroupListBean.getList());
            }
            setNoMore(imGroupListBean.getPage(), imGroupListBean.getCount());
        });

        imAddVm.groupListData.observe(this,imGroupListBean -> {
            if (imGroupListBean.getPage() == 1) {
                imGroupListAdapter.setNewData(imGroupListBean.getList());
                imGroupListAdapter.setEmptyView(emptyView);
            } else {
                imGroupListAdapter.addData(imGroupListAdapter.getData().size(), imGroupListBean.getList());
            }
            setNoMore(imGroupListBean.getPage(), imGroupListBean.getCount());
        });

        refresh.setOnMultiPurposeListener(new SimpleMultiPurposeListener(){
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                getGroupList(imAddVm,name);
            }

            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                getGroupListMore(imAddVm,name);
            }
        });

        imAddVm.pageStateLiveData.observe(this, s -> {
            switch (s) {
                case PageState.PAGE_LOADING:
                    showLoading();
                    break;
                default:
                    hideLoading();
                    break;
            }
        });
        getGroupList(imAddVm,name);
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

    //我的群组列表
    private void getGroupList(IMAddVm imAddVm,String name) {
        if (name != null) {
            imAddVm.searchGroup(name);
        } else {
            imAddVm.myGroup(20);
        }
    }

    //我的群组列表更多
    private void getGroupListMore(IMAddVm imAddVm,String name) {
        if (name != null) {
            imAddVm.searchGroupMore(name);
        } else {
            imAddVm.myGroupMore();
        }
    }

    @Override
    public void onEmptyClick() {
        ActivityUtils.startActivity(ProjectInfoListActivity.class);
    }

    @OnClick({R.id.iv_bar_back})
    void onClick() {
        finish();
    }
}
