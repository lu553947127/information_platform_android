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
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.view.PayActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view.projectinfo
 * @class describe  认购订单
 * @time 2019/7/16 15:34
 * @change
 * @chang time
 * @class describe
 */
public class SubOrderActivity extends BaseActivity {
    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_project_name)
    TextView tvProjectName;
    @BindView(R.id.tv_sub_amount)
    TextView tvSubAmount;
    @BindView(R.id.tv_sub_person)
    TextView tvSubPerson;
    @BindView(R.id.tv_contact_info)
    TextView tvContactInfo;
    @BindView(R.id.tv_order_num)
    TextView tvOrderNum;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_sub_order;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(getString(R.string.sub_order));

        tvProjectName.setText("山东省济南市莱芜区影城电子科技园一期项目");
        tvSubAmount.setText("1000元");
        tvSubPerson.setText("王汪汪");
        tvContactInfo.setText("15112345678");
        tvOrderNum.setText("121dkfhdi32943204820");
    }

    @OnClick({R.id.iv_bar_back, R.id.tv_cancel, R.id.tv_confirm})
    void onClick(View view){
        switch (view.getId()){
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.tv_cancel:
                break;
            case R.id.tv_confirm:
                Bundle bundle = new Bundle();
                bundle.putInt(CustomConfig.PROJECT_ID, getIntent().getIntExtra(CustomConfig.PROJECT_ID, 0));
                ActivityUtils.startActivity(bundle, PayActivity.class);
                break;
        }
    }
}
