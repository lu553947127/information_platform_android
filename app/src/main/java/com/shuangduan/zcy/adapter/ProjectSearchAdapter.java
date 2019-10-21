package com.shuangduan.zcy.adapter;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.TextAppearanceSpan;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.SpanUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.model.bean.ProjectSearchBean;

import java.util.List;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.adapter
 * @class describe
 * @time 2019/8/2 10:21
 * @change
 * @chang time
 * @class describe
 */
public class ProjectSearchAdapter extends BaseQuickAdapter<ProjectSearchBean.ListBean, BaseViewHolder> {
    private String keyword;

    public ProjectSearchAdapter(int layoutResId, @Nullable List<ProjectSearchBean.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ProjectSearchBean.ListBean item) {
        helper.setText(R.id.tv_title, setSpan(item.getTitle()));
    }

    /**
     * 设置高亮
     * @param text
     * @return
     */
    private SpannableString setSpan(String text){
        SpannableString sp = new SpannableString(text);
        // 遍历要显示的文字
        for (int i = 0 ; i < text.length() ; i ++){
            // 得到单个文字
            String s1 = text.charAt(i) + "";
            // 判断字符串是否包含高亮显示的文字
            if (keyword.contains(s1)){
                // 循环查找字符串中所有该文字并高亮显示
                ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#6A5FF8"));
                sp.setSpan(colorSpan, i, i + 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            }
        }
        return sp;
    }

    /**
     * 设置关键字
     * @param keyword
     */
    public void setKeyword(String keyword){
        this.keyword = keyword;
    }
}
