package com.shuangduan.zicaicloudplatform.dialog;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.ConvertUtils;
import com.google.android.flexbox.FlexboxLayout;
import com.shuangduan.zicaicloudplatform.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author 宁文强 QQ:858777523
 * @name ZICAICloudPlatform
 * @class name：com.shuangduan.zicaicloudplatform.dialog
 * @class describe  订阅类型选择
 * @time 2019/7/9 8:35
 * @change
 * @chang time
 * @class describe
 */
public class SubscriptionTypeDialog extends BaseDialog {

    @BindView(R.id.fl_subscription)
    FlexboxLayout flSubscription;

    private String[] items = {"工程进度", "工程预算", "工程工期", "地理位置"};
    private List<ItemBean> itemBeans = new ArrayList<>();

    public SubscriptionTypeDialog(@NonNull Activity activity) {
        super(activity);
    }

    @Override
    int initLayout() {
        return R.layout.dialog_subscription_type;
    }

    @Override
    void initData() {
        DialogUtils.enterCustomAnim(this);
        setWidth(ConvertUtils.dp2px(260));
        setHeight(ConvertUtils.dp2px(340));
        setCancelOutside(false);
        for (int i = 0; i < items.length; i++) {
            itemBeans.add(new ItemBean(false, items[i]));
            addItem(i);
        }

    }

    @Override
    void initEvent() {
        findViewById(R.id.tv_positive).setOnClickListener(v -> {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < itemBeans.size(); i++) {
                if (itemBeans.get(i).isSelect()){
                    stringBuilder.append(itemBeans.get(i).getName());
                }
            }
            if (callBack != null){
                callBack.ok(stringBuilder.toString());
            }
            hideDialog();
        });
        findViewById(R.id.tv_negative).setOnClickListener(v -> {
            if (callBack != null){
                callBack.cancel();
            }
            hideDialog();
        });
    }

    private void addItem(int i){
        final TextView tv = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.item_box, flSubscription, false);
        tv.setText(itemBeans.get(i).getName());
        tv.setSelected(itemBeans.get(i).isSelect());
        tv.setOnClickListener(v -> {
            itemBeans.get(i).setSelect(!tv.isSelected());
            tv.setSelected(!tv.isSelected());
        });
        flSubscription.addView(tv);
    }

    public static class ItemBean{
        boolean isSelect;
        String name;

        public ItemBean(boolean isSelect, String name) {
            this.isSelect = isSelect;
            this.name = name;
        }

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
