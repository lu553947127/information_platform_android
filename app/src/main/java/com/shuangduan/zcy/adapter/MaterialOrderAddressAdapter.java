package com.shuangduan.zcy.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.model.bean.MaterialOrderBean;

import java.util.List;

/**
 * @author xuyu
 * @Package com.shuangduan.zcy.adapter$
 * @class MaterialOrderAddressAdapter$
 * @class 物资预定详情页 存放地Adapter
 * @time 2019/9/25 20:10
 * @change
 * @class describe
 */
public class MaterialOrderAddressAdapter extends BaseQuickAdapter<MaterialOrderBean.AddressList, BaseViewHolder> {
    private final int type;
    private String unit;

    public MaterialOrderAddressAdapter(int layoutResId, @Nullable List<MaterialOrderBean.AddressList> data, int type) {
        super(layoutResId, data);
        this.type = type;
    }

    @Override
    protected void convert(BaseViewHolder helper, MaterialOrderBean.AddressList item) {
        helper.setText(R.id.tv_reserve_num, type == CustomConfig.FRP ? item.number + unit : String.valueOf(item.number))
                .setText(R.id.tv_address, "存放地：" + item.address);

    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
