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

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.SearchMaterialAdapter;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.listener.TextWatcherWrapper;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.model.event.MaterialEvent;
import com.shuangduan.zcy.model.event.SupplierEvent;
import com.shuangduan.zcy.utils.KeyboardUtil;
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
    //搜索类型：1 基建物资，2 供应商
    private int searchType;
    //基建物资类型 1 周转材料 ，2 设备物资
    private int materialsType;

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
        searchType = getIntent().getIntExtra(CustomConfig.SEARCH_MATERIAL_TYPE, -1);
        materialsType = getIntent().getIntExtra(CustomConfig.MATERIALS_TYPE, 0);
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        SearchVm searchVm = ViewModelProviders.of(this).get(SearchVm.class);


        rvMaterial.setLayoutManager(new LinearLayoutManager(this));
        rvMaterial.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST, R.drawable.divider_15));
        searchAdapter = new SearchMaterialAdapter(R.layout.item_project_search, null);
        View emptyView = LayoutInflater.from(this).inflate(R.layout.layout_empty, null);
        searchAdapter.setEmptyView(emptyView);
        rvMaterial.setAdapter(searchAdapter);

        searchAdapter.setOnItemClickListener((adapter, view, position) -> {
            int id = searchAdapter.getData().get(position).id;
            String s = searchAdapter.getData().get(position).name;
            if (searchType == CustomConfig.MATERIAL_TYPE) {
                EventBus.getDefault().post(new MaterialEvent(id, s));
            } else if (searchType == CustomConfig.SUPPLIER_TYPE) {
                EventBus.getDefault().post(new SupplierEvent(id, s));
            }
            finish();
        });


        searchVm.materialLiveData.observe(this, list -> {
            searchAdapter.setKeyword(edtKeyword.getText().toString());
            searchAdapter.setNewData(list);
        });


        searchVm.pageStateLiveData.observe(this, this::showHideLoad);

        //重新键盘，改成搜索键盘，点击搜索键即可完成搜索
        edtKeyword.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (i == EditorInfo.IME_ACTION_SEARCH) {

                if (StringUtils.isTrimEmpty(edtKeyword.getText().toString())) {
                    ToastUtils.showShort(getString(R.string.hint_keyword));
                    return true;
                }

                // 先隐藏键盘
                ((InputMethodManager) Objects.requireNonNull(edtKeyword.getContext()
                        .getSystemService(Context.INPUT_METHOD_SERVICE)))
                        .hideSoftInputFromWindow(Objects.requireNonNull(this.getCurrentFocus()).getWindowToken(),
                                InputMethodManager.HIDE_NOT_ALWAYS);
                // 搜索，进行自己要的操作...
                ivClear.setVisibility(edtKeyword.getText().length() > 0 ? View.VISIBLE : View.INVISIBLE);
                if (edtKeyword.getText().length() > 0) {
                    if (materialsType == CustomConfig.FRP) {
                        searchVm.searchMaterial(searchType, edtKeyword.getText().toString());
                    } else if (materialsType == CustomConfig.EQUIPMENT) {
                        searchVm.searchEquipment(searchType, edtKeyword.getText().toString());
                    }
                }
                return true;
            }
            return false;
        });

//        监听键盘开始搜索
//        edtKeyword.addTextChangedListener(new TextWatcherWrapper() {
//            @Override
//            public void afterTextChanged(Editable editable) {
//                if (null != editable) {
//                    if (StringUtils.isTrimEmpty(editable.toString())) {
//                        ToastUtils.showShort(getString(R.string.hint_keyword));
//                        return;
//                    }
//                    ivClear.setVisibility(edtKeyword.getText().length() > 0 ? View.VISIBLE : View.INVISIBLE);
//                    if (edtKeyword.getText().length() > 0) {
//                        if (materialsType == CustomConfig.FRP) {
//                            searchVm.searchMaterial(searchType, edtKeyword.getText().toString());
//                        } else if (materialsType == CustomConfig.EQUIPMENT) {
//                            searchVm.searchEquipment(searchType, edtKeyword.getText().toString());
//                        }
//                    }
//                }
//            }
//        });
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
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        KeyboardUtil.showSoftInputFromWindow(this, edtKeyword);
    }
}
