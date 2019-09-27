package com.shuangduan.zcy.adapter;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.LogUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.model.bean.ContactBean;
import java.util.List;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.adapter
 * @class describe
 * @time 2019/7/30 18:17
 * @change
 * @chang time
 * @class describe
 */
public abstract class ReleaseContactAdapter extends BaseQuickAdapter<ContactBean, BaseViewHolder> {
    protected ReleaseContactAdapter(int layoutResId, @Nullable List<ContactBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ContactBean item) {
        helper.addOnClickListener(R.id.iv_del)
//                .addOnClickListener(R.id.tv_type)
                .addOnClickListener(R.id.tv_address)
//                .setText(R.id.tv_type, item.getType() == null ? "" : item.getType().getType_name())
                .setText(R.id.tv_address, item.getAddress());




        EditText edtType = helper.getView(R.id.edt_type);
        edtType.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus){
                typeChange(edtType.getText().toString(), helper.getLayoutPosition());
            }
        });
        edtType.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                typeChange(edtType.getText().toString(), helper.getLayoutPosition());
            }
        });

        EditText edtUnit = helper.getView(R.id.edt_unit);
        edtUnit.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus){
                unitChange(edtUnit.getText().toString(), helper.getLayoutPosition());
            }
        });
        edtUnit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                unitChange(edtUnit.getText().toString(), helper.getLayoutPosition());
            }
        });

        EditText edtPrinciple = helper.getView(R.id.edt_principle);
        edtPrinciple.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus){
                principleChange(edtPrinciple.getText().toString(), helper.getLayoutPosition());
            }
        });
        edtPrinciple.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                principleChange(s.toString(), helper.getLayoutPosition());
            }
        });
        EditText edtMobile = helper.getView(R.id.edt_mobile);
        edtMobile.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus){
                mobileChange(edtMobile.getText().toString(), helper.getLayoutPosition());
            }
        });
        edtMobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mobileChange(s.toString(), helper.getLayoutPosition());
            }
        });
    }
    public abstract void typeChange(String text, int position);
    public abstract void unitChange(String text, int position);
    public abstract void principleChange(String text, int position);
    public abstract void mobileChange(String text, int position);

    @Override
    public long getItemId(int position) {
        return position;
    }
}
