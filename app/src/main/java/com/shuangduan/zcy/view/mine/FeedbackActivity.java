package com.shuangduan.zcy.view.mine;

import android.view.View;
import android.widget.EditText;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;

import com.blankj.utilcode.util.BarUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zicaicloudplatform.view.activity
 * @class describe  意见反馈
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

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_feedback;
    }

    @Override
    protected void initDataAndEvent() {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(getString(R.string.feedback));
    }

    @OnClick({R.id.iv_bar_back, R.id.tv_confirm})
    void onClick(View view){
        switch (view.getId()) {
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.tv_confirm:

                break;
        }
    }
}
