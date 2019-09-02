package com.shuangduan.zcy.view.projectinfo;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.LogUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.model.bean.ProjectSubFirstBean;
import com.shuangduan.zcy.vm.GoToSubVm;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view.projectinfo
 * @class describe  去认购
 * @time 2019/7/16 15:00
 * @change
 * @chang time
 * @class describe
 */
public class GoToSubActivity extends BaseActivity {

    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.iv_bar_right)
    AppCompatImageView ivBarRight;
    @BindView(R.id.tv_bar_right)
    AppCompatTextView tvBarRight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_project_name)
    TextView tvProjectName;
    @BindView(R.id.tv_sub_num)
    TextView tvSubNum;
    @BindView(R.id.tv_sub_price)
    TextView tvSubPrice;
    @BindView(R.id.rg_sub_cycle)
    RadioGroup rgSubCycle;
    @BindView(R.id.tv_expected_return)
    TextView tvExpectedReturn;
    @BindView(R.id.rb_first)
    RadioButton rbFirst;
    @BindView(R.id.rb_second)
    RadioButton rbSecond;
    @BindView(R.id.rb_third)
    RadioButton rbThird;
    private GoToSubVm goToSubVm;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_go_to_sub;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(getString(R.string.go_to_sub));
        tvBarRight.setVisibility(View.GONE);
        ivBarRight.setImageResource(R.drawable.icon_help_white);

        rgSubCycle.check(R.id.rb_first);
        rgSubCycle.setOnCheckedChangeListener((group, checkedId) -> {
            ProjectSubFirstBean value = goToSubVm.startLiveData.getValue();
            List<ProjectSubFirstBean.SelectBean> valueSelect = value.getSelect();
            switch (checkedId) {
                case R.id.rb_first:
                    goToSubVm.month = valueSelect.get(0).getMonths();
                    tvSubPrice.setText(valueSelect.get(0).getPrice());
                    tvExpectedReturn.setText(valueSelect.get(0).getExpect_price());
                    break;
                case R.id.rb_second:
                    goToSubVm.month = valueSelect.get(1).getMonths();
                    tvSubPrice.setText(valueSelect.get(1).getPrice());
                    tvExpectedReturn.setText(valueSelect.get(1).getExpect_price());
                    break;
                case R.id.rb_third:
                    goToSubVm.month = valueSelect.get(2).getMonths();
                    tvSubPrice.setText(valueSelect.get(2).getPrice());
                    tvExpectedReturn.setText(valueSelect.get(2).getExpect_price());
                    break;
            }
        });
        goToSubVm = ViewModelProviders.of(this).get(GoToSubVm.class);
        goToSubVm.projectId = getIntent().getIntExtra(CustomConfig.PROJECT_ID, 0);
        goToSubVm.startLiveData.observe(this, projectSubFirstBean -> {
            tvProjectName.setText(projectSubFirstBean.getTitle());
            tvSubNum.setText(projectSubFirstBean.getOrder_sn());
            goToSubVm.orderSn = projectSubFirstBean.getOrder_sn();
            if (projectSubFirstBean.getSelect() != null) {
                for (int i = 0; i < projectSubFirstBean.getSelect().size(); i++) {
                    ProjectSubFirstBean.SelectBean selectBean = projectSubFirstBean.getSelect().get(i);
                    if (i == 0) {
                        rbFirst.setText(selectBean.getTime());
                        tvSubPrice.setText(selectBean.getPrice());
                        tvExpectedReturn.setText(selectBean.getExpect_price());
                        goToSubVm.month = selectBean.getMonths();
                    }else if (i == 1){
                        rbSecond.setText(selectBean.getTime());
                    }else if (i == 2){
                        rbThird.setText(selectBean.getTime());
                    }
                }
            }
        });
        goToSubVm.confirmLiveData.observe(this, projectSubConfirmBean -> {
            Bundle bundle = new Bundle();
            bundle.putInt(CustomConfig.PROJECT_ID, getIntent().getIntExtra(CustomConfig.PROJECT_ID, 0));
            bundle.putParcelable(CustomConfig.ORDER, projectSubConfirmBean);
            ActivityUtils.startActivity(bundle, SubOrderActivity.class);
        });
        goToSubVm.pageStateLiveData.observe(this, s -> {
            showPageState(s);
        });
        goToSubVm.startWarrant();
    }

    @OnClick({R.id.iv_bar_back, R.id.iv_bar_right, R.id.tv_confirm})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.iv_bar_right:
                break;
            case R.id.tv_confirm:
                goToSubVm.confirmWarrant();
                break;
        }
    }
}
