package com.shuangduan.zcy.base;

import androidx.lifecycle.ViewModel;

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

    private CompositeDisposable compositeDisposable;//RxJava

    @Override
    protected void onCleared() {
        if (compositeDisposable != null){
            compositeDisposable.clear();
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

}
