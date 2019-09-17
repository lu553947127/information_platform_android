package com.shuangduan.zcy.vm;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.lifecycle.MutableLiveData;

import com.shuangduan.zcy.R;
import com.shuangduan.zcy.base.BaseViewModel;
import com.shuangduan.zcy.model.bean.ShareBean;
import com.shuangduan.zcy.utils.ShareUtils;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.vm
 * @class describe
 * @time 2019/8/15 16:47
 * @change
 * @chang time
 * @class describe
 */
public class ShareVm extends BaseViewModel {
    public MutableLiveData<ShareBean> shareLiveData;

    public ShareVm() {
        shareLiveData = new MutableLiveData<>();
    }

    public void getBitmap(String url, Bitmap defaultBitmap){
        addDisposable(Flowable.just(url)
                .subscribeOn(Schedulers.io())
                .map(s -> {
                    Bitmap bitMBitmap = ShareUtils.getBitMBitmap(s);
                    return bitMBitmap != null? bitMBitmap: defaultBitmap;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(bitmap -> {
                    ShareBean shareBean = new ShareBean();
                    shareBean.setBitmap(bitmap);
                    shareLiveData.postValue(shareBean);
                }));
    }
}
