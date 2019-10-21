package com.shuangduan.zcy.adapter;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.model.bean.ProjectInfoBean;

import java.util.List;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.adapter
 * @class describe
 * @time 2019/7/12 19:38
 * @change
 * @chang time
 * @class describe
 */
public class ProjectInfoSearchAdapter extends BaseQuickAdapter<ProjectInfoBean.ListBean, BaseViewHolder> {
    private String keyword;

    public ProjectInfoSearchAdapter(int layoutResId, @Nullable List<ProjectInfoBean.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ProjectInfoBean.ListBean item) {
        helper.setText(R.id.tv_title, setSpan(item.getTitle()))
                .setText(R.id.tv_content, item.getIntro())
                .setText(R.id.tv_type, item.getPhases())
                .setText(R.id.tv_readers, String.format(mContext.getString(R.string.format_num_of_readers), item.getSubscription_num()))
                .setText(R.id.tv_time, String.format(mContext.getString(R.string.format_update_time), item.getUpdate_time()))
                .setVisible(R.id.iv_subscribe, item.getWarrant_status() != 1);
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
