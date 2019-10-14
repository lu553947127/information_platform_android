package com.shuangduan.zcy.factory;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.shuangduan.zcy.R;
import com.shuangduan.zcy.manage.ShareManage;

/**
 * @author xuyu
 * @Package com.shuangduan.zcy.view$
 * @class EmptyViewFactory$
 * @class describe
 * @time 2019/10/12 10:07
 * @change
 * @class describe
 */
public class EmptyViewFactory {

    private static EmptyViewFactory instance;

    private final Context context;


    public static EmptyViewFactory newInstance(Context context) {
        if (instance == null) {
            synchronized (ShareManage.class) {
                if (instance == null) {
                    instance = new EmptyViewFactory(context);
                }
            }
        }
        return instance;
    }

    public EmptyViewFactory(Context context) {
        this.context = context;
    }

    /**
     * 空页面没有跳转按钮  callBack 可以传null
     *
     * @param iconRes
     * @param strRes
     * @param btnStrRes
     * @param callBack
     * @return
     */
    public View createEmptyView(int iconRes, int strRes, int btnStrRes, EmptyViewCallBack callBack) {

        View view = LayoutInflater.from(context).inflate(R.layout.layout_empty, null);

        ImageView ivIcon = view.findViewById(R.id.iv_icon);
        ivIcon.setImageResource(iconRes);

        TextView tvTip = view.findViewById(R.id.tv_tip);
        tvTip.setText(strRes);
        TextView tvGoto = view.findViewById(R.id.tv_goto);


        //跳转的点击回调
        if (callBack != null) {
            tvGoto.setText(btnStrRes);
            tvGoto.setVisibility(View.VISIBLE);
            tvGoto.setOnClickListener(v -> callBack.onEmptyClick());
        }
        return view;
    }


    public interface EmptyViewCallBack {
        void onEmptyClick();
    }
}
