package com.shuangduan.zcy.adapter;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.model.bean.RecruitBean;

import java.util.List;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.adapter
 * @class describe
 * @time 2019/7/12 9:03
 * @change
 * @chang time
 * @class describe
 */
public class RecruitSearchAdapter extends BaseQuickAdapter<RecruitBean.ListBean, BaseViewHolder> {
    private String keyword;

    public RecruitSearchAdapter(int layoutResId, @Nullable List<RecruitBean.ListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RecruitBean.ListBean item) {
        helper.setText(R.id.tv_title, setSpan(item.getTitle()))
                .setText(R.id.tv_content, item.getContent())
                .setText(R.id.tv_area, item.getProvince() + item.getCity())
                .setText(R.id.tv_time, item.getCreate_time());
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
