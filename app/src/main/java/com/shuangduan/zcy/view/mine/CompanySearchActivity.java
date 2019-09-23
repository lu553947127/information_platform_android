package com.shuangduan.zcy.view.mine;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.material.internal.FlowLayout;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.CompanySearchAdapter;
import com.shuangduan.zcy.adapter.PostAdapter;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.model.event.CompanyEvent;
import com.shuangduan.zcy.model.event.OfficeEvent;
import com.shuangduan.zcy.vm.SearchVm;
import com.shuangduan.zcy.vm.UserInfoVm;
import com.shuangduan.zcy.weight.DividerItemDecoration;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 宁文强 QQ:858777523
 * @name ZICAICloudPlatform
 * @class name：com.example.zicaicloudplatform.view.activity
 * @class describe  公司搜索
 * @time 2019/7/8 13:40
 * @change
 * @chang time
 * @class describe
 */
public class CompanySearchActivity extends BaseActivity {
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

    private String type;
    //公司列表Adapter
    private CompanySearchAdapter companySearchAdapter;

    //职位列表Adapter
    private PostAdapter postAdapter;

    private UserInfoVm userInfoVm;

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
        SearchVm searchVm = ViewModelProviders.of(this).get(SearchVm.class);
        switch (type) {
            case CustomConfig.searchTypeCompany:
                tvBarTitle.setText(getString(R.string.company_name));
                edtKeyword.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if (!StringUtils.isTrimEmpty(s.toString())) {
                            searchVm.searchCompany(s.toString());
                        }
                    }
                });

                searchVm.companyLiveData.observe(this, list -> {
                    if (companySearchAdapter == null) {
                        rvCompany.setLayoutManager(new LinearLayoutManager(this));
                        rvCompany.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST, R.drawable.divider_15));
                        companySearchAdapter = new CompanySearchAdapter(R.layout.item_project_search, list);
                        View emptyView = LayoutInflater.from(this).inflate(R.layout.layout_empty_top, null);
                        TextView tip = emptyView.findViewById(R.id.tv_tip);
                        tip.setText(getString(R.string.no_company));
                        companySearchAdapter.setEmptyView(emptyView);
                        companySearchAdapter.setKeyword(edtKeyword.getText().toString());
                        rvCompany.setAdapter(companySearchAdapter);
                        companySearchAdapter.setOnItemClickListener((adapter, view, position) -> {
                            String s = companySearchAdapter.getData().get(position);
                            userInfoVm.company.postValue(s);
                        });
                    } else {
                        companySearchAdapter.setNewData(list);
                    }
                });

                break;
            case CustomConfig.searchTypeOffice:
                tvBarTitle.setText(getString(R.string.office));
//                tvBarRight.setText(getString(R.string.upload_business_card));

                searchVm.searchPost();

                searchVm.postLiveData.observe(this, list -> {
                    if (postAdapter == null) {
                        FlexboxLayoutManager flowLayoutManager = new FlexboxLayoutManager(this);

                        rvCompany.setLayoutManager(flowLayoutManager);
                        postAdapter = new PostAdapter(R.layout.item_post, list);
                        View emptyView = LayoutInflater.from(this).inflate(R.layout.layout_empty_top, null);
                        TextView tip = emptyView.findViewById(R.id.tv_tip);
                        tip.setText(getString(R.string.no_company));
                        postAdapter.setEmptyView(emptyView);
                        rvCompany.setAdapter(postAdapter);
                        companySearchAdapter.setOnItemClickListener((adapter, view, position) -> {
                            String s = companySearchAdapter.getData().get(position);
                            userInfoVm.company.postValue(s);
                        });
                    } else {
                        postAdapter.setNewData(list);
                    }
                });

                break;
        }
        userInfoVm = ViewModelProviders.of(this).get(UserInfoVm.class);
        userInfoVm.companyLiveData.observe(this, o -> {
            ToastUtils.showShort(getString(R.string.edit_success));
            EventBus.getDefault().post(new CompanyEvent(userInfoVm.company.getValue()));
            finish();
        });
        userInfoVm.officeLiveData.observe(this, o -> {
            ToastUtils.showShort(getString(R.string.edit_success));
            EventBus.getDefault().post(new OfficeEvent(userInfoVm.office.getValue()));
            finish();
        });


        userInfoVm.pageStateLiveData.observe(this, s -> showHideLoad(s));
        searchVm.pageStateLiveData.observe(this, s -> showHideLoad(s));
    }

    private void showHideLoad(String s) {
        switch (s) {
            case PageState.PAGE_LOADING:
                showLoading();
                break;
            default:
                hideLoading();
                break;
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
                if (StringUtils.isTrimEmpty(edtKeyword.getText().toString())) {
                    ToastUtils.showShort(getString(R.string.hint_keyword));
                    return;
                }
                switch (type) {
                    case CustomConfig.searchTypeCompany:
                        userInfoVm.company.postValue(edtKeyword.getText().toString());
                        break;
                    case CustomConfig.searchTypeOffice:
                        userInfoVm.office.postValue(edtKeyword.getText().toString());
                        break;
                }
                break;
        }
    }

}
