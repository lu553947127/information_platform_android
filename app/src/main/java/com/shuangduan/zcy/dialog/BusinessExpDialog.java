package com.shuangduan.zcy.dialog;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.ConvertUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.weight.datepicker.DatePickerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;

/**
 * @author 徐玉 QQ:876885613
 * @name ZICAICloudPlatform
 * @class name：com.example.zicaicloudplatform.dialog
 * @class describe  业务经验窗口
 * @time 2019/7/8 14:40
 * @change
 * @chang time
 * @class describe
 */
public class BusinessExpDialog extends BaseDialog {
    @BindView(R.id.dp_exp)
    DatePickerView dpExp;
    private int selected = 0;
    private String text;

    public BusinessExpDialog(@NonNull Activity activity) {
        super(activity);
    }

    @Override
    int initLayout() {
        return R.layout.dialog_business_exp;
    }

    @Override
    void initData() {
        setWidth(ConvertUtils.dp2px(260));
        List<String> data = new ArrayList<>();
        String[] stringArray = mActivity.getResources().getStringArray(R.array.experience_list);
        Collections.addAll(data, stringArray);
        dpExp.setData(data);
        dpExp.setCanScroll(true);
        dpExp.setIsLoop(false);
        dpExp.setSelected(selected);
        this.text = stringArray[selected];
    }

    @Override
    void initEvent() {
        findViewById(R.id.tv_confirm).setOnClickListener(v -> {
            if (singleCallBack != null){
                singleCallBack.click(text, selected);
            }
            hideDialog();
        });
        dpExp.setOnSelectListener((text, position) -> {
            this.selected = position;
            this.text = text;
        });
    }

    public BusinessExpDialog setSelected(int position){
        this.selected = position;
        return this;
    }

}
