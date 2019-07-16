package com.shuangduan.zcy.view.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 宁文强 QQ:858777523
 * @name ZICAICloudPlatform
 * @class name：com.example.zicaicloudplatform.view.activity
 * @class describe  公司搜索
 * @time 2019/7/8 13:40
 * @change
 * @chang time
 * @class describe
 */
public class CompanySearchActivity extends BaseActivity {
    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.edt_keyword)
    EditText edtKeyword;
    @BindView(R.id.rv_company)
    RecyclerView rvCompany;
    @BindView(R.id.tv_bar_right)
    AppCompatTextView tvBarRight;

    private String type;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_company_search;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        type = getIntent().getStringExtra(CustomConfig.SEARCH_TYPE);
        switch (type) {
            case CustomConfig.searchTypeCompany:
                tvBarTitle.setText(getString(R.string.company_name));
                break;
            case CustomConfig.searchTypeOffice:
                tvBarTitle.setText(getString(R.string.office));
                tvBarRight.setText(getString(R.string.upload_business_card));
                break;
        }
    }

    @OnClick({R.id.iv_bar_back, R.id.tv_positive, R.id.tv_bar_right})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.tv_bar_right:
                Bundle bundle = new Bundle();
                bundle.putString(CustomConfig.UPLOAD_TYPE, CustomConfig.uploadTypeBusinessCard);
                ActivityUtils.startActivity(bundle, AuthenticationActivity.class);
                break;
            case R.id.tv_positive:
                switch (type) {
                    case CustomConfig.searchTypeCompany:

                        break;
                    case CustomConfig.searchTypeOffice:

                        break;
                }
                break;
        }
    }

}
