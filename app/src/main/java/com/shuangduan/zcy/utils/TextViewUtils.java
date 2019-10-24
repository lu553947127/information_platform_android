package com.shuangduan.zcy.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.style.ImageSpan;
import android.widget.TextView;

import com.huawei.android.hms.agent.common.UIUtils;

/**
 * @author xuyu
 * @Package com.shuangduan.zcy.utils$
 * @class TextViewUtils$
 * @class describe
 * @time 2019/10/24 10:57
 * @change
 * @class describe
 */
public class TextViewUtils {
    /**
     * 富文本添加图片到末尾 并自动匹配高度
     *  @param textView
     * @param drawable
     */
    public static void addDrawableInEnd(TextView textView, Drawable drawable, String str) {

        if (drawable == null) {
            return;
        }
        drawable.setBounds(DensityUtil.dp2px( 5), 0, DensityUtil.dp2px(48),
                DensityUtil.dp2px(13));// 缩放图片  也可根据实际需求

        ImageSpan span = new ImageSpan(drawable, ImageSpan.ALIGN_BOTTOM); //   ImageSpan.ALIGN_BASELINE放置位置
        String space = " ";
        str = str + space;
        int strLength = str.length();
        SpannableString ss = new SpannableString(str);
        ss.setSpan(span, strLength - 1, strLength, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        textView.setText(ss.subSequence(0, strLength));
    }
}
