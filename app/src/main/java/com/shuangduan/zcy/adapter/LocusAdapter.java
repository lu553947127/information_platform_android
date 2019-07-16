package com.shuangduan.zcy.adapter;

import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.SpanUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.model.bean.LocusBean;

import java.util.List;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.adapter
 * @class describe
 * @time 2019/7/16 11:22
 * @change
 * @chang time
 * @class describe
 */
public abstract class LocusAdapter extends BaseQuickAdapter<LocusBean, BaseViewHolder> {
    public LocusAdapter(int layoutResId, @Nullable List<LocusBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, LocusBean item) {
        helper.setText(R.id.tv_time, "2019-04-31")
                .setGone(R.id.view_line, helper.getAdapterPosition() != getData().size() - 1)
                .setImageResource(R.id.iv_mark, helper.getLayoutPosition() == getData().size() - 1?R.drawable.locus_marker_first:R.drawable.locus_marker_normal);

        TextView tvContent = helper.getView(R.id.tv_content);
        SpanUtils.with(tvContent)
                .append("施工队进场，负责人手机号：151****1234").appendSpace(1)
                .append(mContext.getString(R.string.read_detail))
                .setFontSize(ConvertUtils.sp2px(13))
                .setForegroundColor(mContext.getResources().getColor(R.color.colorPrimary))
                .setClickSpan(new ClickableSpan() {
                    @Override
                    public void onClick(View widget) {
                        readDetail();
                    }

                    @Override
                    public void updateDrawState(TextPaint ds) {

                    }
                }).create();
    }

    public abstract void readDetail();
}
