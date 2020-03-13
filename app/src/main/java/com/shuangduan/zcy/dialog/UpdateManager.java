package com.shuangduan.zcy.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.king.app.updater.AppUpdater;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.model.bean.VersionUpgradesBean;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.dialog
 * @ClassName: UpdateManager
 * @Description: java类作用描述
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/9/20 10:06
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/9/20 10:06
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
@SuppressLint({"CutPasteId", "InflateParams", "SetTextI18n","StaticFieldLeak"})
public class UpdateManager {
    private Context mContext;
    private Dialog dialog;
    private static UpdateManager sManager;

    public static UpdateManager getInstance(Context context) {
        if (null == sManager) {
            synchronized (UpdateManager.class) {
                if (null == sManager) {
                    sManager = new UpdateManager(context);
                }
            }
        }
        return sManager;
    }

    private UpdateManager(Context context) {
        this.mContext = context;
        dialog = new Dialog(context, R.style.custom_dialog);
    }

    // 显示软件更新对话框
    public void showNoticeDialog(VersionUpgradesBean versionBean) {
        if (dialog.isShowing()) return;
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_version_layout, null);
//        dialog.setCancelable(false);
        TextView tv_code = view.findViewById(R.id.tv_code);
        TextView tv_content = view.findViewById(R.id.tv_content);
        TextView tv_version_update = view.findViewById(R.id.tv_version_update);
        TextView tv_version_cancel = view.findViewById(R.id.tv_version_cancel);
        tv_code.setText("V" + versionBean.getVersion());
        tv_content.setText(versionBean.getContent());
        tv_version_update.setOnClickListener(v -> {
            dialog.dismiss();
            //开始更新
            new AppUpdater(mContext, versionBean.getDownload_url()).start();
        });
        tv_version_cancel.setOnClickListener(v -> dialog.dismiss());
        DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
        dialog.setContentView(view, new ViewGroup.MarginLayoutParams(displayMetrics.widthPixels, ViewGroup.MarginLayoutParams.MATCH_PARENT));
        dialog.show();
    }
}
