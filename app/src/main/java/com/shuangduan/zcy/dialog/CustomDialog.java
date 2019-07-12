package com.shuangduan.zcy.dialog;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.ConvertUtils;
import com.shuangduan.zcy.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.dialog
 * @class describe
 * @time 2019/7/12 17:24
 * @change
 * @chang time
 * @class describe
 */
public class CustomDialog extends BaseDialog {
    @BindView(R.id.tv_tip)
    TextView tvTip;

    String tip = "";

    public CustomDialog(@NonNull Activity activity) {
        super(activity);
    }

    @Override
    int initLayout() {
        return R.layout.dialog_custom;
    }

    @Override
    void initData() {
        setWidth(ConvertUtils.dp2px(260));
        setCancelOutside(true);
        DialogUtils.enterCustomAnim(this);
        tvTip.setText(tip);
    }

    @Override
    void initEvent() {

    }

    public BaseDialog setTip(String tip){
        this.tip = tip;
        return this;
    }

    @OnClick({R.id.tv_positive, R.id.tv_negative})
    void onClick(View view){
        switch (view.getId()){
            case R.id.tv_positive:
                if (callBack != null)
                    callBack.ok("");
                hideDialog();
                break;
            case R.id.tv_negative:
                if (callBack != null)
                    callBack.cancel();
                hideDialog();
                break;
        }
    }
}
