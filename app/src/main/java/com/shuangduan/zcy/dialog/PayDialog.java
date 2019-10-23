package com.shuangduan.zcy.dialog;

import android.app.Activity;
import android.util.SparseIntArray;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.ActivityUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.view.mine.ForgetPwdPayActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.dialog
 * @class describe  支付密码弹窗
 * @time 2019/8/14 14:40
 * @change
 * @chang time
 * @class describe
 */
public class PayDialog extends BaseDialog {
    @BindView(R.id.iv_first)
    ImageView ivFirst;
    @BindView(R.id.iv_second)
    ImageView ivSecond;
    @BindView(R.id.iv_three)
    ImageView ivThree;
    @BindView(R.id.iv_four)
    ImageView ivFour;
    @BindView(R.id.iv_five)
    ImageView ivFive;
    @BindView(R.id.iv_six)
    ImageView ivSix;
    private SparseIntArray pwd = new SparseIntArray();

    public PayDialog(@NonNull Activity activity) {
        super(activity);
    }

    @Override
    int initLayout() {
        return R.layout.dialog_pay;
    }

    @Override
    void initData() {
        getWindow().getDecorView().setBackgroundResource(R.color.colorBgDark);
        setCancelOutside(false);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setGravity(Gravity.BOTTOM);
        DialogUtils.enterBottomAnim(this);
    }

    @Override
    void initEvent() {

    }

    @OnClick({R.id.tv_cancel, R.id.tv_forget_pwd, R.id.tv_0, R.id.tv_1, R.id.tv_2, R.id.tv_3, R.id.tv_4, R.id.tv_5, R.id.tv_6, R.id.tv_7, R.id.tv_8, R.id.tv_9, R.id.iv_del})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel:
                dismiss();
                break;
            case R.id.tv_forget_pwd:
                ActivityUtils.startActivity(ForgetPwdPayActivity.class);
                break;
            case R.id.tv_0:
                clickItem(0);
                break;
            case R.id.tv_1:
                clickItem(1);
                break;
            case R.id.tv_2:
                clickItem(2);
                break;
            case R.id.tv_3:
                clickItem(3);
                break;
            case R.id.tv_4:
                clickItem(4);
                break;
            case R.id.tv_5:
                clickItem(5);
                break;
            case R.id.tv_6:
                clickItem(6);
                break;
            case R.id.tv_7:
                clickItem(7);
                break;
            case R.id.tv_8:
                clickItem(8);
                break;
            case R.id.tv_9:
                clickItem(9);
                break;
            case R.id.iv_del:
                if (pwd.size() > 0) {
                    pwd.delete(pwd.size() - 1);
                    setShow();
                }
                break;
        }
    }

    private void clickItem(int position) {
        if (pwd.size() < 6) {
            pwd.append(pwd.size(), position);
            setShow();
            if (pwd.size() >= 6) {
                StringBuilder builder = new StringBuilder();
                for (int i = 0; i < pwd.size(); i++) {
                    builder.append(pwd.get(i));
                }
                if (singleCallBack != null) {
                    singleCallBack.click(builder.toString(), 0);
                    dismiss();
                }
            }
        }
    }

    private void setShow() {
        ivFirst.setImageResource(pwd.size() >= 1? R.drawable.shape_pwd_pay_hint: R.drawable.shape_pwd_pay_empty);
        ivSecond.setImageResource(pwd.size() >= 2? R.drawable.shape_pwd_pay_hint: R.drawable.shape_pwd_pay_empty);
        ivThree.setImageResource(pwd.size() >= 3? R.drawable.shape_pwd_pay_hint: R.drawable.shape_pwd_pay_empty);
        ivFour.setImageResource(pwd.size() >= 4? R.drawable.shape_pwd_pay_hint: R.drawable.shape_pwd_pay_empty);
        ivFive.setImageResource(pwd.size() >= 5? R.drawable.shape_pwd_pay_hint: R.drawable.shape_pwd_pay_empty);
        ivSix.setImageResource(pwd.size() >= 6? R.drawable.shape_pwd_pay_hint: R.drawable.shape_pwd_pay_empty);
    }
}
