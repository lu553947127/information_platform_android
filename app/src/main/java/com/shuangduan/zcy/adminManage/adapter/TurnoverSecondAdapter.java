package com.shuangduan.zcy.adminManage.adapter;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adminManage.bean.TurnoverCategoryBean;

import java.util.List;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.adminManage.adapter
 * @ClassName: TurnoverSecondAdapter
 * @Description: java类作用描述
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/10/30 10:22
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/10/30 10:22
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class TurnoverSecondAdapter extends BaseQuickAdapter<TurnoverCategoryBean, BaseViewHolder> {

    private String keyword;
    public TurnoverSecondAdapter(int layoutResId, @Nullable List<TurnoverCategoryBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, TurnoverCategoryBean item) {
        helper.setText(R.id.tv_title,setSpan(item.getCatname()));
    }

    /**
     * 设置高亮
     *
     * @param text
     * @return
     */
    private SpannableString setSpan(String text) {
        SpannableString sp = new SpannableString(text);
        // 遍历要显示的文字
        for (int i = 0; i < text.length(); i++) {
            // 得到单个文字
            String s1 = text.charAt(i) + "";
            // 判断字符串是否包含高亮显示的文字
            if (keyword.contains(s1)) {
                // 循环查找字符串中所有该文字并高亮显示
                ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#6A5FF8"));
                sp.setSpan(colorSpan, i, i + 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            }
        }
        return sp;
    }

    /**
     * 设置关键字
     *
     * @param keyword
     */
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
