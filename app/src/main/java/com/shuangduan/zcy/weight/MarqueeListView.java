package com.shuangduan.zcy.weight;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.widget.TintTypedArray;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.Utils;
import com.shuangduan.zcy.R;

import java.util.List;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.weight
 * @class describe  跑马灯横向滚动列表
 * @time 2019/7/13 16:42
 * @change
 * @chang time
 * @class describe
 */
public class MarqueeListView extends View implements Runnable {
    private static final String TAG = "MarqueeView";
    private String string;//最终绘制的文本
    private float speed = 1;//移动速度
    private int textColor = Color.BLACK;//文字颜色,默认黑色
    private float textSize = 13;//文字颜色,默认黑色
    private int textDistance;//
    private int textDistance1= 10;//item间距，dp单位
    private String black_count = "";//间距转化成空格距离

    private int repeatType = REPEAT_INTERVAL;//滚动模式
    public static final int REPEAT_ONCE = 0;//一次结束
    public static final int REPEAT_INTERVAL = 1;//一次结束以后，再继续第二次
    public static final int REPEAT_CONTINUOUS = 2;//紧接着 滚动第二次

    private float startLocationDistance = 1.0f;//开始的位置选取，百分比来的，距离左边，0~1，0代表不间距，1的话代表，从右面，1/2代表中间。

    private boolean isClickStop = false;//点击是否暂停
    private boolean isResetLocation = true;//默认为true
    private float xLocation = 0;//文本的x坐标
    private int contentWidth;//内容的宽度

    private boolean isRoll = false;//是否继续滚动
    private float oneBlack_width;//空格的宽度

    private TextPaint paint;//画笔
    private Rect rect;

    private int repeatCount = 0;//
    private boolean resetInit = true;

    private Thread thread;
    private String content = "";

    private float textHeight;
    private List<String> textList;
    private static int currentPosition;

    public MarqueeListView(Context context) {
        this(context, null);
    }

