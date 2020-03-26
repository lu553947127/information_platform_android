package com.shuangduan.zcy.rongyun.view;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.SPUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.NewFriendAdapter;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.factory.EmptyViewFactory;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.model.bean.IMFriendApplyListBean;
import com.shuangduan.zcy.view.mine.user.UserInfoActivity;
import com.shuangduan.zcy.view.projectinfo.ProjectInfoListActivity;
import com.shuangduan.zcy.vm.IMAddVm;
import com.shuangduan.zcy.weight.DividerItemDecoration;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 鹿鸿祥 QQ:553947127
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.rongyun.view
 * @class describe  新的好友列表
 * @time 2019/8/29 17:25
 * @change
 * @chang time
 * @class describe
 */
public class NewFriendsActivity extends BaseActivity implements EmptyViewFactory.EmptyViewCallBack {
    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;
    private View emptyView;
    private IMAddVm imAddVm;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_new_friends;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);

        emptyView = emptyViewFactory.createEmptyView(R.drawable.icon_empty_circle_contacts, R.string.empty_new_friends_info, R.string.to_look_over, this);

        tvBarTitle.setText(getString(R.string.friends_new_notice));

        imAddVm = getViewModel(IMAddVm.class);

        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST, R.drawable.divider_15));
        NewFriendAdapter newFriendAdapter = new NewFriendAdapter(R.layout.item_friends_new, null);
        newFriendAdapter.setEmptyView(R.layout.layout_loading, rv);
        rv.setAdapter(newFriendAdapter);
        newFriendAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            IMFriendApplyListBean.ListBean item = newFriendAdapter.getData().get(position);
            Bundle bundle = new Bundle();
            switch (view.getId()) {
                case R.id.tv_accept://接受
                    int id = Integer.parseInt(item.getId());
                    imAddVm.imFriendApplyOperation(id, 2, "");
                    break;
                case R.id.tv_refuse://拒绝
                    bundle.putInt(CustomConfig.FRIEND_DATA, 3);
                    bundle.putString("id", item.getId());
                    bundle.putString("name", item.getUsername());
                    bundle.putString("msg", item.getApply_user_msg());
                    bundle.putString("image", item.getImage());
                    ActivityUtils.startActivity(bundle, IMAddFriendActivity.class);
                    break;
                case R.id.civ_header://点击头像查看人元详情
                    if (item.getApply_user_id()!= SPUtils.getInstance().getInt(SpConfig.USER_ID)){
                        bundle.putInt(CustomConfig.UID, item.getApply_user_id());
                        ActivityUtils.startActivity(bundle, UserInfoActivity.class);
                    }
                    break;
            }
        });

        refresh.setEnableLoadMore(false);
        refresh.setOnRefreshLoadMoreListener(new SimpleMultiPurposeListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishRefresh(500);
                imAddVm.imFriendApplyList();
            }
        });

        imAddVm.applyListLiveData.observe(this, item -> {
            newFriendAdapter.setEmptyView(emptyView);
            newFriendAdapter.setNewData(item.getList());
        });


        imAddVm.applyOperateLiveData.observe(this, item -> {
            imAddVm.imFriendApplyList();
        });

        imAddVm.pageStateLiveData.observe(this, s -> {
            switch (s) {
                case PageState.PAGE_LOADING:
//                    showLoading();
                    break;
                default:
                    hideLoading();
                    break;
            }
        });
    }
    
    @OnClick(R.id.iv_bar_back)
    void onClick() {
        finish();
    }

    @Override
    public void onEmptyClick() {
        ActivityUtils.startActivity(ProjectInfoListActivity.class);
    }

    @Override
    protected void onResume() {
        super.onResume();
        imAddVm.imFriendApplyList();
    }
}
