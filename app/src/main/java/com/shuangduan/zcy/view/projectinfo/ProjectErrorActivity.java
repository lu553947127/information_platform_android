package com.shuangduan.zcy.view.projectinfo;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.utils.KeyboardUtil;
import com.shuangduan.zcy.vm.ProjectDetailVm;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view.projectinfo
 * @class describe  工程信息详情纠错
 * @time 2019/7/24 15:36
 * @change
 * @chang time
 * @class describe
 */
public class ProjectErrorActivity extends BaseActivity {
    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.edt_error)
    EditText edtError;
    private ProjectDetailVm projectDetailVm;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_project_error;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(getString(R.string.error_correction));

        projectDetailVm = getViewModel(ProjectDetailVm.class);
        projectDetailVm.init(getIntent().getIntExtra(CustomConfig.PROJECT_ID, 0));
    }

    @OnClick({R.id.iv_bar_back, R.id.tv_confirm})
    void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.tv_confirm:
                if (StringUtils.isTrimEmpty(edtError.getText().toString())){
                    ToastUtils.showShort(getString(R.string.hint_error_correct));
                    return;
                }
                projectDetailVm.error(edtError.getText().toString());
                projectDetailVm.errorLiveData.observe(this, o -> {
                    ToastUtils.showShort(getString(R.string.submit_success));
                    finish();
                });
                projectDetailVm.pageStateLiveData.observe(this, s -> {
                    switch (s){
                        case PageState.PAGE_SERVICE_ERROR:
                        case PageState.PAGE_ERROR:
                        case PageState.PAGE_LOAD_SUCCESS:
                            hideLoading();
                            break;
                        case PageState.PAGE_LOADING:
                            showLoading();
                            break;
                    }
                });
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        KeyboardUtil.showSoftInputFromWindow(this, edtError);
    }
}
