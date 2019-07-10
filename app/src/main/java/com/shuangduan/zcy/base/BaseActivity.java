package com.shuangduan.zcy.base;

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
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.app.MyApplication;
import com.shuangduan.zcy.dialog.LoadDialog;
import com.shuangduan.zcy.model.event.NormalEvent;
import com.shuangduan.zcy.utils.AutoUtils;

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
    private LoadDialog loadDialog;

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
        if (loadDialog == null){
            loadDialog = new LoadDialog(this);
        }
        if (!loadDialog.isShowing()){
            loadDialog.showDialog();
        }
    }

    @Override
    public void hideLoading() {
        if (loadDialog != null && loadDialog.isShowing()){
            loadDialog.hideDialog();
        }
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
        if (loadDialog != null){
            if (loadDialog.isShowing()) loadDialog.hideDialog();
            loadDialog = null;
        }
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
