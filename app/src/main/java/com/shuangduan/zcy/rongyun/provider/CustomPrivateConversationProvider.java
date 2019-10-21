package com.shuangduan.zcy.rongyun.provider;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.view.ViewGroup;


import io.rong.imkit.model.UIConversation;
import io.rong.imkit.widget.provider.PrivateConversationProvider;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.rongyun.provider
 * @class describe
 * @time 2019/9/9 9:16
 * @change
 * @chang time
 * @class describe
 */
public class CustomPrivateConversationProvider extends PrivateConversationProvider {
    @Override
    public void bindView(View view, int position, UIConversation data) {
        deleteNameIfContains(data);
        super.bindView(view, position, data);
    }

    @Override
    public View newView(Context context, ViewGroup viewGroup) {
        return super.newView(context, viewGroup);
    }

    /**
     * If conversation data contains user's name, delete the name from the data
     *
     * @param data conversation ui data
     */
    private void deleteNameIfContains(UIConversation data) {
        Spannable content = data.getConversationContent();

        String separator = ": ";
        int indexOf = content.toString().indexOf(separator);
        if (indexOf == -1) {
            return;
        }

        SpannableStringBuilder builder = new SpannableStringBuilder(content);
        builder.delete(0, indexOf + separator.length());

        data.setConversationContent(builder);
    }
}
