package com.shuangduan.zcy.utils.image;

import android.graphics.BitmapFactory;
import android.os.Environment;

import com.blankj.utilcode.util.LogUtils;

import java.io.File;
import java.util.Locale;

/**
 * <pre>
 *     author : 宁文强
 *     e-mail : ningwenqiang@lanhuiplay.com
 *     time   : 2018/09/07
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public class CompressUtils {

    public static String getPath() {
        String path = Environment.getExternalStorageDirectory() + "/happy_poetry_school/image/";
        File file = new File(path);
        if (file.mkdirs()) {
            return path;
        }
        return path;
    }

    public static void showResult(File file) {
        int[] thumbSize = computeSize(file);
        String thumbArg = String.format(Locale.CHINA, "压缩后参数：%d*%d, %dk", thumbSize[0], thumbSize[1], file.length() >> 10);
        LogUtils.i(thumbArg);
    }

    private static int[] computeSize(File srcImg) {
        int[] size = new int[2];

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inSampleSize = 1;

        BitmapFactory.decodeFile(srcImg.getAbsolutePath(), options);
        size[0] = options.outWidth;
        size[1] = options.outHeight;

        return size;
    }

}
