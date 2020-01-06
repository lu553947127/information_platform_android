package com.shuangduan.zcy.dialog;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Build;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.shuangduan.zcy.R;
import com.shuangduan.zcy.utils.PhoneUtils;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.dialog
 * @ClassName: HomePageAddDialog
 * @Description: java类作用描述
 * @Author: 鹿鸿祥
 * @CreateDate: 2020/1/6 10:07
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2020/1/6 10:07
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class HomePageAddDialog extends PopupWindow implements View.OnClickListener  {

    private Activity mContext;
    private RelativeLayout relativeLayout;
    private ImageView ivClose;
    private View view;
    private int mWidth;
    private int mHeight;
    private Handler mHandler;

    public HomePageAddDialog(Activity context,Handler mHandler) {
        this.mContext = context;
        this.mHandler = mHandler;
    }

    /**
     * 初始化
     *
     * @param view 要显示的模糊背景View,一般选择跟布局layout
     */
    @SuppressLint("InflateParams")
    public void init(View view) {
        getStatusBarHeight();
        relativeLayout = (RelativeLayout) LayoutInflater.from(mContext).inflate(R.layout.dialog_home_page_add, null);
        setContentView(relativeLayout);
        findView(relativeLayout);
        setOutsideTouchable(true);
        setFocusable(true);
        setClippingEnabled(false);//全屏显示
        showMoreWindow(view);
    }

    //获取宽高
    private void getStatusBarHeight() {
        Rect frame = new Rect();
        mContext.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        DisplayMetrics metrics = new DisplayMetrics();
        mContext.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        mWidth = metrics.widthPixels;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            if(PhoneUtils.isPhone(mContext)) {
                mHeight = metrics.heightPixels + mContext.getResources().getDimensionPixelSize(R.dimen.dp_55);
            }else{
                mHeight = metrics.heightPixels;
            }
        } else {
            mHeight = metrics.heightPixels;
        }
        setWidth(mWidth);
        setHeight(mHeight);
    }

    //初始化
    private void findView(RelativeLayout layout) {
        ivClose =layout.findViewById(R.id.iv_close);
        view = layout.findViewById(R.id.rl);
        ivClose.setOnClickListener(this);
    }

   //显示window动画
   private void showMoreWindow(View anchor) {
        showAtLocation(anchor, Gravity.TOP | Gravity.START, 0, 0);
        mHandler.post(() -> {
            try {
                //圆形扩展的动画
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    int x = mWidth / 2;
                    int y = (int) (mHeight - fromDpToPx());
                    Animator animator = ViewAnimationUtils.createCircularReveal(view, x, y, 0, view.getHeight());
                    animator.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {

                        }
                    });
                    animator.start();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        showAnimation(relativeLayout);
    }

    //打开动画
    private void showAnimation(ViewGroup layout) {
        try {
            LinearLayout linearLayout = layout.findViewById(R.id.lin);
            //＋ 旋转动画
            mHandler.post(() -> ivClose.animate().rotation(90).setDuration(800));
            //菜单项弹出动画
            for (int i = 0; i < linearLayout.getChildCount(); i++) {
                final View child = linearLayout.getChildAt(i);
                child.setOnClickListener(this);
                child.setVisibility(View.INVISIBLE);
                mHandler.postDelayed(() -> {
                    child.setVisibility(View.VISIBLE);
                    ValueAnimator fadeAnim = ObjectAnimator.ofFloat(child, "translationY", 600, 0);
                    fadeAnim.setDuration(500);
                    KickBackAnimator kickAnimator = new KickBackAnimator();
                    kickAnimator.setDuration(500);
                    fadeAnim.setEvaluator(kickAnimator);
                    fadeAnim.start();
                }, i * 50 + 100);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //关闭window动画
    private void closeAnimation() {
        mHandler.post(() -> ivClose.animate().rotation(-90).setDuration(400));
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                int x = mWidth / 2;
                int y = (int) (mHeight - fromDpToPx());
                Animator animator = ViewAnimationUtils.createCircularReveal(view, x, y, view.getHeight(), 0);
                animator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        dismiss();
                    }
                });
                animator.setDuration(300);
                animator.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

   //点击事件处理
    @Override
    public void onClick(View v) {
        if (isShowing()) {
            closeAnimation();
        }
        switch (v.getId()) {
            case R.id.iv_close://关闭按钮
                if (isShowing()) {
                    closeAnimation();
                }
                break;
        }

    }

    private float fromDpToPx() {
        return (float) 25 * Resources.getSystem().getDisplayMetrics().density;
    }

    //仿微博，酷安点击加号揭露动画弹出菜单+背景模糊效果
    public class KickBackAnimator implements TypeEvaluator<Float> {
        private final float s = 1.70158f;
        float mDuration = 0f;

        public void setDuration(float duration) {
            mDuration = duration;
        }

        public Float evaluate(float fraction, Float startValue, Float endValue) {
            float t = mDuration * fraction;
            float b = startValue.floatValue();
            float c = endValue.floatValue() - startValue.floatValue();
            float d = mDuration;
            float result = calculate(t, b, c, d);
            return result;
        }

        public Float calculate(float t, float b, float c, float d) {
            return c * ((t = t / d - 1) * t * ((s + 1) * t + s) + 1) + b;
        }
    }
}
