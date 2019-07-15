package com.shuangduan.zcy.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ConvertUtils;
import com.google.android.flexbox.FlexboxLayout;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.base.BaseActivity;

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

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_search;
    }

    @Override
    protected void initDataAndEvent() {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(getString(R.string.search));

        for (int i = 0; i < 4; i++) {
            TextView itemHot = (TextView) LayoutInflater.from(this).inflate(R.layout.item_fl_search, flHot, false);
            itemHot.setText("热热热");
            flHot.addView(itemHot);
        }
    }

    @OnClick(R.id.iv_bar_back)
    void onClick(){finish();}
}
