package com.shuangduan.zcy.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.text.InputFilter;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.shuangduan.zcy.base.BaseActivity;

import java.util.List;
import java.util.Objects;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.utils
 * @ClassName: KeyboardUtil
 * @Description: 键盘工具类
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/9/19 15:57
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/9/19 15:57
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class KeyboardUtil {

    /**
     * 隐藏键盘的方法
     *
     * @param context
     */
    public static void hideKeyboard(Activity context) {
//        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
//        // 隐藏软键盘
//        imm.hideSoftInputFromWindow(context.getWindow().getDecorView().getWindowToken(), 0);
        InputMethodManager imm = (InputMethodManager) context.getSystemService(INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(context.getWindow().getDecorView().getWindowToken(), 0);
        }
    }

    /**
     * 关闭activity中打开的键盘
     *
     * @param activity
     */
    public static void closeKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive() && activity.getCurrentFocus() != null) {
            if (activity.getCurrentFocus().getWindowToken() != null) {
                imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }


    /**
     * 判断软键盘是否显示方法
     * @param activity
     * @return
     */
    public static boolean isSoftShowing(Activity activity) {
        //获取当屏幕内容的高度
        int screenHeight = activity.getWindow().getDecorView().getHeight();
        //获取View可见区域的bottom
        Rect rect = new Rect();
        //DecorView即为activity的顶级view
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        //考虑到虚拟导航栏的情况（虚拟导航栏情况下：screenHeight = rect.bottom + 虚拟导航栏高度）
        //选取screenHeight*2/3进行判断
        return screenHeight*2/3 > rect.bottom+getSoftButtonsBarHeight(activity);
    }

    /**
     * 底部虚拟按键栏的高度
     * @return
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private static int getSoftButtonsBarHeight(Activity activity) {
        DisplayMetrics metrics = new DisplayMetrics();
        //这个方法获取可能不是真实屏幕的高度
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int usableHeight = metrics.heightPixels;
        //获取当前屏幕的真实高度
        activity.getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
        int realHeight = metrics.heightPixels;
        if (realHeight > usableHeight) {
            return realHeight - usableHeight;
        } else {
            return 0;
        }
    }

    /**
     * 隐藏或显示软键盘
     * 如果现在是显示调用后则隐藏 反之则显示
     * @param activity
     */
    public static void showORhideSoftKeyboard(Activity activity){
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        Objects.requireNonNull(imm).toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    //打开键盘
    public static void showSoftInputFromWindow(BaseActivity activity, EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        Objects.requireNonNull(inputManager).toggleSoftInput(0, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    /**
     * 设置输入框 只能输入数字和小数点
     *
     * @param editText
     */
    public static void RemoveDecimalPoints(EditText editText) {
        //设置输入框 只能输入数字和小数点
        editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        //设置字符过滤（设置小数点只能输入到后两位）
        editText.setFilters(new InputFilter[]{(source, start, end, dest, dstart, dend) -> {
            if (source.equals(".") && dest.toString().length() == 0) {
                return "0.";
            }
            if (dest.toString().contains(".")) {
                int index = dest.toString().indexOf(".");
                int length = dest.toString().substring(index).length();
                if (length == 3) {
                    return "";
                }
            }
            return null;
        }});
    }

    /**
     * list转字符串
     *
     * @param list
     */
    public static String getListForString(List list) {
        StringBuilder stringBuffer = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            if (i == list.size() - 1) {
                stringBuffer.append(list.get(i));
            } else {
                stringBuffer.append(list.get(i)).append(",");
            }
        }
        return stringBuffer.toString();
    }

    //复制
    public static void copyString(Context context, String str) {
        //获取剪贴板管理器：
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        //创建普通字符型ClipData
        ClipData mClipData = ClipData.newPlainText("Label", str);
        //将ClipData内容放到系统剪贴板里。
        Objects.requireNonNull(cm).setPrimaryClip(mClipData);
    }
}
