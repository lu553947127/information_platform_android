package com.shuangduan.zcy.adminManage.adapter;

import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.StringUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adminManage.bean.DeviceDetailBean;

import java.util.List;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.adminManage.adapter
 * @ClassName: DeviceImageAdapter
 * @Description: java类作用描述
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/11/6 16:56
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/11/6 16:56
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class DeviceImageAdapter  extends BaseQuickAdapter<DeviceDetailBean.ImagesBean, BaseViewHolder> {
    public DeviceImageAdapter(int layoutResId, @Nullable List<DeviceDetailBean.ImagesBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper,DeviceDetailBean.ImagesBean item) {
        Glide.with(mContext).load(StringUtils.isTrimEmpty(item.getHeade_url())?item.getUrl():item.getHeade_url()).into(helper.<ImageView>getView(R.id.iv));
        helper.addOnClickListener(R.id.iv);
    }
}
