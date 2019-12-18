package com.shuangduan.zcy.dialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import com.shuangduan.zcy.R;
import com.shuangduan.zcy.vm.PermissionVm;
import com.tbruyelle.rxpermissions2.RxPermissions;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.dialog
 * @ClassName: BaseBottomSheetDialog
 * @Description: 相机相册弹出选择框
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/12/18 10:19
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/12/18 10:19
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
@SuppressLint("InflateParams")
public class BaseBottomSheetDialog  {

    private Activity activity;
    private RxPermissions rxPermissions;
    private PermissionVm permissionVm;
    private BottomSheetDialogs btn_dialog;

    public BaseBottomSheetDialog(Activity activity,final RxPermissions rxPermissions,final PermissionVm permissionVm) {
        this.activity = activity;
        this.rxPermissions = rxPermissions;
        this.permissionVm = permissionVm;
    }

    //弹出相册弹出
    public void showPhotoDialog() {
        //底部滑动对话框
        btn_dialog = new BottomSheetDialogs(activity);
        //设置自定view
        View dialog_view = activity.getLayoutInflater().inflate(R.layout.dialog_photo, null);
        //把布局添加进去
        btn_dialog.setContentView(dialog_view);
        //去除系统默认的背景色
        try {
            // hack bg color of the BottomSheetDialog
            ViewGroup parent = (ViewGroup) dialog_view.getParent();
            parent.setBackgroundResource(android.R.color.transparent);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ((ViewGroup.MarginLayoutParams) dialog_view.findViewById(R.id.tv_cancel).getLayoutParams()).bottomMargin = 60;
        dialog_view.findViewById(R.id.tv_cancel).setOnClickListener(view -> btn_dialog.cancel());
        dialog_view.findViewById(R.id.tv_photo).setOnClickListener(view -> { permissionVm.getPermissionCamera(rxPermissions);btn_dialog.cancel(); });
        dialog_view.findViewById(R.id.tv_select_pic).setOnClickListener(view -> { permissionVm.getPermissionAlbum(rxPermissions);btn_dialog.cancel();});
        btn_dialog.show();
    }
}
