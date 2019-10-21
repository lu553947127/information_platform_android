package com.shuangduan.zcy.dialog;

import android.app.Activity;
import android.text.TextUtils;
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
 * @author 徐玉 QQ:876885613
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
    @BindView(R.id.tv_enclosure_title)
    TextView tvEnclosureTitle;
    @BindView(R.id.tv_tip_left_icon)
    TextView tvTipLeftIcon;
    private String ok = "";
    private String tip = "";
    private String tip_left_icon = "";
    private String title ="";
    private int iconRes = 0;

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
        if (!TextUtils.isEmpty(tip_left_icon)){
            tvTipLeftIcon.setText(tip_left_icon);
            tvTipLeftIcon.setVisibility(View.VISIBLE);
        }
        if (ok.equals("")){
            tvOk.setText(getString(R.string.positive));
        }else {
            tvOk.setText(ok);
        }
        if (iconRes != 0){
            ivIcon.setImageResource(iconRes);
            ivIcon.setVisibility(View.VISIBLE);
        }

        if (!TextUtils.isEmpty(title)){
            tvEnclosureTitle.setText(title);
            tvEnclosureTitle.setVisibility(View.VISIBLE);
        }
    }

    @Override
    void initEvent() {

    }

    public CustomDialog setTitle(String title){
        this.title = title;
        return this;
    }

    public CustomDialog setTip(String tip){
        this.tip = tip;
        return this;
    }

    public CustomDialog setTipLeftIcon(String tip_left_icon){
        this.tip_left_icon = tip_left_icon;
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
