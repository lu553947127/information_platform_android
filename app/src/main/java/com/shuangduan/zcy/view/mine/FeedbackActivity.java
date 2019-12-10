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
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.utils.KeyboardUtil;
import com.shuangduan.zcy.vm.FeedbackVm;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zicaicloudplatform.view.activity
 * @class 意见反馈
 * @time 2019/7/10 14:49
 * @change
 * @chang time
 * @class describe
 */
public class FeedbackActivity extends BaseActivity {
    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.edt_feedback)
    EditText edtFeedback;
    private FeedbackVm feedbackVm;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_feedback;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(getString(R.string.feedback));

        feedbackVm = ViewModelProviders.of(this).get(FeedbackVm.class);
        feedbackVm.feedbackLiveData.observe(this, o -> {
            ToastUtils.showShort("提交意见反馈成功.");
            finish();
        });
        feedbackVm.pageStateLiveData.observe(this, s -> {
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
                if (StringUtils.isTrimEmpty(edtFeedback.getText().toString())){
                    ToastUtils.showShort(getString(R.string.hint_opinion_error));
                    return;
                }
                feedbackVm.submit(edtFeedback.getText().toString());
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        KeyboardUtil.showSoftInputFromWindow(this, edtFeedback);
    }
}
