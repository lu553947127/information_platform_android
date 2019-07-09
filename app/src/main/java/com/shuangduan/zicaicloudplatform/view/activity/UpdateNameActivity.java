package com.shuangduan.zicaicloudplatform.view.activity;

import android.view.View;
import android.widget.EditText;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;

import com.blankj.utilcode.util.BarUtils;
import com.shuangduan.zicaicloudplatform.R;
import com.shuangduan.zicaicloudplatform.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 宁文强 QQ:858777523
 * @name ZICAICloudPlatform
 * @class name：com.example.zicaicloudplatform.view.activity
 * @class describe  更新用户名
 * @time 2019/7/8 10:30
 * @change
 * @chang time
 * @class describe
 */
public class UpdateNameActivity extends BaseActivity {
    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.edt_name)
    EditText edtName;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_update_name;
    }

    @Override
    protected void initDataAndEvent() {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(getString(R.string.name));
    }

    @OnClick({R.id.iv_bar_back, R.id.tv_save})
    void onClick(View view){
        switch (view.getId()){
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.tv_save:

                break;
        }
    }
}
