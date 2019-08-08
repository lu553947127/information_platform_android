package com.shuangduan.zcy.model.api.repository;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.ImageUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.ZipUtils;
import com.shuangduan.zcy.app.MyApplication;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.model.api.rxjava.BaseSubscriber;
import com.shuangduan.zcy.model.bean.UploadBean;
import com.shuangduan.zcy.utils.ShareUtils;

import org.reactivestreams.Subscription;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import top.zibin.luban.Luban;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.model.api.repository
 * @class describe
 * @time 2019/7/25 14:25
 * @change
 * @chang time
 * @class describe
 */
public class UploadRepository extends BaseRepository {

    public void uploadPhoto(MutableLiveData<UploadBean> liveData, MutableLiveData<String> pageStateLiveData, final int userId, String path){
        List<File> list = new ArrayList<>();
        list.add(new File(path));
        //如果uri为空，代表为网络图片，无需压缩，直接增加即可
        Flowable.just(list)
                .observeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Subscription>() {
                    @Override
                    public void accept(Subscription subscription) throws Exception {
                        pageStateLiveData.postValue(PageState.PAGE_LOADING);
                    }
                })
                .map(new Function<List<File>, List<File>>() {
                    @Override
                    public List<File> apply(@NonNull List<File> list) throws Exception {
                        return Luban.with(MyApplication.getInstance())
                                .ignoreBy(1024)
                                .setTargetDir(getPath())
                                .load(list)
                                .get();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        ToastUtils.showShort(throwable.getMessage());
                        LogUtils.i(throwable.getMessage());
                    }
                })
                .onErrorResumeNext(Flowable.<List<File>>empty())
                .subscribe(new Consumer<List<File>>() {
                    @Override
                    public void accept(@NonNull List<File> files) {
                        upload(liveData, pageStateLiveData, userId, files.get(0));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        ToastUtils.showShort("图片过大");
                    }
                });
    }

    private String getPath() {
        String path = Environment.getExternalStorageDirectory() + "/Luban/image/";
        File file = new File(path);
        if (file.mkdirs()) {
            return path;
        }
        return path;
    }

    private void upload(MutableLiveData<UploadBean> liveData, MutableLiveData<String> pageStateLiveData, int userId, File file){
        // 创建 RequestBody，用于封装 请求RequestBody
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);

        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("file", file.getName(), requestFile);

        request(apiService.upload(userId, body)).setData(liveData).setPageState(pageStateLiveData).send();
    }

}
