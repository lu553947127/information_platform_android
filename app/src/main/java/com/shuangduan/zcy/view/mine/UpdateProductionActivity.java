package com.shuangduan.zcy.view.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.model.event.ProductionEvent;
import com.shuangduan.zcy.vm.UserInfoVm;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view.mine
 * @class describe  经营产品修改
 * @time 2019/8/10 16:22
 * @change
 * @chang time
 * @class describe
 */
public class UpdateProductionActivity extends BaseActivity {
    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.edt_production)
    EditText edtProduction;
    private UserInfoVm userInfoVm;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_update_production;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(getString(R.string.product));
        edtProduction.setText(getIntent().getStringExtra(CustomConfig.PRODUCTION));

        userInfoVm = ViewModelProviders.of(this).get(UserInfoVm.class);
        userInfoVm.infoLiveData.observe(this, o -> {
            EventBus.getDefault().post(new ProductionEvent(edtProduction.getText().toString()));
            finish();
        });
        userInfoVm.pageStateLiveData.observe(this, s -> {
            switch (s){
                case PageState.PAGE_LOADING:
                    showLoading();
                default:
                    hideLoading();
                    break;
            }
        });
    }

    @OnClick({R.id.iv_bar_back, R.id.tv_confirm})
    void onClick(View view){
        switch (view.getId()) {
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.tv_confirm:
                if (StringUtils.isTrimEmpty(edtProduction.getText().toString())){
                    ToastUtils.showShort(getString(R.string.hint_production));
                    return;
                }
                userInfoVm.updateProduction(edtProduction.getText().toString());
                break;
        }
    }
}
