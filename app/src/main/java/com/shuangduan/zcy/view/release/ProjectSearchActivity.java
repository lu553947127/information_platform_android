package com.shuangduan.zcy.view.release;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.ProjectSearchAdapter;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.model.bean.ProjectSearchBean;
import com.shuangduan.zcy.model.event.ProjectNameEvent;
import com.shuangduan.zcy.vm.ProjectSearchVm;
import com.shuangduan.zcy.weight.DividerItemDecoration;

import org.greenrobot.eventbus.EventBus;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view.release
 * @class describe  工程搜索
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
    public boolean isUseEventBus() {
        return false;
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
                projectSearchVm.moreSearch();
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

        //重新键盘，改成搜索键盘，点击搜索键即可完成搜索
        edtKeyword.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (i == EditorInfo.IME_ACTION_SEARCH) {
                // 先隐藏键盘
                ((InputMethodManager) Objects.requireNonNull(edtKeyword.getContext()
                        .getSystemService(Context.INPUT_METHOD_SERVICE)))
                        .hideSoftInputFromWindow(Objects.requireNonNull(this.getCurrentFocus()).getWindowToken(),
                                InputMethodManager.HIDE_NOT_ALWAYS);
                // 搜索，进行自己要的操作...
                projectSearchVm.search(edtKeyword.getText().toString());
                if (projectSearchAdapter != null)
                    projectSearchAdapter.setKeyword(edtKeyword.getText().toString());
                return true;
            }
            return false;
        });

        //监听键盘开始搜索
        edtKeyword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (null != editable) {
                    projectSearchVm.search(editable.toString());
                    if (projectSearchAdapter != null)
                        projectSearchAdapter.setKeyword(editable.toString());
                }
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

    @OnClick({R.id.iv_bar_back, R.id.tv_positive})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_bar_back:
                finish();
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
