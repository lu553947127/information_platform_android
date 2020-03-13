package com.shuangduan.zcy.weight;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.LevelListDrawable;
import android.text.Editable;
import android.text.Html;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.LeadingMarginSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.blankj.utilcode.util.LogUtils;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.shuangduan.zcy.R;

import org.xml.sax.XMLReader;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <pre>
 *     author : 徐玉
 *     e-mail : ningwenqiang@lanhuiplay.com
 *     time   : 2018/11/27
 *     desc   : 带查看图片，可点击网址链接监听的h5标签查看器
 *     richText.setGlide(Glide.with(this));
 *         richText.setOnRichClickListener(new RichText.CustomRichClickListener(){
 *             @Override
 *             public void onNormalUrl(String url) {
 *                 Bundle bundle = new Bundle();
 *                 bundle.putString(CustomConfig.TITLE, getString(R.string.app_name));
 *                 bundle.putString(CustomConfig.URL, url);
 *                 ActivityUtils.startActivity(bundle, WebActivity.class);
 *             }
 *
 *             @Override
 *             public void OnImg(String imgUrl) {
 *                 startPhotoActivity(imgUrl);
 *             }
 *         });
 *     tvContent.setHtml(headlinesDetailBean.getContent());
 *     version: 1.0
 * </pre>
 */

public class RichText extends AppCompatTextView {

    private static final int TOPIC_TAG = 0;
    private static final int AT_USER = 1;
    private static final int NORMAL_URL = 2;
    private static final int CUSTOM_TAG = 3;
    private static final int IMG = 4;
    private static final int DOC_URL = 5;

    private Context mContext;
    private OnRichClickListener onRichClickListener;

    private float density;
    private int screenWidth;

    private RequestManager glideRm;
    private Map<String, LevelListDrawable> drawableMap = new HashMap<>();

    public RichText(Context context) {
        this(context, null);
    }

    public RichText(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RichText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;

        WindowManager wm = (WindowManager) mContext.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        assert wm != null;
        wm.getDefaultDisplay().getMetrics(metrics);
        density = metrics.density;
        screenWidth = metrics.widthPixels;

        setTextIsSelectable(true);
        setClickable(true);
        setMovementMethod(LinkMovementMethod.getInstance());

//        setIncludeFontPadding(false);
//        setLineSpacing(density * 5, 1);
        setHighlightColor(getResources().getColor(R.color.colorPrimary));
    }

    private CharSequence getClickableHtml(String html) {
        html = html.replaceAll("span", "androidspan");
        LogUtils.i(html);

        try {
            Html.ImageGetter mImageGetter = initImageGetter();
            Spanned spannedHtml = Html.fromHtml(html, mImageGetter, new CustomTagHandler());
            SpannableStringBuilder clickableHtmlBuilder = new SpannableStringBuilder(spannedHtml);
            URLSpan[] urls = clickableHtmlBuilder.getSpans(0, spannedHtml.length(), URLSpan.class);

            for (final URLSpan span : urls) {
                int start = clickableHtmlBuilder.getSpanStart(span);
                int end = clickableHtmlBuilder.getSpanEnd(span);
                int flag = clickableHtmlBuilder.getSpanFlags(span);
                final String url = span.getURL();
                setUpUrl(clickableHtmlBuilder, span, start, end, flag, url);
            }

            return clickableHtmlBuilder;
        }catch (Exception e){
            LogUtils.e(e.getMessage(), e.getCause());
        }

        return null;
    }

