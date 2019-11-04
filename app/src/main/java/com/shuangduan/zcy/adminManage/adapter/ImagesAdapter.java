package com.shuangduan.zcy.adminManage.adapter;

import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.utils.image.ImageConfig;
import com.shuangduan.zcy.utils.image.ImageLoader;

import java.util.List;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.adminManage.adapter
 * @ClassName: ImagesAdapter
 * @Description: java类作用描述
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/11/4 15:59
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/11/4 15:59
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class ImagesAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public ImagesAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    //删除item
    public void removeData(List<String> thumb,int position) {
        thumb.remove(position);
        //删除动画
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        ImageView ivHead = helper.getView(R.id.iv_images);
        ImageLoader.load(mContext, new ImageConfig.Builder()
                .url(item)
                .imageView(ivHead)
                .placeholder(R.drawable.wuzhi_default)
                .errorPic(R.drawable.wuzhi_default)
                .build());
        helper.addOnClickListener(R.id.iv_delete);
    }
}
