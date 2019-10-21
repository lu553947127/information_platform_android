package com.shuangduan.zcy.adapter;

import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ActivityUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.model.bean.IMFriendSearchBean;
import com.shuangduan.zcy.utils.image.ImageConfig;
import com.shuangduan.zcy.utils.image.ImageLoader;
import com.shuangduan.zcy.view.mine.UserInfoActivity;
import com.shuangduan.zcy.weight.CircleImageView;

import java.util.List;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.adapter
 * @class describe
 * @time 2019/8/29 14:41
 * @change
 * @chang time
 * @class describe
 */
public class IMSearchAdapter extends BaseQuickAdapter<IMFriendSearchBean.DataBean.FriendBean, BaseViewHolder> {
    private String keyword;

    public IMSearchAdapter(int layoutResId, @Nullable List<IMFriendSearchBean.DataBean.FriendBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, IMFriendSearchBean.DataBean.FriendBean item) {
        helper.setText(R.id.tv_name, setSpan(item.getName()));
        helper.setVisible(R.id.iv_sgs, item.getCardStatus() == 2);
        CircleImageView ivHead = helper.getView(R.id.iv_header);
        ImageLoader.load(mContext, new ImageConfig.Builder()
                .url(item.getPortraitUri())
                .imageView(ivHead)
                .placeholder(R.drawable.default_head)
                .errorPic(R.drawable.default_head)
                .build());
        ivHead.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putInt(CustomConfig.UID, Integer.parseInt(item.getUserId()));
            ActivityUtils.startActivity(bundle, UserInfoActivity.class);
        });
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
