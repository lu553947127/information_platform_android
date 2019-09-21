package com.shuangduan.zcy.dialog;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.ConvertUtils;
import com.shuangduan.zcy.R;

import butterknife.BindView;
import butterknife.OnClick;

import static com.blankj.utilcode.util.StringUtils.getString;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.dialog
 * @class describe  双按钮弹窗
 * @time 2019/7/12 17:24
 * @change
 * @chang time
 * @class describe
 */
public class CustomDialog extends BaseDialog {

    @BindView(R.id.tv_positive)
    TextView tvOk;
    @BindView(R.id.tv_tip)
    TextView tvTip;
    @BindView(R.id.iv_icon)
    ImageView ivIcon;
    String ok = "";
    String tip = "";
    int iconRes = 0;

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
        setCancelOutside(false);
        DialogUtils.enterCustomAnim(this);
        tvTip.setText(tip);
        if (ok.equals("")){
            tvOk.setText(getString(R.string.positive));
        }else {
            tvOk.setText(ok);
        }

        if (iconRes != 0){
            ivIcon.setImageResource(iconRes);
            ivIcon.setVisibility(View.VISIBLE);
        }
    }

    @Override
    void initEvent() {

    }

    public CustomDialog setTip(String tip){
        this.tip = tip;
        return this;
    }

    public CustomDialog setOk(String ok){
        this.ok = ok;
        return this;
    }

    public CustomDialog setIcon(int icon){
        this.iconRes = icon;
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
