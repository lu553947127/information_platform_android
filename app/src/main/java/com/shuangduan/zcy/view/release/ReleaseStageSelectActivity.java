package com.shuangduan.zcy.view.release;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.BarUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.SelectorFirstAdapter;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.model.event.StageEvent;
import com.shuangduan.zcy.vm.StageVm;
import com.shuangduan.zcy.weight.DividerItemDecoration;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view.release
 * @class 项目阶段选择
 * @time 2019/7/30 11:48
 * @change
 * @chang time
 * @class describe
 */
public class ReleaseStageSelectActivity extends BaseActivity {
    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_stage)
    RecyclerView rvStage;
    private StageVm stageVm;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_stage_select;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(getString(R.string.project_stage));

        rvStage.setLayoutManager(new LinearLayoutManager(this));
        rvStage.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST, R.drawable.divider_15));
        SelectorFirstAdapter stageFirstAdapter = new SelectorFirstAdapter(R.layout.item_province, null);
        rvStage.setAdapter(stageFirstAdapter);

        stageVm = getViewModel(StageVm.class);
        //阶段点击事件
        stageFirstAdapter.setOnItemClickListener((adapter, view1, position) -> {
            stageVm.clickFirst(position);
            EventBus.getDefault().post(new StageEvent(stageVm.getFirstName(), stageVm.getFirstId()));
            finish();
        });
        stageVm.init();
        stageVm.stageFirstLiveData.observe(this, stageBeans -> {
            stageVm.setProvinceInit();
            stageFirstAdapter.setNewData(stageBeans);
        });
    }

    @OnClick({R.id.iv_bar_back})
    void onClick(View view){
        switch (view.getId()) {
            case R.id.iv_bar_back:
                finish();
                break;
        }
    }
}
