package com.shuangduan.zcy.utils.image;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

/**
 * <pre>
 *     author : 宁文强
 *     e-mail : ningwenqiang@lanhuiplay.com
 *     time   : 2018/08/07
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public class ImageLoader {

    public static void load(Context context, ImageConfig config){

        RequestOptions options = new RequestOptions()
                .placeholder(config.getPlaceholder())
                .error(config.getErrorPic());

        if (config.isCenterCrop()){
            options.centerCrop();
        }

        if (config.isCircle()){
            options.circleCrop();
        }

        if (config.getImageRadius() > 0){
            options.transform(new RoundedCorners(config.getImageRadius()));
        }

        if (config.getBlurValue() > 0){
            options.transform(new BlurTransformation(config.getBlurValue()));
        }

        RequestManager requestManager = Glide.with(context);

        RequestBuilder<Drawable> requestBuilder = requestManager.load(config.getUrl());

        if (config.isCrossFade()){
            requestBuilder.transition(DrawableTransitionOptions.withCrossFade());
        }

        requestBuilder.apply(options)
                .into(config.getImageView());

    }

}
