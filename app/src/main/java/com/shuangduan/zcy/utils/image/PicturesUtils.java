package com.shuangduan.zcy.utils.image;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.utils.image
 * @ClassName: PicturesUtils
 * @Description: java类作用描述
 * @Author: 鹿鸿祥
 * @CreateDate: 2020/1/10 15:43
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2020/1/10 15:43
 * @UpdateRemark: 更新说明
 * @Version: 1.1.0
 */
public class PicturesUtils {

    /**
     * 页面布局生成一张Bitmap图片
     * @param v
     * @return
     */
    public static Bitmap createViewBitmap(View v) {
        LogUtils.e(v.getWidth());
        LogUtils.e(v.getHeight());
        //如果上面那个布局不在屏幕上找到的话，这里就会报，宽高为0的异常
        Bitmap bitmap = Bitmap.createBitmap(v.getWidth(), v.getHeight(),Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        v.draw(canvas);
        return bitmap;
    }

    /**
     * 保存bitmap到本地
     * @param context
     * @param bitmap
     * @return
     */
    public static String saveImageToLocal(Context context, Bitmap bitmap, int type) {
        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(), "zcy/Image");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        LogUtils.e(file.getAbsolutePath());
        if (type == 1)saveImageAlbum(context,file,fileName);
        return file.getAbsolutePath();
    }

    /**
     * 把图片文件插入到系统图库
     * @param context
     * @param file
     * @return
     */
    private static void saveImageAlbum(Context context,File file,String fileName) {
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 通知图库更新
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) { // 判断SDK版本是不是4.4或者高于4.4
            String[] paths = new String[]{file.getAbsolutePath()};
            MediaScannerConnection.scanFile(context, paths, null, null);
        } else {
            final Intent intent;
            if (file.isDirectory()) {
                intent = new Intent(Intent.ACTION_MEDIA_MOUNTED);
                intent.setClassName("com.android.providers.media", "com.android.providers.media.MediaScannerReceiver");
                intent.setData(Uri.fromFile(Environment.getExternalStorageDirectory()));
            } else {
                intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                intent.setData(Uri.fromFile(file));
            }
            context.sendBroadcast(intent);
        }
        ToastUtils.showShort("保存图片成功");
    }
}
