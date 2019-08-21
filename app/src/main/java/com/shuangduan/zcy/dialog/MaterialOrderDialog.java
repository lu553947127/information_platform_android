package com.shuangduan.zcy.dialog;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.SPUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.app.SpConfig;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.dialog
 * @class describe  基建物质订单信息确认弹窗
 * @time 2019/8/21 9:19
 * @change
 * @chang time
 * @class describe
 */
public class MaterialOrderDialog extends BaseDialog {
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_suit_num)
    TextView tvSuitNum;
    @BindView(R.id.tv_suit_t)
    TextView tvSuitT;
    @BindView(R.id.tv_amount)
    TextView tvAmount;
    @BindView(R.id.tv_owner)
    TextView tvOwner;
    @BindView(R.id.tv_tel)
    TextView tvTel;
    private String name;
    private String suitNum;
    private String suitT;
    private String amount;

    public MaterialOrderDialog(@NonNull Activity activity) {
        super(activity);
    }

    @Override
    int initLayout() {
        return R.layout.dialog_material_order;
    }

    @Override
    void initData() {
        setWidth(ConvertUtils.dp2px(260));
        setHeight(ConvertUtils.dp2px(260));
        DialogUtils.enterCustomAnim(this);
    }

    @Override
    void initEvent() {
        tvName.setText(name);
        tvSuitNum.setText(suitNum);
        tvSuitT.setText(suitT);
        tvAmount.setText(amount);
        tvOwner.setText(SPUtils.getInstance().getString(SpConfig.USERNAME));
        tvTel.setText(SPUtils.getInstance().getString(SpConfig.MOBILE));
    }

    public MaterialOrderDialog setName(String name) {
        this.name = name;
        return this;
    }

    public MaterialOrderDialog setSuitNum(String suitNum) {
        this.suitNum = suitNum;
        return this;
    }

    public MaterialOrderDialog setSuitT(String suitT) {
        this.suitT = suitT;
        return this;
    }

    public MaterialOrderDialog setAmount(String amount) {
        this.amount = amount;
        return this;
    }

    @OnClick({R.id.tv_positive, R.id.tv_negative})
    void onClick(View view){
        switch (view.getId()){
            case R.id.tv_positive:
                if (singleCallBack != null)
                    singleCallBack.click(null, 0);
                dismiss();
                break;
            case R.id.tv_negative:
                dismiss();
                break;
        }
    }
}
