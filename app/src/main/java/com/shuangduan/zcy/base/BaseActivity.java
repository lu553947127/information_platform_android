package com.shuangduan.zcy.base;

import android.os.Bundle;
import android.util.SparseArray;
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
import com.shuangduan.zcy.dialog.BaseDialog;
import com.shuangduan.zcy.dialog.LoadDialog;
import com.shuangduan.zcy.factory.EmptyViewFactory;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.swipeback.SwipeBackActivityBase;
import com.shuangduan.zcy.swipeback.SwipeBackActivityHelper;
import com.shuangduan.zcy.swipeback.SwipeBackLayout;
import com.shuangduan.zcy.swipeback.SwipeBackUtils;
import com.shuangduan.zcy.utils.AutoUtils;

import org.greenrobot.eventbus.EventBus;

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

public abstract class BaseActivity extends AppCompatActivity implements IView, SwipeBackActivityBase {

    private Unbinder unBinder;
    public boolean isTranslationBar = false;//透明状态栏开关
    private LoadDialog loadDialog;
    private SparseArray<BaseDialog> dialogArray = new SparseArray<>();

    private SwipeBackActivityHelper mHelper;
    public EmptyViewFactory emptyViewFactory;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //适配初始化
        AutoUtils.setCustomDensity(this, MyApplication.getInstance());
        setContentView(initLayoutRes());
        unBinder = ButterKnife.bind(this);
        if (isUseEventBus()) {
            EventBus.getDefault().register(this);
        }

        if (!isTranslationBar) {
            //主题色状态栏
            BarUtils.setStatusBarColor(this, ContextCompat.getColor(Utils.getApp(), R.color.colorStatusBar), false);
        } else {
            //透明状态栏
            BarUtils.setStatusBarColor(this, ContextCompat.getColor(Utils.getApp(), android.R.color.transparent), false);
        }

        //初始化空页面工程
        emptyViewFactory = EmptyViewFactory.newInstance(this);

        initDataAndEvent(savedInstanceState);

        //右滑退出初始化
        mHelper = new SwipeBackActivityHelper(this);
        mHelper.onActivityCreate();
    }

    @Override
    public void showLoading() {
        if (loadDialog == null) {
            loadDialog = new LoadDialog(this);
        }
        loadDialog.showDialog();
    }

    @Override
    public void hideLoading() {
        if (loadDialog != null) {
            loadDialog.hideDialog();
        }
    }

    /**
     * 加载状态处理
     */
    public void showPageState(String s) {
        switch (s) {
            case PageState.PAGE_LOADING:
                showLoading();
                break;
            default:
                hideLoading();
                break;
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //获取当前焦点控件，如果是EditText,则失去焦点并隐藏软键盘
        View view = getCurrentFocus();
        if (view != null && view instanceof EditText && isShouldHideInput(view, ev)) {
            view.clearFocus();
            KeyboardUtils.hideSoftInput(view);
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 保险起见的dialog关闭，防止内存泄漏
     */
    public void addDialog(BaseDialog dialog) {
        dialogArray.put(dialogArray.size(), dialog);
    }

    @Override
    protected void onDestroy() {
        unBinder.unbind();
        if (isUseEventBus() && EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        if (loadDialog != null) {
            loadDialog.dismiss();
            loadDialog = null;
        }
        for (int i = 0; i < dialogArray.size(); i++) {
            if (dialogArray.get(i) != null) {
                dialogArray.get(i).dismiss();
            }
        }
        dialogArray = null;
        super.onDestroy();
    }

    protected abstract int initLayoutRes();

    /**
     * EventBus开关
     */
    public abstract boolean isUseEventBus();

    protected abstract void initDataAndEvent(Bundle savedInstanceState);

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


    //以下为右滑退出重写方法
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mHelper.onPostCreate();
    }

    @Override
    public View findViewById(int id) {
        View v = super.findViewById(id);
        if (v == null && mHelper != null)
            return mHelper.findViewById(id);
        return v;
    }

    @Override
    public SwipeBackLayout getSwipeBackLayout() {
        if (mHelper != null) {
            return mHelper.getSwipeBackLayout();
        }
        return null;
    }

    @Override
    public void setSwipeBackEnable(boolean enable) {
        getSwipeBackLayout().setEnableGesture(enable);
    }

    @Override
    public void scrollToFinishActivity() {
        SwipeBackUtils.convertActivityToTranslucent(this);
        getSwipeBackLayout().scrollToFinishActivity();
    }


}
