package com.shuangduan.zcy.view.search;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.android.material.tabs.TabLayout;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.ViewPagerAdapter;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.model.event.SearchHistoryEvent;
import com.shuangduan.zcy.vm.SearchVm;

import org.greenrobot.eventbus.EventBus;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view.search
 * @class 项目搜索页
 * @time 2019/8/15 11:40
 * @change
 * @chang time
 * @class describe
 */
public class SearchResultActivity extends BaseActivity {

    @BindView(R.id.edt_bar_title)
    EditText edtBarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.vp)
    ViewPager vp;
    private SearchVm searchVm;
    private String project_type;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_search_result;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        project_type=getIntent().getStringExtra(CustomConfig.PROJECT_TYPE);
        Fragment[] fragments = new Fragment[]{
                ProjectSearchFragment.newInstance(),
                RecruitSearchFragment.newInstance()
        };
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());
        vp.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), fragments, getResources().getStringArray(R.array.mine_sub)));
        tabLayout.setupWithViewPager(vp);

        if (!TextUtils.isEmpty(project_type)){
            vp.setCurrentItem(1);
        }
        searchVm = ViewModelProviders.of(this).get(SearchVm.class);
        searchVm.historyLiveData.observe(this, history -> {
            EventBus.getDefault().post(new SearchHistoryEvent());
        });

        searchVm.keyword = getIntent().getStringExtra(CustomConfig.KEYWORD);
        edtBarTitle.setText(getIntent().getStringExtra(CustomConfig.KEYWORD));
        //进入后初次搜索
        searchVm.search();

        //重新键盘，改成搜索键盘，点击搜索键即可完成搜索
        edtBarTitle.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (i == EditorInfo.IME_ACTION_SEARCH) {
                // 先隐藏键盘
                ((InputMethodManager) Objects.requireNonNull(edtBarTitle.getContext()
                        .getSystemService(Context.INPUT_METHOD_SERVICE)))
                        .hideSoftInputFromWindow(Objects.requireNonNull(this.getCurrentFocus()).getWindowToken(),
                                InputMethodManager.HIDE_NOT_ALWAYS);
                // 搜索，进行自己要的操作...
                searchVm.keyword = edtBarTitle.getText().toString();
                searchVm.search();
                return true;
            }
            return false;
        });
        //监听键盘开始搜索
        edtBarTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (null != editable) {
                    if (StringUtils.isTrimEmpty(editable.toString())){
                        ToastUtils.showShort(getString(R.string.hint_keyword));
                        return;
                    }
                    searchVm.keyword = edtBarTitle.getText().toString();
                    searchVm.search();
                }
            }
        });
    }

    @OnClick({R.id.iv_bar_back, R.id.tv_bar_right})
    void onClick(View view){
        switch (view.getId()){
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.tv_bar_right:
                if (StringUtils.isTrimEmpty(edtBarTitle.getText().toString())){
                    ToastUtils.showShort(getString(R.string.hint_keyword));
                    return;
                }
                searchVm.keyword = edtBarTitle.getText().toString();
                searchVm.search();
                break;
        }
    }
}
