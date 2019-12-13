package com.shuangduan.zcy.utils;

import android.app.Activity;
import android.os.Bundle;

import com.blankj.utilcode.util.ActivityUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.dialog.BaseDialog;
import com.shuangduan.zcy.dialog.CustomDialog;
import com.shuangduan.zcy.view.supplier.SupplierDetailActivity;
import com.shuangduan.zcy.view.supplier.SupplierJoinActivity;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.utils
 * @ClassName: SupplierUtils
 * @Description: 供应商信息判断
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/12/13 11:41
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/12/13 11:41
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class SupplierUtils {

    ///供应商信息判断
    public static void SupplierCustom(Activity activity, int supplier_status,int id){
        switch (supplier_status){
            case 0://未申请
                ActivityUtils.startActivity(SupplierJoinActivity.class);
                break;
            case 1://未通过
                new CustomDialog(activity)
                        .setTipLeftIcon(R.drawable.icon_error,"抱歉，您的审核未通过，\n请核对后重新提交！",R.color.colorTv)
                        .setOk("重新申请")
                        .setCallBack(new BaseDialog.CallBack() {
                            @Override
                            public void cancel() {

                            }

                            @Override
                            public void ok(String s) {
                                ActivityUtils.startActivity(SupplierJoinActivity.class);
                            }
                        }).showDialog();
                break;
            case 2://已通过
                new CustomDialog(activity)
                        .setTipLeftIcon(R.drawable.icon_success,"恭喜，您已成为供应商！",R.color.colorTv)
                        .setOk("查看")
                        .setCallBack(new BaseDialog.CallBack() {
                            @Override
                            public void cancel() {

                            }

                            @Override
                            public void ok(String s) {
                                Bundle bundle = new Bundle();
                                bundle.putInt(CustomConfig.SUPPLIER_ID, id);
                                ActivityUtils.startActivity(bundle, SupplierDetailActivity.class);
                            }
                        }).showDialog();
                break;
            case 3://驳回
                new CustomDialog(activity)
                        .setTipLeftIcon(R.drawable.icon_reject_supplier,"我们正在加急核对您的申请，\n请耐心等待！",R.color.colorTv)
                        .setOk("确定")
                        .setCallBack(new BaseDialog.CallBack() {
                            @Override
                            public void cancel() {

                            }

                            @Override
                            public void ok(String s) {

                            }
                        }).showDialog();
                break;
        }
    }
}
