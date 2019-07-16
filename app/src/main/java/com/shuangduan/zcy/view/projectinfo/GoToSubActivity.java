package com.shuangduan.zcy.view.projectinfo;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.base.BaseActivity;

import butterknife.BindView;
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
    @BindView(R.id.tv_sub_cycle)
    TextView tvSubCycle;
    @BindView(R.id.tv_expected_return)
    TextView tvExpectedReturn;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_go_to_sub;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(getString(R.string.go_to_sub));
        tvBarRight.setVisibility(View.GONE);
        ivBarRight.setImageResource(R.drawable.icon_help);

        tvProjectName.setText("山东省济南市莱芜区影城电子科技园一期项目");
        tvSubNum.setText("11112345678906564");
        tvSubPrice.setText("1000元");
        tvSubCycle.setText("2019-07-01至2019-07-31");
        tvExpectedReturn.setText("1000元");
    }

    @OnClick({R.id.iv_bar_back, R.id.iv_bar_right, R.id.tv_confirm})
    void onClick(View view){
        switch (view.getId()){
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.iv_bar_right:
                break;
            case R.id.tv_confirm:
                ActivityUtils.startActivity(SubOrderActivity.class);
                break;
        }
    }
}
