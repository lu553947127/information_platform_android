package com.shuangduan.zicaicloudplatform.base;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.Utils;
import com.shuangduan.zicaicloudplatform.R;
import com.shuangduan.zicaicloudplatform.app.MyApplication;
import com.shuangduan.zicaicloudplatform.model.event.NormalEvent;
import com.shuangduan.zicaicloudplatform.utils.AutoUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * <pre>
 *     author : nwq
 *     time   : 2018/04/03
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public abstract class BaseActivity extends AppCompatActivity implements IView {

    private Unbinder unBinder;
    public boolean isTranslationBar = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AutoUtils.setCustomDensity(this, MyApplication.getInstance());
        setContentView(initLayoutRes());
        unBinder = ButterKnife.bind(this);
        EventBus.getDefault().register(this);

        if (!isTranslationBar){
            //透明状态栏
            BarUtils.setStatusBarColor(this, ContextCompat.getColor(Utils.getApp(), R.color.colorStatusBar), false);
        } else {
            BarUtils.setStatusBarColor(this, ContextCompat.getColor(Utils.getApp(), android.R.color.transparent), false);
        }

        initDataAndEvent();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showContent() {

    }

    @Subscribe()
    public void onNormalEvent(NormalEvent normalEvent){

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //获取当前焦点控件，如果是EditText,则失去焦点并隐藏软键盘
        View view = getCurrentFocus();
        if (view != null && view instanceof EditText && isShouldHideInput(view, ev)){
            view.clearFocus();
            KeyboardUtils.hideSoftInput(view);
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onDestroy() {
        unBinder.unbind();
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    protected abstract int initLayoutRes();

    protected abstract void initDataAndEvent();

    private boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) { //如果点击的view是EditText
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

}
