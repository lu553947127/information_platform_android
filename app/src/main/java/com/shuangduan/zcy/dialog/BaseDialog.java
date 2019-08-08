package com.shuangduan.zcy.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import com.shuangduan.zcy.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * <pre>
 *     author : nwq
 *     time   : 2019/07/06
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public abstract class BaseDialog extends Dialog {

    public final String TAG = "BaseDialog";
    public Activity mActivity;
    private BaseDialog dialog;
    private Unbinder unbinder;

    public BaseDialog(@NonNull Activity activity) {
        super(activity);
        this.mActivity = activity;
        this.dialog = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(initLayout());
        unbinder = ButterKnife.bind(this);
        initData();
        initEvent();
    }

    abstract int initLayout();
    abstract void initData();
    abstract void initEvent();

    /**
     * 显示位置
     * @param gravity
     * @return
     */
    public BaseDialog setGravity(int gravity){
        if (getWindow() != null){
            getWindow().setGravity(gravity);
        }
        return this;
    }

    /**
     * 宽度
     * @return
     */
    public BaseDialog setWidth(int width){
        if (getWindow() != null){
            getWindow().getDecorView().setPadding(0,0,0,0);
            getWindow().getDecorView().setBackgroundResource(R.drawable.shape_fff_2);
            WindowManager.LayoutParams attributes = getWindow().getAttributes();
            attributes.width = width;
            getWindow().setAttributes(attributes);
        }
        return this;
    }

    /**
     * 高度
     * @return
     */
    public BaseDialog setHeight(int height){
        if (getWindow() != null){
            getWindow().getDecorView().setPadding(0,0,0,0);
            getWindow().getDecorView().setBackgroundResource(R.drawable.shape_fff_2);
            WindowManager.LayoutParams attributes = getWindow().getAttributes();
            attributes.height = height;
            getWindow().setAttributes(attributes);
        }
        return this;
    }

    /**
     * 点击外部消失
     * @param isCancel
     * @return
     */
    public BaseDialog setCancelOutside(boolean isCancel){
        dialog.setCanceledOnTouchOutside(isCancel);
        return this;
    }

    /**
     * 设置回退键是否有效
     * @param isCancel
     * @return
     */
    public BaseDialog setCancelableBack(boolean isCancel){
        dialog.setCancelable(isCancel);
        return this;
    }

    /**
     * 显示
     * @return
     */
    public BaseDialog showDialog(){
        dialog.show();
        return this;
    }

    /**
     * 隐藏
     * @return
     */
    public BaseDialog hideDialog(){
        dialog.hide();
        return this;
    }

    @Override
    public void hide() {
        unbinder.unbind();
        super.hide();
    }

    public CallBack callBack;

    public interface CallBack{
        void cancel();
        void ok(String s);
    }

    public BaseDialog setCallBack(CallBack callBack){
        this.callBack = callBack;
        return this;
    }

    public SingleCallBack singleCallBack;

    public interface SingleCallBack{
        void click(String item, int position);
    }

    public BaseDialog setSingleCallBack(SingleCallBack singleCallBack){
        this.singleCallBack = singleCallBack;
        return this;
    }

    public PhotoCallBack photoCallBack;

    public interface PhotoCallBack{
        void camera();
        void album();
    }

    public BaseDialog setPhotoCallBack(PhotoCallBack photoCallBack){
        this.photoCallBack = photoCallBack;
        return this;
    }

}
