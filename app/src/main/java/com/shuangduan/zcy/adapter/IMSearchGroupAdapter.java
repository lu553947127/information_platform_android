package com.shuangduan.zcy.adapter;

import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ActivityUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.model.bean.IMFriendSearchBean;
import com.shuangduan.zcy.rongyun.view.IMGroupDetailsActivity;

import java.util.List;

public class IMSearchGroupAdapter extends BaseQuickAdapter<IMFriendSearchBean.GroupBean, BaseViewHolder> {
    private String keyword;
    public IMSearchGroupAdapter(int layoutResId, @Nullable List<IMFriendSearchBean.GroupBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, IMFriendSearchBean.GroupBean item) {
        helper.setText(R.id.tv_name, setSpan(item.getGroup_name()));
        TextView avatar_tv= helper.getView(R.id.iv_avatar_tv);
        RelativeLayout relativeLayout=helper.getView(R.id.rl);
        if(!TextUtils.isEmpty(item.getGroup_name())){
            String name=item.getGroup_name();
            avatar_tv.setText(name.substring(0,1));
        }
        relativeLayout.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("group_id", item.getGroup_id());
            ActivityUtils.startActivity(bundle, IMGroupDetailsActivity.class);
        });
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
