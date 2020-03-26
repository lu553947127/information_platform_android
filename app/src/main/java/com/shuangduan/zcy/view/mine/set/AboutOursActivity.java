package com.shuangduan.zcy.view.mine.set;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.IncomeStatementAdapter;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.model.bean.HomeListBean;
import com.shuangduan.zcy.utils.PhoneFormatCheckUtils;
import com.shuangduan.zcy.utils.VersionUtils;
import com.shuangduan.zcy.vm.HomeVm;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zicaicloudplatform.view.activity
 * @class 关于我们
 * @time 2019/7/10 14:13
 * @change
 * @chang time
 * @class describe
 */
public class AboutOursActivity extends BaseActivity {
    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_app_version)
    TextView tvAppVersion;

    @BindView(R.id.rv_income_statement)
    RecyclerView rvIncomeStatement;
    private IncomeStatementAdapter incomeStatementAdapter;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_about_ours;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);

        if (getIntent().getStringExtra("income")!=null){
            tvBarTitle.setText(getString(R.string.my_income_explain));
        }else {
            tvBarTitle.setText(getString(R.string.about_ours));
        }

        tvAppVersion.setText(String.format(getString(R.string.format_version_name), VersionUtils.getVerName(this)));

        HomeVm homeVm = getViewModel(HomeVm.class);
        homeVm.listLiveData.observe(this, homeListBean -> {
            if (incomeStatementAdapter == null){
                rvIncomeStatement.setLayoutManager(new LinearLayoutManager(this));
                incomeStatementAdapter = new IncomeStatementAdapter(R.layout.item_income_statement, homeListBean.getExplain());
                rvIncomeStatement.setAdapter(incomeStatementAdapter);
                incomeStatementAdapter.setOnItemClickListener((adapter, view, position) -> {
                    HomeListBean.ExplainBean explainBean = incomeStatementAdapter.getData().get(position);
                    Bundle bundle = new Bundle();
                    bundle.putInt(CustomConfig.EXPLAIN_ID, explainBean.getId());
                    ActivityUtils.startActivity(bundle, ExplainDetailActivity.class);
                });
            }else {
                incomeStatementAdapter.setNewData(homeListBean.getExplain());
            }
        });
        homeVm.getList();
    }

    @OnClick({R.id.iv_bar_back,R.id.tv_phone})
    void onClick(View view){
        switch (view.getId()) {
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.tv_phone:
                PhoneFormatCheckUtils.getCallPhone(getApplicationContext(),"4009-987-789");
                break;
        }
    }
}
