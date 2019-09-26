package com.shuangduan.zcy.view.release;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.BarUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.SelectorFirstAdapter;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.model.event.StageEvent;
import com.shuangduan.zcy.vm.StageVm;
import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 宁文强 QQ:858777523
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
    @BindView(R.id.tv_bar_right)
    AppCompatTextView tvBarRight;
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
        tvBarTitle.setText(getString(R.string.project_type));
        tvBarRight.setText(getString(R.string.save));

        rvStage.setLayoutManager(new LinearLayoutManager(this));
        SelectorFirstAdapter stageFirstAdapter = new SelectorFirstAdapter(R.layout.item_province, null);
        rvStage.setAdapter(stageFirstAdapter);

        stageVm = ViewModelProviders.of(this).get(StageVm.class);
        //阶段点击事件
        stageFirstAdapter.setOnItemClickListener((adapter, view1, position) -> stageVm.clickFirst(position));
        stageVm.init();
        stageVm.stageFirstLiveData.observe(this, stageBeans -> {
            stageVm.setProvinceInit();
            stageFirstAdapter.setNewData(stageBeans);
        });
    }

    @OnClick({R.id.iv_bar_back, R.id.tv_bar_right})
    void onClick(View view){
        switch (view.getId()) {
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.tv_bar_right:
                EventBus.getDefault().post(new StageEvent(stageVm.getFirstName(), stageVm.getFirstId()));
                finish();
                break;
        }
    }
}
