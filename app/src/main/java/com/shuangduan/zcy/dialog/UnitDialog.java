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
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.dialog
 * @ClassName: UnitDialog
 * @Description: java类作用描述
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/9/23 17:07
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/9/23 17:07
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class UnitDialog extends BaseDialog {
    @BindView(R.id.dp_exp)
    DatePickerView dpExp;
    private int selected = 0;
    private String text;
    private float minTextScale;
    private float maxTextScale;

    public UnitDialog(@NonNull Activity activity, float minTextScale, float maxTextScale) {
        super(activity);
        this.minTextScale = minTextScale;
        this.maxTextScale = maxTextScale;
    }


    @Override
    int initLayout() {
        return R.layout.dialog_unit;
    }

    @Override
    void initData() {
        setWidth(ConvertUtils.dp2px(260));
        List<String> data = new ArrayList<>();
        String[] stringArray = mActivity.getResources().getStringArray(R.array.unit_list);
        Collections.addAll(data, stringArray);
        dpExp.setData(data);
        dpExp.setCanScroll(true);
        dpExp.setIsLoop(false);
        dpExp.setSelected(selected);
        dpExp.setTextScale(minTextScale, maxTextScale);
        this.text = stringArray[selected];
    }

    @Override
    void initEvent() {
        findViewById(R.id.tv_confirm).setOnClickListener(v -> {
            if (singleCallBack != null) {
                singleCallBack.click(text, selected);
            }
            hideDialog();
        });
        dpExp.setOnSelectListener((text, position) -> {
            this.selected = position;
            this.text = text;
        });
    }

    public UnitDialog setSelected(int position) {
        this.selected = position;
        return this;
    }
}
