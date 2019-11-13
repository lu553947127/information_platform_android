package com.shuangduan.zcy.adminManage.adapter;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adminManage.bean.DeviceBean;

import java.util.List;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.adminManage.adapter
 * @ClassName: DeviceAdapter
 * @Description: java类作用描述
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/11/6 9:54
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/11/6 9:54
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class DeviceAdapter extends BaseQuickAdapter<DeviceBean.ListBean, BaseViewHolder> {

    private int construction_edit, construction_delete, is_children;

    public DeviceAdapter(int layoutResId, @Nullable List<DeviceBean.ListBean> data, int construction_edit, int construction_delete, int is_children) {
        super(layoutResId, data);
        this.construction_edit = construction_edit;
        this.construction_delete = construction_delete;
        this.is_children = is_children;
    }

    @Override
    protected void convert(BaseViewHolder helper, DeviceBean.ListBean item) {
        String use_status = item.getUse_status();
        TextView tvUseStatus = helper.getView(R.id.tv_use_status);
        tvUseStatus.setText(use_status);
        switch (use_status){
            case "再用":
                tvUseStatus.setBackgroundResource(R.drawable.shape_green);
                break;
            case "闲置":
                tvUseStatus.setBackgroundResource(R.drawable.shape_gray);
                break;
            case "出租":
                tvUseStatus.setBackgroundResource(R.drawable.shape_yellow);
                break;
            case "待报废":
                tvUseStatus.setBackgroundResource(R.drawable.shape_red);
                break;
        }

        helper.setText(R.id.tv_material, item.getMaterial_id())
                .setText(R.id.tv_category, "设备类别：" + item.getCategory())
                .setText(R.id.tv_is_shelf, "是否上架：" + item.getIs_shelf() + "      库存数量：" + item.getStock())
                .setText(R.id.tv_company, "下属公司：" + item.getCompany())
                .setText(R.id.tv_project, "项目名称：" + item.getProject())
                .setText(R.id.tv_address, "存放地点：" + item.getProvince() + item.getCity() + item.getAddress())
                .addOnClickListener(R.id.tv_edit)
                .addOnClickListener(R.id.tv_delete);

        LinearLayout linearLayout = helper.getView(R.id.ll_edit);
        TextView tvEdit = helper.getView(R.id.tv_edit);
        TextView tvDelete = helper.getView(R.id.tv_delete);
        switch (is_children){
            case 1://普通供应商
            case 2://子公司
                linearLayout.setVisibility(View.VISIBLE);
                tvEdit.setVisibility(View.VISIBLE);
                tvDelete.setVisibility(View.VISIBLE);
                break;
            case 3://集团
            case 5://集团子账号
                linearLayout.setVisibility(View.GONE);
                break;
            case 4://子公司子账号
                if (construction_edit == 0 && construction_delete == 0) {
                    linearLayout.setVisibility(View.GONE);
                } else if (construction_edit == 1 && construction_delete == 0) {
                    linearLayout.setVisibility(View.VISIBLE);
                    tvEdit.setVisibility(View.VISIBLE);
                    tvDelete.setVisibility(View.GONE);
                } else if (construction_edit == 0 && construction_delete == 1) {
                    linearLayout.setVisibility(View.VISIBLE);
                    tvEdit.setVisibility(View.GONE);
                    tvDelete.setVisibility(View.VISIBLE);
                }
                break;
        }
    }
}
