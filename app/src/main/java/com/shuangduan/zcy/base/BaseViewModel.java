package com.shuangduan.zcy.base;

import android.util.SparseArray;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.shuangduan.zcy.model.api.convert.exception.ErrorHandlerFactory;
import com.shuangduan.zcy.model.api.convert.exception.ResponseErrorListenerImpl;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * <pre>
 *     author : 宁文强
 *     e-mail : ningwenqiang@lanhuiplay.com
 *     time   : 2018/04/03
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public class BaseViewModel extends ViewModel {

    private CompositeDisposable compositeDisposable;
    private SparseArray<MutableLiveData> liveDataSparseArray;

    @Override
    protected void onCleared() {
        if (compositeDisposable != null){
            compositeDisposable.clear();
        }
        if (liveDataSparseArray != null) {
            liveDataSparseArray.clear();
        }
        super.onCleared();
    }

    /**
     * 将每一个Disposable保存，clear方法清空
     * @param disposable
     */
    protected void addDisposable(Disposable disposable){
        if (compositeDisposable == null){
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(disposable);
    }

    /**
     * 将每一个LiveData保存，clear方法清空
     * @param liveData
     */
    protected void addLiveData(MutableLiveData liveData){
        if (liveDataSparseArray == null){
             liveDataSparseArray = new SparseArray<>();
        }
        liveDataSparseArray.put(liveDataSparseArray.size(), liveData);
    }

}
