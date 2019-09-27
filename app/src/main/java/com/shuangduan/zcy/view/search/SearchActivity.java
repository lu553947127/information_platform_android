package com.shuangduan.zcy.view.search;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
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
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.android.flexbox.FlexboxLayout;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.SearchHistoryAdapter;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.model.event.SearchHistoryEvent;
import com.shuangduan.zcy.vm.SearchVm;

import org.greenrobot.eventbus.Subscribe;

import java.util.Objects;

import butterknife.BindView;
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
    @BindView(R.id.rv)
    RecyclerView rv;
    private SearchVm searchVm;
    private SearchHistoryAdapter searchAdapter;
    private String project_type;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_search;
    }

    @Override
    public boolean isUseEventBus() {
        return true;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(getString(R.string.search));

        project_type=getIntent().getStringExtra(CustomConfig.PROJECT_TYPE);

        searchVm = ViewModelProviders.of(this).get(SearchVm.class);
        searchVm.hotLiveData.observe(this, list -> {
            for (String s : list) {
                TextView itemHot = (TextView) LayoutInflater.from(this).inflate(R.layout.item_fl_search, flHot, false);
                itemHot.setText(s);
                itemHot.setOnClickListener(l -> {
                    Bundle bundle = new Bundle();
                    bundle.putString(CustomConfig.KEYWORD, s);
                    bundle.putString(CustomConfig.PROJECT_TYPE, project_type);
                    ActivityUtils.startActivity(bundle, SearchResultActivity.class);
                });
                flHot.addView(itemHot);
            }
        });

        rv.setLayoutManager(new GridLayoutManager(this, 4));
        searchAdapter = new SearchHistoryAdapter(R.layout.item_search_history, null);
        rv.setAdapter(searchAdapter);
        searchAdapter.setOnItemClickListener((adapter, view, position) -> {
            String s = searchAdapter.getData().get(position);
            Bundle bundle = new Bundle();
            bundle.putString(CustomConfig.KEYWORD, s);
            bundle.putString(CustomConfig.PROJECT_TYPE, project_type);
            ActivityUtils.startActivity(bundle, SearchResultActivity.class);
        });
        searchVm.historyLiveData.observe(this, history -> {
            searchAdapter.setNewData(history);
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

        searchVm.getHot();//获取热点搜索
        searchVm.getHistory();

        //重新键盘，改成搜索键盘，点击搜索键即可完成搜索
        edtKeyword.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (i == EditorInfo.IME_ACTION_SEARCH) {
                // 先隐藏键盘
                ((InputMethodManager) Objects.requireNonNull(edtKeyword.getContext()
                        .getSystemService(Context.INPUT_METHOD_SERVICE)))
                        .hideSoftInputFromWindow(Objects.requireNonNull(this.getCurrentFocus()).getWindowToken(),
                                InputMethodManager.HIDE_NOT_ALWAYS);
                // 搜索，进行自己要的操作...
                Bundle bundle = new Bundle();
                bundle.putString(CustomConfig.KEYWORD, edtKeyword.getText().toString());
                bundle.putString(CustomConfig.PROJECT_TYPE, project_type);
                ActivityUtils.startActivity(bundle, SearchResultActivity.class);
                return true;
            }
            return false;
        });
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
                Bundle bundle = new Bundle();
                bundle.putString(CustomConfig.KEYWORD, edtKeyword.getText().toString());
                bundle.putString(CustomConfig.PROJECT_TYPE, project_type);
                ActivityUtils.startActivity(bundle, SearchResultActivity.class);
                break;
            case R.id.iv_del:
                searchVm.delHistory();
                break;
        }
    }

    @Subscribe
    public void onEventHistoryChange(SearchHistoryEvent event){
        searchVm.getHistory();
    }

}