    public MarqueeListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MarqueeListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        textDistance1 = ConvertUtils.px2dp(getScreenWidth());
//        LogUtils.i("屏宽",getScreenWidth(), textDistance1);
        initAttrs(attrs);
        initPaint();
        initClick();
    }

    private void initClick() {
        setOnClickListener(v -> {
            if (isClickStop) {
                if (isRoll) {
                    stopRoll();
                } else {
                    continueRoll();
                }
            }
        });
    }

    @SuppressLint("RestrictedApi")
    private void initAttrs(AttributeSet attrs) {
        TintTypedArray tta = TintTypedArray.obtainStyledAttributes(getContext(), attrs,
                R.styleable.MarqueeView);

        textColor = tta.getColor(R.styleable.MarqueeView_marqueeview_text_color, textColor);
        isClickStop = tta.getBoolean(R.styleable.MarqueeView_marqueeview_isclickalbe_stop, isClickStop);
        isResetLocation = tta.getBoolean(R.styleable.MarqueeView_marqueeview_is_resetLocation, isResetLocation);
        speed = tta.getFloat(R.styleable.MarqueeView_marqueeview_text_speed, speed);
        textSize = tta.getFloat(R.styleable.MarqueeView_marqueeview_text_size, textSize);
        textDistance1 = tta.getInteger(R.styleable.MarqueeView_marqueeview_text_distance, textDistance1);
        startLocationDistance = tta.getFloat(R.styleable.MarqueeView_marqueeview_text_startlocationdistance, startLocationDistance);
        repeatType = tta.getInt(R.styleable.MarqueeView_marqueeview_repet_type, repeatType);
        tta.recycle();
    }


    /**
     * 刻字机修改
     */
    private void initPaint() {
        rect = new Rect();
        paint = new TextPaint(Paint.ANTI_ALIAS_FLAG);//初始化文本画笔
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(textColor);//文字颜色值,可以不设定
        paint.setTextSize(dp2px(textSize));//文字大小
    }

    public int dp2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (resetInit) {
            setTextDistance(textDistance1);

            if (startLocationDistance < 0) {
                startLocationDistance = 0;
            } else if (startLocationDistance > 1) {
                startLocationDistance = 1;
            }
            xLocation = getWidth() * startLocationDistance;
            resetInit = false;
        }

        //需要判断滚动模式的
        switch (repeatType) {
            case REPEAT_ONCE:
                if (contentWidth < (-xLocation)) {
                    //也就是说文字已经到头了
                    if (locationListener != null){
                        locationListener.end();
                    }
                    //此时停止线程就可以了
                    stopRoll();
                }
                break;
            case REPEAT_INTERVAL:
                if (contentWidth <= (-xLocation)) {
                    //也就是说文字已经到头了
                    if (locationListener != null){
                        locationListener.end();
                    }
                    //重置文字内容
                    resetContent();
                    xLocation = getWidth();
                }
                break;

            case REPEAT_CONTINUOUS:
                if (xLocation < 0) {
                    int beAppend = (int) ((-xLocation) / contentWidth);

                    Log.e(TAG, "onDraw: ---" + contentWidth + "--------" + (-xLocation) + "------" + beAppend);

                    if (beAppend >= repeatCount) {
                        repeatCount++;
                        //也就是说文字已经到头了
                        if (locationListener != null){
                            locationListener.end();
                        }
//                    xLocation = speed;//这个方法有问题，所以采取了追加字符串的 方法
                        string = string + content;
                    }
                }
                //此处需要判断的xLocation需要加上相应的宽度
                break;

            default:
                //默认一次到头好了
                if (contentWidth < (-xLocation)) {
                    //也就是说文字已经到头了
                    if (locationListener != null){
                        locationListener.end();
                    }
//                    此时停止线程就可以了
                    stopRoll();
                }
                break;
        }

        //文字显露
        if (xLocation == getWidth()){
//            LogUtils.i("发出显露消息");
            if (locationListener != null){
                locationListener.start(currentPosition);
            }
        }

        //把文字画出来
        if (string != null) {
            canvas.drawText(string, xLocation, getHeight() / 2 + textHeight / 2, paint);
        }

    }

    public void setRepeatType(int repeatType) {
        this.repeatType = repeatType;
        resetInit = true;
        setContent(content);
    }

    /**
     * 重置文字内容
     */
    private void resetContent(){
        if (textList != null && textList.size() > 0){
            currentPosition++;
            if (currentPosition >= textList.size()){
                currentPosition = 0;
            }
            setContent(textList.get(currentPosition));
        }
    }

    @Override
    public void run() {
        while (isRoll && !TextUtils.isEmpty(content)) {
            try {
                Thread.sleep(10);
                xLocation = xLocation - speed;
                postInvalidate();//每隔10毫秒重绘视图
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 继续滚动
     */
    public void continueRoll() {
        if (!isRoll) {
            if (thread != null) {
                thread.interrupt();

                thread = null;
            }

            isRoll = true;
            thread = new Thread(this);
            thread.start();//开启死循环线程让文字动起来

        }
    }

    /**
     * 停止滚动
     */
    public void stopRoll() {
        isRoll = false;
        if (thread != null) {
            thread.interrupt();
            thread = null;
        }

    }

    /**
     * 点击是否暂停，默认是不
     *
     * @param isClickStop
     */
    private void setClickStop(boolean isClickStop) {
        this.isClickStop = isClickStop;
    }

    /**
     * 是否循环滚动
     *
     * @param isContinuable
     */
    private void setContinueble(int isContinuable) {
        this.repeatType = isContinuable;
    }

    /**
     * 设置文字间距  不过如果内容是List形式的，该方法不适用 ,list的数据源，必须在设置setContent之前调用此方法。
     * @param textdistance2
     */
    public void setTextDistance(int textdistance2) {
        //设置之后就需要初始化了
        String black = " ";
        oneBlack_width = getBlacktWidth();//空格的宽度
        textdistance2 = dp2px(textdistance2);
        int count = (int) (textdistance2 / oneBlack_width);//空格个数，有点粗略，有兴趣的朋友可以精细

        if (count == 0) {
            count = 1;
        }

        textDistance = (int) (oneBlack_width * count);
        black_count = "";
        for (int i = 0; i <= count; i++) {
            black_count = black_count + black;//间隔字符串
        }
        setContent(content);//设置间距以后要重新刷新内容距离，不过如果内容是List形式的，该方法不适用
    }

    /**
     * 计算出一个空格的宽度
     * @return
     */
    private float getBlacktWidth() {
        String text1 = "en en";
        String text2 = "enen";
        return getContentWidth(text1) - getContentWidth(text2);
    }

    private float getContentWidth(String black) {
        if (black == null || black == "") {
            return 0;
        }

        if (rect == null) {
            rect = new Rect();
        }
        paint.getTextBounds(black, 0, black.length(), rect);
        textHeight = getContentHeight();

        return rect.width();
    }

    /**
     * http://blog.csdn.net/u014702653/article/details/51985821
     * 详细解说了
     *
     * @param
     * @return
     */
    private float getContentHeight() {
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        return Math.abs((fontMetrics.bottom - fontMetrics.top)) / 2;
    }

    /**
     * 设置文字颜色
     *
     * @param textColor
     */
    public void setTextColor(int textColor) {
        if (textColor != 0) {
            this.textColor = textColor;
            paint.setColor(getResources().getColor(textColor));//文字颜色值,可以不设定
        }
    }

    /**
     * 设置文字大小
     *
     * @param textSize
     */
    public void setTextSize(float textSize) {
        if (textSize > 0) {
            this.textSize = textSize;
            paint.setTextSize(dp2px(textSize));//文字颜色值,可以不设定
            contentWidth = (int) (getContentWidth(content) + textDistance);//大小改变，需要重新计算宽高
        }
    }

    /**
     * 设置滚动速度
     *
     * @param speed
     */
    public void setTextSpeed(float speed) {
        this.speed = speed;
    }


    /**
     * |设置滚动的条目内容 ， 集合形式的
     *
     * @param strings
     */
    public void setContent(List<String> strings) {
        this.textList = strings;
        if (strings != null && strings.size() > 0){
            currentPosition = 0;
            //直接从第一条开始走
            setContent(strings.get(currentPosition));
        }
    }

    /**
     * 设置滚动的条目内容  字符串形式的
     *
     * @parambt_control00
     */
    private void setContent(String content2) {
        if (TextUtils.isEmpty(content2)){
            return;
        }
        if (isResetLocation) {//控制重新设置文本内容的时候，是否初始化xLocation。
            xLocation = getWidth() * startLocationDistance;
        }

        if (!content2.endsWith(black_count)) {
            content2 = content2 + black_count;//避免没有后缀
        }
        this.content = content2;

        //这里需要计算宽度啦，当然要根据模式来搞
        if (repeatType == REPEAT_CONTINUOUS) {
//如果说是循环的话，则需要计算 文本的宽度 ，然后再根据屏幕宽度 ， 看能一个屏幕能盛得下几个文本

            contentWidth = (int) (getContentWidth(content) + textDistance);//可以理解为一个单元内容的长度
            //从0 开始计算重复次数了， 否则到最后 会跨不过这个坎而消失。
            repeatCount = 0;
            int contentCount = (getWidth() / contentWidth) + 2;
            this.string = "";
            for (int i = 0; i <= contentCount; i++) {
                this.string = this.string + this.content;//根据重复次数去叠加。
            }

        } else {
            if (xLocation < 0 && repeatType == REPEAT_ONCE) {
                if (-xLocation > contentWidth) {
                    xLocation = getWidth() * startLocationDistance;
                }
            }
            contentWidth = (int) getContentWidth(content);

            this.string = content2;
        }

        if (!isRoll) {//如果没有在滚动的话，重新开启线程滚动
            continueRoll();
        }


    }

    /**
     * 从新添加内容的时候，是否初始化位置
     *
     * @param isReset
     */
    private void setResetLocation(boolean isReset) {
        isResetLocation = isReset;
    }

    public void appendContent(String appendContent) {
        //有兴趣的朋友可以自己完善，在现有的基础之上，静默追加新的 公告
    }

    /**
     * Return the width of screen, in pixel.
     *
     * @return the width of screen, in pixel
     */
    private int getScreenWidth() {
        WindowManager wm = (WindowManager) Utils.getApp().getSystemService(Context.WINDOW_SERVICE);
        Point point = new Point();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            //noinspection ConstantConditions
            wm.getDefaultDisplay().getRealSize(point);
        } else {
            //noinspection ConstantConditions
            wm.getDefaultDisplay().getSize(point);
        }
        return point.x;
    }

    private LocationListener locationListener;

    public void setLocationListener(LocationListener locationListener) {
        this.locationListener = locationListener;
    }

    public interface LocationListener {

        /**
         * 文字进入视野
         */
        public void start(int position);

        /**
         * 文字走出视野
         */
        public void end();
    }

}