    @NonNull
    private Html.ImageGetter initImageGetter() {
        //处理图片
        //加载网络图片
        return source -> {

            final LevelListDrawable drawable = new LevelListDrawable();
            drawableMap.put(source, drawable);
            if (glideRm == null){
                throw new NullPointerException("没有注入Glide.with(context)");
            }
            glideRm.asBitmap()
                    .apply(new RequestOptions().placeholder(R.drawable.wuzhi_default))
                    .load(source).into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {

                    int corner = 8;
                    int width = (int) (resource.getWidth() * density);
                    int height = (int) (resource.getHeight() * density);
                    int mWidth = getWidth();

                    if (width > mWidth){
                        if (mWidth != 0){
                            height = height * mWidth/width;
                            corner = corner * width/mWidth;
                            width = mWidth;
                        }else if (width > screenWidth){
                            //列表中有时取不到宽度，取屏幕一半
                            mWidth = screenWidth/2;
                            height = height * mWidth/width;
                            corner = corner * width/mWidth;
                            width = mWidth;
                        }
                    }

                    Bitmap roundedCornerBitmap = getRoundedCornerBitmap(resource, corner);
                    BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), roundedCornerBitmap);
                    drawable.addLevel(1, 1, bitmapDrawable);
                    drawable.setBounds(0, 0, width, height);
                    drawable.setLevel(1);

                    setText(getText());
                    refreshDrawableState();
                }

            });
            return drawableMap.get(source);
        };
    }

    private Bitmap getRoundedCornerBitmap(Bitmap bitmap,float roundPx){

        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap
                .getHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

    private void setUpUrl(SpannableStringBuilder clickableHtmlBuilder, URLSpan span, int start, int end, int flag, final String url) {
        CustomClickableSpan clickableSpan = null;
        if (url.startsWith("@")) {
            clickableSpan = new CustomClickableSpan(AT_USER, url);
        } else if (url.startsWith("#")) {
            clickableSpan = new CustomClickableSpan(TOPIC_TAG, url);
        } else if (url.startsWith("http") || url.startsWith("https")) {
            if (url.endsWith("doc") || url.endsWith("docx") || url.endsWith("xls") || url.endsWith("xlsx") || url.endsWith("ppt") || url.endsWith("pptx")){
                //在线文档
                clickableSpan = new CustomClickableSpan(DOC_URL, url);
            }else {
                clickableSpan = new CustomClickableSpan(NORMAL_URL, url);
            }
        }
        clickableHtmlBuilder.removeSpan(span);//清除当前span
        clickableHtmlBuilder.setSpan(clickableSpan, start, end, flag);
    }


    //处理点击事件
    class CustomClickableSpan extends ClickableSpan {
        String text;
        int type;

        public CustomClickableSpan(int type, String text) {
            this.text = text;
            this.type = type;
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            int color = Color.parseColor("#0000ff");
            ds.setColor(color);//设置点击文字的颜色
            ds.setUnderlineText(true); //去掉下划线
        }

        @Override
        public void onClick(View widget) {
            //点击事件处理
            Intent intent = null;
            switch (type) {
                case AT_USER://@用户
                    if (onRichClickListener != null)
                        onRichClickListener.onAtUser(text);
                    break;
                case TOPIC_TAG://#话题#
                    if (onRichClickListener != null)
                        onRichClickListener.onTopicTag(text);
                    break;
                case DOC_URL://在线文档
                    if (onRichClickListener != null)
                        onRichClickListener.onNormalUrl("https://view.officeapps.live.com/op/view.aspx?src=" + text);
                    break;
                case NORMAL_URL://网页
                    if (onRichClickListener != null)
                        onRichClickListener.onNormalUrl(text);
                    break;
                case CUSTOM_TAG://自定义标签
                    if (onRichClickListener != null)
                        onRichClickListener.onCustomTag(text);
                    break;
                case IMG://图片
                    if (onRichClickListener != null)
                        onRichClickListener.OnImg(text);
                    break;
            }
        }
    }

    //处理自定义标签
    class CustomTagHandler implements Html.TagHandler {
        private int startIndex = 0;
        private int stopIndex = 0;
        private int pStartIndex= 0;
        private int pEndIndex= 0;

        @Override
        public void handleTag(boolean opening, String tag, Editable output, XMLReader xmlReader) {

            if (tag.toLowerCase().equals("tag")) {//自定义标签处理
                if (opening) {//开始标签
                    startTag(tag, output, xmlReader);
                } else {//结束标签
                    endTag(tag, output, xmlReader);
                }
            }

            if (tag.toLowerCase().equals("img")) {
                int length = output.length();
                ImageSpan[] imageSpans = output.getSpans(length - 1, length, ImageSpan.class);
                output.setSpan(new CustomClickableSpan(IMG, imageSpans[0].getSource()), length - 1, length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }

            if (tag.equalsIgnoreCase("androidspan")) {
                if (opening) {
                    processAttributes(xmlReader, "style");
                    startSpan(tag, output, xmlReader);
                } else {
                    endSpan(tag, output, xmlReader);
                    String pStyle = attributes.get("pStyle");
                    attributes.clear();
                    attributes.put("pStyle", pStyle);
                }
            }

            if (tag.equalsIgnoreCase("androidp")) {
                if (opening) {
                    processAttributes(xmlReader, "pStyle");
                    startP(tag, output, xmlReader);
                } else {
                    endP(tag, output, xmlReader);
                    attributes.clear();
                }
            }

        }

        public void startTag(String tag, Editable output, XMLReader xmlReader) {
            startIndex = output.length();
        }

        public void endTag(String tag, Editable output, XMLReader xmlReader) {
            stopIndex = output.length();
            String content = output.subSequence(startIndex, stopIndex).toString();//获取点击内容
            output.setSpan(new CustomClickableSpan(CUSTOM_TAG, content), startIndex, stopIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        public void startSpan(String tag, Editable output, XMLReader xmlReader) {
            startIndex = output.length();
        }

        public void endSpan(String tag, Editable output, XMLReader xmlReader) {
            stopIndex = output.length();
            String color = attributes.get("color");
            String size = attributes.get("size");
            String style = attributes.get("style");
            if (!TextUtils.isEmpty(style)) {
                analysisStyle(startIndex, stopIndex, output, style);
            }
            if (!TextUtils.isEmpty(size)) {
                size = size.split("px")[0];
            }
            if (!TextUtils.isEmpty(color)) {
                if (color.startsWith("@")) {
                    Resources res = Resources.getSystem();
                    String name = color.substring(1);
                    int colorRes = res.getIdentifier(name, "color", "android");
                    if (colorRes != 0) {
                        output.setSpan(new ForegroundColorSpan(colorRes), startIndex, stopIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }
                } else {
                    try {
                        output.setSpan(new ForegroundColorSpan(Color.parseColor(color)), startIndex, stopIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    } catch (Exception e) {
                        e.printStackTrace();
                        reductionFontColor(startIndex, stopIndex, output);
                    }
                }
            }
            if (!TextUtils.isEmpty(size)) {
                int fontSizePx = 16;
                fontSizePx = (int) (getResources().getDisplayMetrics().scaledDensity * Integer.parseInt(size));
                output.setSpan(new AbsoluteSizeSpan(fontSizePx), startIndex, stopIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }

        public void startP(String tag, Editable output, XMLReader xmlReader) {
            pStartIndex = output.length();
        }

        public void endP(String tag, Editable output, XMLReader xmlReader) {
            LogUtils.i(output.toString());
            pEndIndex = output.length();
            String style = attributes.get("pStyle");

            if (!TextUtils.isEmpty(style)) {
                analysisStyle(pStartIndex, pEndIndex , output, style);
            }

            //Html中的P标签处理
            startBlockElement(output, style, getMarginParagraph());
            startCssStyle(output, style);
        }

        final HashMap<String, String> attributes = new HashMap<String, String>();

        private void processAttributes(final XMLReader xmlReader, String key) {
            try {
                Field elementField = xmlReader.getClass().getDeclaredField("theNewElement");
                elementField.setAccessible(true);
                Object element = elementField.get(xmlReader);
                Field attsField = element.getClass().getDeclaredField("theAtts");
                attsField.setAccessible(true);
                Object atts = attsField.get(element);
                Field dataField = atts.getClass().getDeclaredField("data");
                dataField.setAccessible(true);
                String[] data = (String[]) dataField.get(atts);
                Field lengthField = atts.getClass().getDeclaredField("length");
                lengthField.setAccessible(true);
                int len = (Integer) lengthField.get(atts);

                for (int i = 0; i < len; i++){
                    if (data[i * 5 + 1].equals("style")){
                        //由于p标签中的style会在span标签结束时被覆盖掉，故style名称另外区分
                        attributes.put(key, data[i * 5 + 4]);
                    }else {
                        attributes.put(data[i * 5 + 1], data[i * 5 + 4]);
                    }
                }
            } catch (Exception e) {

            }
        }

        /**
          * 解析style属性
          * @param startIndex
          * @param stopIndex
          * @param editable
          * @param style
         */
        private void analysisStyle(int startIndex,int stopIndex,Editable editable,String style){
//             LogUtils.i("style："+style);
             String[] attrArray = style.split(";");
             Map<String,String> attrMap = new HashMap<>();
             if (null != attrArray){
                 for (String attr:attrArray){
                     String[] keyValueArray = attr.split(":");
                     if (null != keyValueArray && keyValueArray.length == 2){
                         // 记住要去除前后空格
                         attrMap.put(keyValueArray[0].trim(),keyValueArray[1].trim());
                     }
                 }
             }
//             LogUtils.i("attrMap："+attrMap.toString());
             String color = attrMap.get("color");
             String fontSize = attrMap.get("font-size");
             String fontWeight = attrMap.get("font-weight");
             String textDecoration = attrMap.get("text-decoration");
             String backgroundColor = attrMap.get("background-color");
             String textIndent = attrMap.get("text-indent");

             if(!TextUtils.isEmpty(color)){
                 if (color.startsWith("@")) {
                     Resources res = Resources.getSystem();
                     String name = color.substring(1);
                     int colorRes = res.getIdentifier(name, "color", "android");
                     if (colorRes != 0) {
                         editable.setSpan(new ForegroundColorSpan(colorRes), startIndex, stopIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                     }
                 } else {
                     try {
                         editable.setSpan(new ForegroundColorSpan(Color.parseColor(color)), startIndex, stopIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                     } catch (Exception e) {
                         e.printStackTrace();
                         reductionFontColor(startIndex,stopIndex,editable);
                     }
                 }
             }
             //字体大小
            if (!TextUtils.isEmpty(fontSize)) {
                fontSize = fontSize.split("px")[0];
            }
             if (!TextUtils.isEmpty(fontSize)) {
                 int fontSizePx = 16;
                 try {
                     int i = Integer.parseInt(fontSize);
                     fontSizePx = (int) (getResources().getDisplayMetrics().scaledDensity * i);
                 }catch (Exception e){

                 }
                 editable.setSpan(new AbsoluteSizeSpan(fontSizePx), startIndex, stopIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
             }
             //加粗
             if (!TextUtils.isEmpty(fontWeight)){
                 if (fontWeight.equals("bold")){
                     editable.setSpan(new StyleSpan(Typeface.BOLD), startIndex, stopIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                 }else if (fontWeight.equals("normal")){
                     editable.setSpan(new StyleSpan(Typeface.NORMAL), startIndex, stopIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                 }else if (fontWeight.equals("bold_italic")){
                     editable.setSpan(new StyleSpan(Typeface.BOLD_ITALIC), startIndex, stopIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                 }else if (fontWeight.equals("italic")){
                     editable.setSpan(new StyleSpan(Typeface.ITALIC), startIndex, stopIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                 }
             }

             //下划线
             if (!TextUtils.isEmpty(textDecoration)){
                 if (textDecoration.equals("underline")){
                     editable.setSpan(new UnderlineSpan(), startIndex, stopIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                 }else if (textDecoration.equals("line-through")){
                     editable.setSpan(new StrikethroughSpan(), startIndex, stopIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                 }
             }

             //背景色渲染
            if (!TextUtils.isEmpty(backgroundColor)){
                 editable.setSpan(new BackgroundColorSpan(Color.parseColor(backgroundColor)), startIndex, stopIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }

            //首行缩进
            if (!TextUtils.isEmpty(textIndent)){
                textIndent = textIndent.split("em")[0];
            }
            if (!TextUtils.isEmpty(textIndent)){
                int indent = (int) (14 * density) * 2;
                try {
                    indent = (int) (Integer.valueOf(textIndent) * (14 * density));
                }catch (Exception e){
                    LogUtils.e(e.getMessage(), e.getCause());
                }

                LeadingMarginSpan.Standard marginSpan = new LeadingMarginSpan.Standard(indent, 0);
//                editable.setSpan(marginSpan, startIndex, stopIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                //索引问题引发bug,用两个空格暂时
                editable.append("  ");
            }
        }

        /**
         * 还原为原来的颜色
         * @param startIndex
         * @param stopIndex
         * @param editable
         */
        private void reductionFontColor(
                int startIndex,int stopIndex,Editable editable){
            editable.setSpan(new ForegroundColorSpan(0xff2b2b2b), startIndex, stopIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

    }

    /**
     * 注入glide
     * @param requestManager
     */
    public void setGlide(RequestManager requestManager){
        glideRm = requestManager;
    }

    /**
     * 设置html文件
     * @param html
     */
    public void setHtml(String html){
        if (html != null)
            setText(getClickableHtml(html));
    }

    /**
     * 增加点击事件
     * @param onRichClickListener
     */
    public void setOnRichClickListener(OnRichClickListener onRichClickListener) {
        this.onRichClickListener = onRichClickListener;
    }

    public interface OnRichClickListener{
        void onAtUser(String at);
        void onTopicTag(String topic);
        void onNormalUrl(String url);
        void onCustomTag(String tag);
        void OnImg(String imgUrl);
    }

    public static class CustomRichClickListener implements OnRichClickListener{

        @Override
        public void onAtUser(String at) {

        }

        @Override
        public void onTopicTag(String topic) {

        }

        @Override
        public void onNormalUrl(String url) {

        }

        @Override
        public void onCustomTag(String tag) {

        }

        @Override
        public void OnImg(String imgUrl) {

        }
    }

    /*重写P标签*/
    private int getMarginParagraph() {
        return 2;
    }

    private static void appendNewlines(Editable text, int minNewline) {
        final int len = text.length();

        if (len == 0) {
            return;
        }

        int existingNewlines = 0;
        for (int i = len - 1; i >= 0 && text.charAt(i) == '\n'; i--) {
            existingNewlines++;
        }

        for (int j = existingNewlines; j < minNewline; j++) {
            text.append("\n");
        }
    }

    private static void startBlockElement(Editable text, String style, int margin) {
        final int len = text.length();
        if (margin > 0) {
            appendNewlines(text, margin);
            start(text, new Newline(margin));
        }

        if (style != null) {
            Matcher m = getTextAlignPattern().matcher(style);
            if (m.find()) {
                String alignment = m.group(1);
                if (alignment.equalsIgnoreCase("start")) {
                    start(text, new Alignment(Layout.Alignment.ALIGN_NORMAL));
                } else if (alignment.equalsIgnoreCase("center")) {
                    start(text, new Alignment(Layout.Alignment.ALIGN_CENTER));
                } else if (alignment.equalsIgnoreCase("end")) {
                    start(text, new Alignment(Layout.Alignment.ALIGN_OPPOSITE));
                }
            }
        }
    }

    private void startCssStyle(Editable text, String style) {
        if (style != null) {
            Matcher m = getForegroundColorPattern().matcher(style);
            if (m.find()) {
                int c = getHtmlColor(m.group(1));
                if (c != -1) {
                    start(text, new Foreground(c | 0xFF000000));
                }
            }

            m = getBackgroundColorPattern().matcher(style);
            if (m.find()) {
                int c = getHtmlColor(m.group(1));
                if (c != -1) {
                    start(text, new Background(c | 0xFF000000));
                }
            }

            m = getTextDecorationPattern().matcher(style);
            if (m.find()) {
                String textDecoration = m.group(1);
                if (textDecoration.equalsIgnoreCase("line-through")) {
                    start(text, new Strikethrough());
                }
            }
        }
    }

    private int getHtmlColor(String color) {
        return Color.parseColor(color);
    }

    private static Pattern sTextAlignPattern;
    private static Pattern sForegroundColorPattern;
    private static Pattern sBackgroundColorPattern;
    private static Pattern sTextDecorationPattern;
    private static Pattern getTextAlignPattern() {
        if (sTextAlignPattern == null) {
            sTextAlignPattern = Pattern.compile("(?:\\s+|\\A)text-align\\s*:\\s*(\\S*)\\b");
        }
        return sTextAlignPattern;
    }

    private static Pattern getForegroundColorPattern() {
        if (sForegroundColorPattern == null) {
            sForegroundColorPattern = Pattern.compile(
                    "(?:\\s+|\\A)color\\s*:\\s*(\\S*)\\b");
        }
        return sForegroundColorPattern;
    }

    private static Pattern getBackgroundColorPattern() {
        if (sBackgroundColorPattern == null) {
            sBackgroundColorPattern = Pattern.compile(
                    "(?:\\s+|\\A)background(?:-color)?\\s*:\\s*(\\S*)\\b");
        }
        return sBackgroundColorPattern;
    }

    private static Pattern getTextDecorationPattern() {
        if (sTextDecorationPattern == null) {
            sTextDecorationPattern = Pattern.compile(
                    "(?:\\s+|\\A)text-decoration\\s*:\\s*(\\S*)\\b");
        }
        return sTextDecorationPattern;
    }

    private static void start(Editable text, Object mark) {
        int len = text.length();
        text.setSpan(mark, len, len, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
    }

    private static class Foreground {
        private int mForegroundColor;

        public Foreground(int foregroundColor) {
            mForegroundColor = foregroundColor;
        }
    }

    private static class Background {
        private int mBackgroundColor;

        public Background(int backgroundColor) {
            mBackgroundColor = backgroundColor;
        }
    }

    private static class Strikethrough { }

    private static class Newline {
        private int mNumNewlines;

        public Newline(int numNewlines) {
            mNumNewlines = numNewlines;
        }
    }

    private static class Alignment {
        private Layout.Alignment mAlignment;

        public Alignment(Layout.Alignment alignment) {
            mAlignment = alignment;
        }
    }

}
