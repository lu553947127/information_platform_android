package com.shuangduan.zcy.weight;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shuangduan.zcy.R;

import java.util.List;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.weight
 * @ClassName: NoticeRecyclerViewAdapter
 * @Description: java类作用描述
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/10/14 14:34
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/10/14 14:34
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class NoticeRecyclerViewAdapter  extends BaseQuickAdapter<String, BaseViewHolder> {

    public NoticeRecyclerViewAdapter(@Nullable List<String> data) {
        super(R.layout.adapter_material_depositing_place, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_title, item);
    }

    @Nullable
    @Override
    public String getItem(int position) {
        int newPosition = position % getData().size();
        return getData().get(newPosition);
    }

    @Override
    public int getItemViewType(int position) {
        //刚开始进入包含该类的activity时,count为0。就会出现0%0的情况，这会抛出异常，所以我们要在下面做一下判断
        int count = getHeaderLayoutCount() + getData().size();
        if (count <= 0) {
            count = 1;
        }
        int newPosition = position % count;
        return super.getItemViewType(newPosition);
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }
}
