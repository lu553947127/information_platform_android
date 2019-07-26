package com.shuangduan.zcy.dialog;

import android.app.Activity;
import android.view.View;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.ConvertUtils;
import com.shuangduan.zcy.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 宁文强 QQ:858777523
 * @name ZICAICloudPlatform
 * @class name：com.example.zicaicloudplatform.dialog
 * @class describe  性别选择
 * @time 2019/7/8 10:00
 * @change
 * @chang time
 * @class describe
 */
public class SexDialog extends BaseDialog {

    @BindView(R.id.rg_sex)
    RadioGroup rgSex;

    private int sex = 0;

    public SexDialog(@NonNull Activity activity) {
        super(activity);
    }

    @Override
    int initLayout() {
        return R.layout.dialog_sex;
    }

    @Override
    void initData() {
        setWidth(ConvertUtils.dp2px(260));
        DialogUtils.enterCustomAnim(this);

        if (sex == 1){
            rgSex.check(R.id.rb_man);
        }else if (sex == 2){
            rgSex.check(R.id.rb_woman);
        }
    }

    @Override
    void initEvent() {

    }

    public SexDialog setSex(int sex){
        this.sex = sex;
        return this;
    }

    @OnClick({R.id.rb_man, R.id.rb_woman})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.rb_man:
                if (onSexSelectListener != null) {
                    onSexSelectListener.man();
                }
                hideDialog();
                break;
            case R.id.rb_woman:
                if (onSexSelectListener != null) {
                    onSexSelectListener.woman();
                }
                hideDialog();
                break;
        }
    }

    private OnSexSelectListener onSexSelectListener;

    public SexDialog setOnSexSelectListener(OnSexSelectListener onSexSelectListener) {
        this.onSexSelectListener = onSexSelectListener;
        return this;
    }

    public interface OnSexSelectListener {
        void man();

        void woman();
    }

}
