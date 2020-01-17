package com.shuangduan.zcy.dialog;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
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
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.utils.TimeUtils;
import com.shuangduan.zcy.view.WebViewActivity;
import com.shuangduan.zcy.view.demand.FindBluePrintActivity;
import com.shuangduan.zcy.view.demand.FindFoundationActivity;
import com.shuangduan.zcy.view.demand.FindLogisticsActivity;
import com.shuangduan.zcy.view.design.SmartDesignActivity;
import com.shuangduan.zcy.weight.RenderScriptGaussianBlur;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.dialog
 * @ClassName: HomePageAddDialog
 * @Description: 首页各种找弹窗
 * @Author: 鹿鸿祥
 * @CreateDate: 2020/1/6 10:07
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2020/1/6 10:07
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class HomePageAddDialog extends PopupWindow implements View.OnClickListener {
    private Activity mContext;
    private RelativeLayout relativeLayout;
    private ImageView ivClose;
    private View view;
    private int mWidth;
    private int mHeight;
    private Handler mHandler;

    private int statusBarHeight;

    public HomePageAddDialog(Activity context, Handler mHandler) {
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
        relativeLayout = (RelativeLayout) LayoutInflater.from(mContext).inflate(R.layout.dialog_home_page_add, null);
        setContentView(relativeLayout);
        findView(relativeLayout);
        getIsPhone();

        setOutsideTouchable(true);
        setFocusable(true);
        setClippingEnabled(false);//全屏显示

        showMoreWindow(view);
        showAtLocation(view, Gravity.BOTTOM, 0, statusBarHeight);
    }

    //初始化
    @SuppressLint("SimpleDateFormat")
    private void findView(RelativeLayout layout) {
        ivClose = layout.findViewById(R.id.iv_close);
        view = layout.findViewById(R.id.rl);
        TextView tv_day = layout.findViewById(R.id.window_add_tv_day);
        TextView tv_week = layout.findViewById(R.id.window_add_tv_week);
        TextView tv_month = layout.findViewById(R.id.window_add_tv_month);
        TextView tv_weather = layout.findViewById(R.id.tv_weather);
        tv_weather.setOnClickListener(this);
        ivClose.setOnClickListener(this);
        layout.findViewById(R.id.window_home_add_ll_a).setOnClickListener(this);
        layout.findViewById(R.id.window_home_add_ll_b).setOnClickListener(this);
        layout.findViewById(R.id.window_home_add_ll_c).setOnClickListener(this);
        layout.findViewById(R.id.window_home_add_ll_d).setOnClickListener(this);
        //设置时间
        tv_day.setText(TimeUtils.getDayTime());
        tv_week.setText(TimeUtils.getWeekTime());
        tv_month.setText(TimeUtils.getYearMonthTime());
        //设置天气
        tv_weather.setText(SPUtils.getInstance().getString(SpConfig.WEATHER));
    }

    //适配各大机型
    private void getIsPhone() {
        Rect frame = new Rect();
        mContext.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        statusBarHeight = frame.top;
        DisplayMetrics metrics = new DisplayMetrics();
        mContext.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        mWidth = metrics.widthPixels;
        mHeight = metrics.heightPixels;

        String name = Build.MANUFACTURER;
        String moder = android.os.Build.MODEL;
        LogUtils.e(moder);
        switch (name) {
            case "HUAWEI":
                view.setBackgroundResource(0);
                mHeight = metrics.heightPixels + mContext.getResources().getDimensionPixelSize(R.dimen.dp_55);
                //设置毛玻璃背景
                setBackgroundDrawable(new BitmapDrawable(mContext.getResources(), blur()));
                break;
            case "vivo":
                view.setBackgroundResource(0);
                if (moder.equals("vivo X21A"))mHeight = metrics.heightPixels + mContext.getResources().getDimensionPixelSize(R.dimen.dp_55);
                //设置毛玻璃背景
                setBackgroundDrawable(new BitmapDrawable(mContext.getResources(), blur()));
                break;
            case "OPPO":
                view.setBackgroundResource(0);
                //设置毛玻璃背景
                setBackgroundDrawable(new BitmapDrawable(mContext.getResources(), blur()));
                break;
            case "Coolpad":
                view.setBackgroundResource(0);
                //设置毛玻璃背景
                setBackgroundDrawable(new BitmapDrawable(mContext.getResources(), blur()));
                break;
            case "Meizu":
                view.setBackgroundResource(0);
                //设置毛玻璃背景
                setBackgroundDrawable(new BitmapDrawable(mContext.getResources(), blur()));
                break;
            case "Xiaomi":
                view.setBackgroundResource(R.color.colorFFF);
                view.getBackground().setAlpha(245);
                break;
            case "samsung":
                view.setBackgroundResource(R.color.colorFFF);
                view.getBackground().setAlpha(245);
                break;
            case "Sony":
                view.setBackgroundResource(R.color.colorFFF);
                view.getBackground().setAlpha(245);
                break;
            case "LG":
                view.setBackgroundResource(0);
                //设置毛玻璃背景
                setBackgroundDrawable(new BitmapDrawable(mContext.getResources(), blur()));
                break;
            case "OnePlus":
                view.setBackgroundResource(0);
                //设置毛玻璃背景
                setBackgroundDrawable(new BitmapDrawable(mContext.getResources(), blur()));
                break;
            case "Google":
                view.setBackgroundResource(0);
                //设置毛玻璃背景
                setBackgroundDrawable(new BitmapDrawable(mContext.getResources(), blur()));
                break;
            default:
                view.setBackgroundResource(0);
                //设置毛玻璃背景
                setBackgroundDrawable(new BitmapDrawable(mContext.getResources(), blur()));
                break;
        }

        setWidth(mWidth);
        setHeight(mHeight);
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
        Bundle bundle = new Bundle();
        switch (v.getId()) {
            case R.id.iv_close://关闭按钮
                if (isShowing()) {
                    closeAnimation();
                }
                break;
            case R.id.window_home_add_ll_a://智能设计
                ActivityUtils.startActivity(SmartDesignActivity.class);
                break;
            case R.id.window_home_add_ll_b://找基地
                ActivityUtils.startActivity(FindFoundationActivity.class);
                break;
            case R.id.window_home_add_ll_c://找物流
                ActivityUtils.startActivity(FindLogisticsActivity.class);
                break;
            case R.id.window_home_add_ll_d://找方案
                ActivityUtils.startActivity(FindBluePrintActivity.class);
                break;
            case R.id.tv_weather://天气详情
                bundle.putString("register", "weather");
                ActivityUtils.startActivity(bundle, WebViewActivity.class);
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
            float b = startValue;
            float c = endValue - startValue;
            float d = mDuration;
            return calculate(t, b, c, d);
        }

        Float calculate(float t, float b, float c, float d) {
            return c * ((t = t / d - 1) * t * ((s + 1) * t + s) + 1) + b;
        }
    }

    //高斯毛玻璃背景设置
    private Bitmap blur() {
        Bitmap overlay = null;

        View view = mContext.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache(true);
        Bitmap mBitmap = view.getDrawingCache();

        float scaleFactor = 8;//图片缩放比例
        float radius = 10;//模糊程度
        int width = mBitmap.getWidth();
        int height = mBitmap.getHeight();

        overlay = Bitmap.createBitmap((int) (width / scaleFactor), (int) (height / scaleFactor), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(overlay);
        canvas.scale(1 / scaleFactor, 1 / scaleFactor);
        Paint paint = new Paint();
        paint.setFlags(Paint.FILTER_BITMAP_FLAG);
        canvas.drawBitmap(mBitmap, 0, 0, paint);

        overlay = RenderScriptGaussianBlur.doBlur(overlay, (int) radius, true);
        return overlay;
    }
}
