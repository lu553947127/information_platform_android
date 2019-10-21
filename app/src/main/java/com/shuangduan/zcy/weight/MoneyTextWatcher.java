package com.shuangduan.zcy.weight;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * <pre>
 *     author : 徐玉
 *     e-mail : ningwenqiang@lanhuiplay.com
 *     time   : 2018/07/12
 *     desc   : 输入小数监听器，钱
 *     version: 1.0
 * </pre>
 */

public class MoneyTextWatcher implements TextWatcher {

    private EditText editText;
    private int digits = 2;

    public MoneyTextWatcher(EditText editText) {
        this.editText = editText;
    }

    public MoneyTextWatcher setDigits(int digits) {
        this.digits = digits;
        return this;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        //删除“.”后边超出2位后的数据
        if (s.toString().contains(".")){
            if (s.length() - 1 - s.toString().indexOf(".") > digits){
                s = s.toString().subSequence(0,
                        s.toString().indexOf(".") + digits + 1);
                editText.setText(s);
                editText.setSelection(s.length());//光标移到最后
            }
        }
        //如果“.”在起始位置， 则起始位置自动补0
        if (s.toString().trim().substring(0).equals(".")){
            s = "0" + s;
            editText.setText(s);
            editText.setSelection(2);
        }

        //如果起始位置为0，且第二位跟的不是“.”，则无法后续输入
        if (s.toString().startsWith("0")
                && s.toString().trim().length() > 1){
            if (!s.toString().substring(1, 2).equals(".")){
                editText.setText(s.subSequence(0, 1));
                editText.setSelection(1);
                return;
            }
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
