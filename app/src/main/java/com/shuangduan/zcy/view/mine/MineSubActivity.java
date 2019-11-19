package com.shuangduan.zcy.view.mine;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
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
import com.shuangduan.zcy.adapter.ProjectSubAdapter;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.dialog.BaseDialog;
import com.shuangduan.zcy.dialog.SubscriptionTypeDialog;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.model.bean.MyPhasesBean;
import com.shuangduan.zcy.model.bean.ProjectSubBean;
import com.shuangduan.zcy.view.projectinfo.ProjectDetailActivity;
import com.shuangduan.zcy.vm.MineSubVm;

import butterknife.BindView;
import butterknife.OnClick;

import static com.shuangduan.zcy.app.CustomConfig.SUBSCRIBE;
import static com.shuangduan.zcy.app.CustomConfig.UNUSED;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view.mine
 * @class describe  我的订阅
 * @time 2019/8/2 17:40
 * @change
 * @chang time
 * @class describe
 */
public class MineSubActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.iv_bar_right_select)
    AppCompatImageView ivBarRightSelect;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;
    private MineSubVm mineSubVm;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_mine_sub;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {

        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);

        mineSubVm = ViewModelProviders.of(this).get(MineSubVm.class);
        switch (getIntent().getIntExtra(CustomConfig.NEWS_TYPE,0)){
            case SUBSCRIBE://订阅消息
                tvBarTitle.setText(getString(R.string.subscribe_message));
                ivBarRightSelect.setVisibility(View.VISIBLE);
                getSubscribe();
                break;
            case UNUSED://闲置提醒
                tvBarTitle.setText(getString(R.string.subscribe_message_group));
                ivBarRightSelect.setVisibility(View.GONE);
                getUnused();
                break;
        }

        refresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mineSubVm.moreMyProject();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mineSubVm.myProject();
            }
        });

        mineSubVm.pageStateLiveData.observe(this, s -> {
            switch (s){
                case PageState.PAGE_LOADING:
                    showLoading();
                    break;
                default:
                    hideLoading();
                    break;
            }
        });
    }

    //订阅信息
    private void getSubscribe() {

        View emptyView = emptyViewFactory.createEmptyView(R.drawable.icon_empty_subscibe, R.string.empty_project_subscribe_info, 0, null);

        rv.setLayoutManager(new LinearLayoutManager(this));
        ProjectSubAdapter adapter = new ProjectSubAdapter(R.layout.item_project_item, null);
        adapter.setEmptyView(R.layout.layout_loading, rv);
        rv.setAdapter(adapter);

        adapter.setOnItemClickListener((helper, view, position) -> {
            ProjectSubBean.ListBean listBean = adapter.getData().get(position);
            Bundle bundle = new Bundle();
            bundle.putInt(CustomConfig.PROJECT_ID, listBean.getType_id());
            bundle.putInt(CustomConfig.LOCATION, 0);
            ActivityUtils.startActivity(bundle, ProjectDetailActivity.class);
        });

        //获取订阅信息数据
        mineSubVm.projectLiveData.observe(this, projectMineBean -> {
            if (projectMineBean.getPage() == 1) {
                adapter.setNewData(projectMineBean.getList());
                adapter.setEmptyView(emptyView);
            }else {
                adapter.addData(projectMineBean.getList());
            }
            setNoMore(projectMineBean.getPage(), projectMineBean.getCount());
        });

        //推送选择列表数据
        mineSubVm.phasesLiveData.observe(this, myPhasesBean -> new SubscriptionTypeDialog(this)
                .setItems(myPhasesBean)
                .setCallBack(new BaseDialog.CallBack() {
                    @Override
                    public void cancel() {

                    }

                    @Override
                    public void ok(String s) {
                        if (mineSubVm.phasesLiveData.getValue() == null) {
                            return;
                        }
                        mineSubVm.phasesId.clear();
                        for (MyPhasesBean bean: mineSubVm.phasesLiveData.getValue()) {
                            if (bean.getIs_select() == 1)
                                mineSubVm.phasesId.add(bean.getId());
                        }
                        mineSubVm.setPhases();
                    }
                }).showDialog());

        //推送选择成功返回结果
        mineSubVm.phasesSetLiveData.observe(this, o -> {
            mineSubVm.myProject();
            mineSubVm.myRecruit();
        });

        mineSubVm.myProject();
    }

    //闲置提醒
    private void getUnused() {

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

    @OnClick({R.id.iv_bar_back,R.id.iv_bar_right_select, R.id.iv_bar_right})
    void onClick(View v){
        Bundle bundle = new Bundle();
        switch (v.getId()){
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.iv_bar_right_select://工程推送选择
                mineSubVm.myPhases();
                break;
            case R.id.iv_bar_right://设置
                bundle.putInt(CustomConfig.NEWS_TYPE,getIntent().getIntExtra(CustomConfig.NEWS_TYPE,0));
                ActivityUtils.startActivity(bundle, NoticeSetActivity.class);
                break;
        }
    }
}
