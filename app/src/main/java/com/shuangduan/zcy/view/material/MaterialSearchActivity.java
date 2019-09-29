package com.shuangduan.zcy.view.material;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.CompanySearchAdapter;
import com.shuangduan.zcy.adapter.SearchMaterialAdapter;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.model.event.MaterialEvent;
import com.shuangduan.zcy.model.event.OfficeEvent;
import com.shuangduan.zcy.model.event.SupplierEvent;
import com.shuangduan.zcy.view.search.SearchResultActivity;
import com.shuangduan.zcy.vm.SearchVm;
import com.shuangduan.zcy.weight.DividerItemDecoration;

import org.greenrobot.eventbus.EventBus;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author xuyu
 * @Package com.shuangduan.zcy.view.material$
 * @class MaterialSearchActivity$
 * @class 物资搜索页面
 * @time 2019/9/24 14:34
 * @change
 * @class describe
 */
public class MaterialSearchActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    ConstraintLayout toolbar;

    @BindView(R.id.edt_keyword)
    EditText edtKeyword;

    @BindView(R.id.iv_clear)
    ImageView ivClear;

    @BindView(R.id.rv_material)
    RecyclerView rvMaterial;

    //物资或供应商列表Adapter
    private SearchMaterialAdapter searchAdapter;
    private int type;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_material_search;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        type = getIntent().getIntExtra(CustomConfig.SEARCH_MATERIAL_TYPE, -1);
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        SearchVm searchVm = ViewModelProviders.of(this).get(SearchVm.class);
        //初始化搜索框监听
//        keywordTextChangedListener(searchVm);

        searchVm.materialLiveData.observe(this, list -> {
            if (searchAdapter == null) {
                rvMaterial.setLayoutManager(new LinearLayoutManager(this));
                rvMaterial.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST, R.drawable.divider_15));
                searchAdapter = new SearchMaterialAdapter(R.layout.item_project_search, list);
                View emptyView = LayoutInflater.from(this).inflate(R.layout.layout_empty, null);
                searchAdapter.setEmptyView(emptyView);
                searchAdapter.setKeyword(edtKeyword.getText().toString());
                rvMaterial.setAdapter(searchAdapter);
                searchAdapter.setOnItemClickListener((adapter, view, position) -> {
                    int id = searchAdapter.getData().get(position).id;
                    String s = searchAdapter.getData().get(position).name;
                    if (type == CustomConfig.MATERIAL_TYPE) {
                        EventBus.getDefault().post(new MaterialEvent(id,s));
                    }else if(type == CustomConfig.SUPPLIER_TYPE){
                        EventBus.getDefault().post(new SupplierEvent(id,s));
                    }
                    finish();
                });
            } else {
                searchAdapter.setNewData(list);
            }
        });

        searchVm.pageStateLiveData.observe(this, s -> showHideLoad(s));

        //重新键盘，改成搜索键盘，点击搜索键即可完成搜索
        edtKeyword.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (i == EditorInfo.IME_ACTION_SEARCH) {
                // 先隐藏键盘
                ((InputMethodManager) Objects.requireNonNull(edtKeyword.getContext()
                        .getSystemService(Context.INPUT_METHOD_SERVICE)))
                        .hideSoftInputFromWindow(Objects.requireNonNull(this.getCurrentFocus()).getWindowToken(),
                                InputMethodManager.HIDE_NOT_ALWAYS);
                // 搜索，进行自己要的操作...
                ivClear.setVisibility(edtKeyword.getText().length() > 0 ? View.VISIBLE : View.INVISIBLE);
                if (edtKeyword.getText().length() > 0) {
                    searchVm.searchMaterial(type, edtKeyword.getText().toString());
                }
                return true;
            }
            return false;
        });
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

    private void keywordTextChangedListener(SearchVm vm) {
        edtKeyword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                ivClear.setVisibility(s.length() > 0 ? View.VISIBLE : View.INVISIBLE);
                if (s.length() > 0) {
                    vm.searchMaterial(type, s.toString());
                }
            }
        });
    }

    @OnClick({R.id.iv_bar_back, R.id.iv_clear})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.iv_clear:
                edtKeyword.setText("");
                ivClear.setVisibility(View.INVISIBLE);
                searchAdapter.setNewData(null);
                break;
//            case R.id.tv_positive:
//                if (StringUtils.isTrimEmpty(edtKeyword.getText().toString())) {
//                    ToastUtils.showShort(getString(R.string.hint_keyword));
//                    return;
//                }
//                switch (type) {
//                    case CustomConfig.MATERIAL_TYPE:
//                        EventBus.getDefault().post(new MaterialEvent(edtKeyword.getText().toString()));
//                        break;
//                    case CustomConfig.SUPPLIER_TYPE:
//                        EventBus.getDefault().post(new SupplierEvent(edtKeyword.getText().toString()));
//                        break;
//                }
//                finish();
//                break;
        }
    }
}
