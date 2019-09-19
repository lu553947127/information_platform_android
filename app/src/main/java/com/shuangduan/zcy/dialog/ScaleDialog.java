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
 * @ClassName: ScaleDialog
 * @Description: 人员规模选择窗口
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/9/19 15:30
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/9/19 15:30
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class ScaleDialog extends BaseDialog {

    @BindView(R.id.dp_exp)
    DatePickerView dpExp;
    private int selected = 0;
    private String text;

    public ScaleDialog(@NonNull Activity activity) {
        super(activity);
    }

    @Override
    int initLayout() {
        return R.layout.dialog_scale;
    }

    @Override
    void initData() {
        setWidth(ConvertUtils.dp2px(260));
        List<String> data = new ArrayList<>();
        String[] stringArray = mActivity.getResources().getStringArray(R.array.scale_list);
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

    public ScaleDialog setSelected(int position) {
        this.selected = position;
        return this;
    }

}
