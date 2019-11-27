package com.shuangduan.zcy.utils.image;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.LogUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.shuangduan.zcy.R;
import com.youth.banner.loader.ImageLoader;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.utils.image
 * @class describe  banner专用
 * @time 2019/7/11 15:38
 * @change
 * @chang time
 * @class describe
 */
public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        /**
         注意：
         1.图片加载器由自己选择，这里不限制，只是提供几种使用方法
         2.返回的图片路径为Object类型，由于不能确定你到底使用的那种图片加载器，
         传输的到的是什么格式，那么这种就使用Object接收和返回，你只需要强转成你传输的类型就行，
         切记不要胡乱强转！
         */
        if (path instanceof Integer){
            imageView.setImageResource((Integer) path);
        }else if (path instanceof String){
            //Glide 加载图片简单用法

            Glide.with(context)
                    .load((String) path)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            LogUtils.e("加载失败");
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(final Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            LogUtils.e("加载成功");
                            imageView.setImageDrawable(resource);
                            return false;
                        }
                    })
                    .placeholder(R.drawable.no_banner)
                    .error(R.drawable.no_banner)
                    .into(imageView);
        }
    }

    //提供createImageView 方法，如果不用可以不重写这个方法，主要是方便自定义ImageView的创建
    @Override
    public ImageView createImageView(Context context) {
        return super.createImageView(context);
    }
}
