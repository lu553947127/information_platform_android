package com.shuangduan.zcy.view;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.WorkerThread;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.android.flexbox.FlexboxLayout;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.SearchAdapter;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.vm.SearchVm;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view
 * @class describe  搜索
 * @time 2019/7/15 19:18
 * @change
 * @chang time
 * @class describe
 */
public class SearchActivity extends BaseActivity {
    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.edt_keyword)
    EditText edtKeyword;
    @BindView(R.id.tv_positive)
    TextView tvPositive;
    @BindView(R.id.fl_hot)
    FlexboxLayout flHot;
    @BindView(R.id.rv_company)
    RecyclerView rvCompany;
    private SearchVm searchVm;
    private SearchAdapter searchAdapter;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_search;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(getString(R.string.search));

        searchVm = ViewModelProviders.of(this).get(SearchVm.class);
        searchVm.hotLiveData.observe(this, list -> {
            for (String s : list) {
                TextView itemHot = (TextView) LayoutInflater.from(this).inflate(R.layout.item_fl_search, flHot, false);
                itemHot.setText(s);
                flHot.addView(itemHot);
            }
        });

        rvCompany.setLayoutManager(new LinearLayoutManager(this));
        searchAdapter = new SearchAdapter(R.layout.item_search, null);
        rvCompany.setAdapter(searchAdapter);

        searchVm.getHot();//获取热点搜索
    }

    @OnClick({R.id.iv_bar_back, R.id.tv_positive, R.id.iv_del})
    void onClick(View view){
        switch (view.getId()){
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.tv_positive:
                if (StringUtils.isTrimEmpty(edtKeyword.getText().toString())){
                    ToastUtils.showShort(getString(R.string.hint_keyword));
                    return;
                }
                searchVm.search(edtKeyword.getText().toString());
                searchVm.searchLiveData.observe(this, searchBeans -> {

                });
                searchVm.pageStateLiveData.observe(this, s -> {
                    switch (s){
                        case PageState.PAGE_LOADING:
                            showLoading();
                            break;
                        case PageState.PAGE_LOAD_SUCCESS:
                        case PageState.PAGE_ERROR:
                            hideLoading();
                            break;
                    }
                });
                break;
            case R.id.iv_del:
                break;
        }
    }
}
