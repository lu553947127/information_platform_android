package com.shuangduan.zicaicloudplatform.dialog;

import android.app.Activity;
import android.view.View;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.ConvertUtils;
import com.shuangduan.zicaicloudplatform.R;
import com.shuangduan.zicaicloudplatform.weight.datepicker.DatePickerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 宁文强 QQ:858777523
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
        data.add("0-1");
        data.add("1-3");
        data.add("3-5");
        data.add("5-10");
        dpExp.setData(data);
        dpExp.setCanScroll(true);
        dpExp.setIsLoop(false);
        dpExp.setSelected(2);
    }

    @Override
    void initEvent() {
        findViewById(R.id.tv_confirm).setOnClickListener(v -> {
            hideDialog();
        });
        dpExp.setOnSelectListener((text, position) -> {
            if (singleCallBack != null){
                singleCallBack.click(text);
            }
        });
    }

}
