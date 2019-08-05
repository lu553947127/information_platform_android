package com.shuangduan.zcy.view.release;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.ProjectSearchAdapter;
import com.shuangduan.zcy.adapter.SearchAdapter;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.model.bean.ProjectSearchBean;
import com.shuangduan.zcy.model.event.ProjectNameEvent;
import com.shuangduan.zcy.view.mine.AuthenticationActivity;
import com.shuangduan.zcy.vm.ProjectSearchVm;
import com.shuangduan.zcy.weight.DividerItemDecoration;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.http.Body;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view.release
 * @class describe
 * @time 2019/8/2 9:13
 * @change
 * @chang time
 * @class describe
 */
public class ProjectSearchActivity extends BaseActivity {
    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.edt_keyword)
    EditText edtKeyword;
    @BindView(R.id.rv_company)
    RecyclerView rvCompany;
    @BindView(R.id.tv_bar_right)
    AppCompatTextView tvBarRight;
    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;

    private String type;
    private ProjectSearchVm projectSearchVm;
    private ProjectSearchAdapter projectSearchAdapter;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_company_search;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        type = getIntent().getStringExtra(CustomConfig.SEARCH_TYPE);
        tvBarTitle.setText(getString(R.string.project_names));

        projectSearchVm = ViewModelProviders.of(this).get(ProjectSearchVm.class);
        projectSearchVm.searchLiveData.observe(this, projectSearchBean -> {
            noMoreData(projectSearchBean.getPage(), projectSearchBean.getCount());
            if (projectSearchBean.getPage() == 1){
                if (projectSearchAdapter == null){
                    rvCompany.setLayoutManager(new LinearLayoutManager(this));
                    rvCompany.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST, R.drawable.divider_15));
                    projectSearchAdapter = new ProjectSearchAdapter(R.layout.item_project_search, projectSearchBean.getList());
                    projectSearchAdapter.setEmptyView(R.layout.layout_empty, rvCompany);
                    projectSearchAdapter.setKeyword(edtKeyword.getText().toString());
                    rvCompany.setAdapter(projectSearchAdapter);
                    projectSearchAdapter.setOnItemClickListener((adapter, view, position) -> {
                        ProjectSearchBean.ListBean listBean = (ProjectSearchBean.ListBean) (adapter.getData().get(position));
                        EventBus.getDefault().post(new ProjectNameEvent(listBean.getTitle(), listBean.getId()));
                        finish();
                    });
                }else {
                    projectSearchAdapter.setNewData(projectSearchBean.getList());
                }
            }else {
                projectSearchAdapter.addData(projectSearchBean.getList());
            }
        });

        refresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                projectSearchVm.refreshSearch();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                if (TextUtils.isEmpty(edtKeyword.getText()) || StringUtils.isTrimEmpty(edtKeyword.getText().toString())){
                    ToastUtils.showShort(getString(R.string.hint_keyword));
                    return;
                }
                if (projectSearchAdapter != null)
                    projectSearchAdapter.setKeyword(edtKeyword.getText().toString());
                projectSearchVm.search(edtKeyword.getText().toString());
            }
        });

    }

    private void noMoreData(int page, int count){
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

    @OnClick({R.id.iv_bar_back, R.id.tv_positive, R.id.tv_bar_right})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.tv_bar_right:
                Bundle bundle = new Bundle();
                bundle.putString(CustomConfig.UPLOAD_TYPE, CustomConfig.uploadTypeBusinessCard);
                ActivityUtils.startActivity(bundle, AuthenticationActivity.class);
                break;
            case R.id.tv_positive:
                if (TextUtils.isEmpty(edtKeyword.getText()) || StringUtils.isTrimEmpty(edtKeyword.getText().toString())){
                    ToastUtils.showShort(getString(R.string.hint_keyword));
                    return;
                }
                projectSearchVm.search(edtKeyword.getText().toString());
                if (projectSearchAdapter != null)
                    projectSearchAdapter.setKeyword(edtKeyword.getText().toString());
                break;
        }
    }

}
