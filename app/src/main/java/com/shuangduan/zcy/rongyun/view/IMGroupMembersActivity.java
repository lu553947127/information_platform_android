package com.shuangduan.zcy.rongyun.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.SPUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.IMGroupInfoAdapter;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.model.bean.IMGroupInfoBean;
import com.shuangduan.zcy.view.mine.user.UserInfoActivity;
import com.shuangduan.zcy.vm.IMAddVm;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 鹿鸿祥 QQ:553947127
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.rongyun.view
 * @class 群聊详情人员列表
 * @time 2019/9/11 10:12
 * @change
 * @chang time
 * @class describe
 */

public class IMGroupMembersActivity extends BaseActivity {

    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv)
    RecyclerView recyclerView;
    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;

    private IMAddVm imAddVm;
    private String group_id;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_im_group_members;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(getString(R.string.im_group_group_members));
        group_id=getIntent().getStringExtra("group_id");

        imAddVm = ViewModelProviders.of(this).get(IMAddVm.class);

        recyclerView.setLayoutManager(new GridLayoutManager(IMGroupMembersActivity.this, 5));
        IMGroupInfoAdapter imGroupInfoAdapter = new IMGroupInfoAdapter(R.layout.adapter_im_group_info, null);
        imGroupInfoAdapter.setEmptyView(R.layout.layout_loading, recyclerView);
        recyclerView.setAdapter(imGroupInfoAdapter);

        imGroupInfoAdapter.setOnItemClickListener((adapter, view, position) -> {
            IMGroupInfoBean.ListBean listBean = imGroupInfoAdapter.getData().get(position);
            if (listBean.getId()!=SPUtils.getInstance().getInt(SpConfig.USER_ID)){
                Bundle bundle = new Bundle();
                bundle.putInt(CustomConfig.UID, listBean.getId());
                ActivityUtils.startActivity(bundle, UserInfoActivity.class);
            }
        });

        //获取群聊详情返回结果
        imAddVm.imGroupInfoLiveData.observe(this,imGroupInfoBean -> {
            if (imGroupInfoBean.getPage() == 1) {
                imGroupInfoAdapter.setNewData(imGroupInfoBean.getList());
                imGroupInfoAdapter.setEmptyView(R.layout.layout_empty_admin, recyclerView);
            } else {
                imGroupInfoAdapter.addData(imGroupInfoAdapter.getData().size(), imGroupInfoBean.getList());
            }
            setNoMore(imGroupInfoBean.getPage(), imGroupInfoBean.getCount());
        });

        refresh.setOnMultiPurposeListener(new SimpleMultiPurposeListener(){
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                imAddVm.groupList(group_id,60);
            }

            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                imAddVm.groupListMore(group_id);
            }
        });

        imAddVm.groupList(group_id,60);
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
    void onClick(){ finish(); }
}
