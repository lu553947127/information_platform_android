package com.shuangduan.zcy.adapter;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.model.bean.MaterialDetailBean;
import com.shuangduan.zcy.utils.image.ImageConfig;
import com.shuangduan.zcy.utils.image.ImageLoader;

import java.util.List;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.adapter
 * @ClassName: BrowsePeopleAdapter
 * @Description: java类作用描述
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/10/24 14:12
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/10/24 14:12
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class BrowsePeopleAdapter extends BaseQuickAdapter<MaterialDetailBean.User, BaseViewHolder> {

    private int count;
    public BrowsePeopleAdapter(int layoutResId, @Nullable List<MaterialDetailBean.User> data,int count) {
        super(layoutResId, data);
        this.count=count;
    }

    @Override
    protected void convert(BaseViewHolder helper, MaterialDetailBean.User item) {
        TextView tv_number = helper.getView(R.id.tv_number);
        ImageView ivIcon = helper.getView(R.id.iv_header);
        FrameLayout frameLayout = helper.getView(R.id.frame);

        if (helper.getPosition()==getData().size()-1) {
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0, 0, 0, 0);
            frameLayout.setLayoutParams(layoutParams);
        }

        if (helper.getPosition()<5){
            ImageLoader.load(mContext, new ImageConfig.Builder()
                    .url(item.getImage())
                    .imageView(ivIcon)
                    .placeholder(R.drawable.default_pic)
                    .errorPic(R.drawable.default_pic)
                    .build());
        }else if (helper.getPosition()==5){
            ivIcon.setVisibility(View.GONE);
            tv_number.setVisibility(View.VISIBLE);
            tv_number.setText(count);
        }else {
            ivIcon.setVisibility(View.GONE);
            tv_number.setVisibility(View.GONE);
        }
    }
}
