package com.shuangduan.zcy.utils.image;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

import cc.shinichi.library.ImagePreview;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.utils.image
 * @ClassName: PictureEnlargeUtils
 * @Description: 图片放大查看
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/9/23 9:37
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/9/23 9:37
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class PictureEnlargeUtils {

    public static void getPictureEnlarge(Activity activity,String picture) {
        ImagePreview.getInstance()
                // 上下文，必须是activity，不需要担心内存泄漏，本框架已经处理好
                .setContext(activity)
                // 3：只有一张图片的情况，可以直接传入这张图片的url
                .setImage(picture)
                // 是否启用上拉/下拉关闭。默认不启用
                .setEnableDragClose(true)
                // 是否显示下载按钮，在页面右下角。默认显示
                .setShowDownButton(true)
                // 开启预览
                .start();
    }

    public static void getPictureEnlargeList(Activity activity,List<String> list, int position) {
        //给图片地址添加域名
        List<String> imageList = new ArrayList<>(list);
        ImagePreview.getInstance()
                // 上下文，必须是activity，不需要担心内存泄漏，本框架已经处理好
                .setContext(activity)
                // 从第几张图片开始，索引从0开始哦~
                .setIndex(position)
                // 3：只有一张图片的情况，可以直接传入这张图片的url
                .setImageList(imageList)
                // 是否启用上拉/下拉关闭。默认不启用
                .setEnableDragClose(true)
                // 是否显示下载按钮，在页面右下角。默认显示
                .setShowDownButton(true)
                // 开启预览
                .start();
    }
}
