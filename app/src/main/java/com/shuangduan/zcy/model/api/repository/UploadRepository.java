package com.shuangduan.zcy.model.api.repository;

import android.annotation.SuppressLint;
import android.os.Environment;

import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.shuangduan.zcy.app.MyApplication;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.model.bean.UploadBean;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import top.zibin.luban.Luban;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.model.api.repository
 * @class describe
 * @time 2019/7/25 14:25
 * @change
 * @chang time
 * @class describe
 */
public class UploadRepository extends BaseRepository {

    @SuppressLint("CheckResult")
    public void uploadPhoto(MutableLiveData<UploadBean> liveData, MutableLiveData<String> pageStateLiveData, final int userId, String path) {
        List<File> list = new ArrayList<>();
        list.add(new File(path));
        LogUtils.e(new File(path));


        //如果uri为空，代表为网络图片，无需压缩，直接增加即可
        Flowable.just(list)
                .observeOn(Schedulers.io())
                .doOnSubscribe(subscription -> pageStateLiveData.postValue(PageState.PAGE_LOADING))
                .map(list1 -> Luban.with(MyApplication.getInstance())
                        .ignoreBy(1024)
                        .setTargetDir(getPath())
                        .load(list1)
                        .get())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> {
                    ToastUtils.showShort(throwable.getMessage());
                    LogUtils.i(throwable.getMessage());
                })
                .onErrorResumeNext(Flowable.<List<File>>empty())
                .subscribe(files -> upload(liveData, pageStateLiveData, userId, files.get(0)), throwable -> ToastUtils.showShort("图片过大"));
    }

    private String getPath() {
        String path = MyApplication.getInstance().getFilesDir() + "/Luban/image/";
        File file = new File(path);
        if (file.mkdirs()) {
            return path;
        }
        return path;
    }

    private void upload(MutableLiveData<UploadBean> liveData, MutableLiveData<String> pageStateLiveData, int userId, File file) {
        // 创建 RequestBody，用于封装 请求RequestBody
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);

        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);

        request(apiService.upload(userId, body)).setData(liveData).setPageState(pageStateLiveData).send();
    }
}
