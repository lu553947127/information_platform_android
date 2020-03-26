package com.shuangduan.zcy.view.mine;

import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.BarUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.HelpAdapter;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.vm.HomeVm;
import com.shuangduan.zcy.weight.DividerItemDecoration;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zicaicloudplatform.view.activity
 * @class 帮助
 * @time 2019/7/10 11:45
 * @change
 * @chang time
 * @class describe
 */
public class HelperActivity extends BaseActivity {

    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv)
    TextView textView;
    @BindView(R.id.rv)
    RecyclerView recyclerView;
    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_helper;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(getString(R.string.helper));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST, R.drawable.divider_15));
        HelpAdapter adapter = new HelpAdapter(HelperActivity.this,R.layout.item_help, null);
        adapter.setEmptyView(R.layout.layout_loading, recyclerView);
        recyclerView.setAdapter(adapter);
        refresh.setEnableLoadMore(false);
        refresh.setEnableRefresh(false);

        HomeVm homeVm = getViewModel(HomeVm.class);
        homeVm.helpLiveData.observe(this,helpBean -> {
            adapter.setNewData(helpBean.getList());
            String str="关于易基建平台的<font color=\"#6a5ff8\">"+helpBean.getCount()+"</font>个问题";
            textView.setText(Html.fromHtml(str));
        });
        homeVm.pageStateLiveData.observe(this, s -> {
            switch (s) {
                case PageState.PAGE_LOADING:
                    showLoading();
                    break;
                default:
                    hideLoading();
                    break;
            }
        });
        homeVm.help();
    }

    @OnClick({R.id.iv_bar_back})
    void onClick(){
        finish();
    }
}
