package com.shuangduan.zcy.view.projectinfo;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.BarUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.ProjectInfoAdapter;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.model.bean.ProjectInfoBean;
import com.shuangduan.zcy.weight.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view.projectinfo
 * @class describe  工程信息列表模式
 * @time 2019/7/12 16:27
 * @change
 * @chang time
 * @class describe
 */
public class ProjectInfoListActivity extends BaseActivity {
    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.iv_bar_right)
    AppCompatImageView ivBarRight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_area)
    TextView tvArea;
    @BindView(R.id.iv_expand)
    ImageView ivExpand;
    @BindView(R.id.tv_stage)
    TextView tvStage;
    @BindView(R.id.iv_stage)
    ImageView ivStage;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.iv_type)
    ImageView ivType;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.iv_time)
    ImageView ivTime;
    @BindView(R.id.tv_subscribe)
    TextView tvSubscribe;
    @BindView(R.id.iv_subscribe)
    ImageView ivSubscribe;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.refresh)
    SmartRefreshLayout refresh;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_project_info_list;
    }

    @Override
    protected void initDataAndEvent() {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(getResources().getStringArray(R.array.classify)[0]);

        List<ProjectInfoBean> list = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            list.add(new ProjectInfoBean());
        }
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST, R.drawable.divider_15));
        ProjectInfoAdapter projectInfoAdapter = new ProjectInfoAdapter(R.layout.item_project_info, list);
        rv.setAdapter(projectInfoAdapter);

        refresh.setEnableLoadMore(false);
        refresh.setEnableRefresh(false);
    }

    @OnClick({R.id.iv_bar_back, R.id.iv_bar_right, R.id.ll_area, R.id.ll_stage, R.id.ll_type, R.id.ll_time, R.id.ll_subscribe})
    void onClick(View view){
        switch (view.getId()){
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.iv_bar_right:
                break;
            case R.id.ll_area:
                break;
            case R.id.ll_stage:
                break;
            case R.id.ll_type:
                break;
            case R.id.ll_time:
                break;
            case R.id.ll_subscribe:
                break;
        }
    }
}
