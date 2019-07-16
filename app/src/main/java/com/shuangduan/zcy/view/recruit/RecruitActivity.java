package com.shuangduan.zcy.view.recruit;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.RecruitAdapter;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.model.bean.RecruitBean;
import com.shuangduan.zcy.weight.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view.recruit
 * @class describe  招采信息列表
 * @time 2019/7/12 8:56
 * @change
 * @chang time
 * @class describe
 */
public class RecruitActivity extends BaseActivity {
    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_recruit;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(getResources().getStringArray(R.array.classify)[1]);

        List<RecruitBean> list = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            list.add(new RecruitBean());
        }
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST, R.drawable.divider_15));
        RecruitAdapter recruitAdapter = new RecruitAdapter(R.layout.item_recruit, list);
        rv.setAdapter(recruitAdapter);

        refresh.setEnableRefresh(false);
        refresh.setEnableLoadMore(false);

        recruitAdapter.setOnItemClickListener((adapter, view, position) -> {
            ActivityUtils.startActivity(RecruitDetailActivity.class);
        });
    }

    @OnClick(R.id.iv_bar_back)
    void onClick(){finish();}
}
