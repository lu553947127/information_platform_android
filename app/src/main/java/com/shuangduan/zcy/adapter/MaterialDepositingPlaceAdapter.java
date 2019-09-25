package com.shuangduan.zcy.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.model.bean.MaterialDepositingPlaceBean;

import java.util.List;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.adapter
 * @ClassName: MaterialDepositingPlaceAdapter
 * @Description: java类作用描述
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/9/25 10:02
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/9/25 10:02
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class MaterialDepositingPlaceAdapter extends BaseQuickAdapter<MaterialDepositingPlaceBean, BaseViewHolder> {
    public MaterialDepositingPlaceAdapter(int layoutResId, @Nullable List<MaterialDepositingPlaceBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MaterialDepositingPlaceBean item) {
        helper.setText(R.id.tv_title, item.getAddress());
    }
}
