package com.zcy.framelibrary.navigationBar;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.zcy.baselibrary.navigationBar.AbsNavigationBar;
import com.zcy.framelibrary.R;
import com.zcy.framelibrary.app.ActivityManager;

/**
 * @ProjectName: EssayJoke
 * @Package: com.coder.framelibrary.navigationBar
 * @ClassName: DefaultNavigationBar
 * @Description: 默认的头部导航栏
 * @Author: XuYu
 * @CreateDate: 2020/2/8$ 16:16$
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/2/8 16:16
 * @UpdateRemark: 更新内容
 * @Version: 1.0
 */
public class DefaultNavigationBar<D extends DefaultNavigationBar.Builder.DefaultNavigationParams>
        extends AbsNavigationBar<DefaultNavigationBar.Builder.DefaultNavigationParams> {


    public DefaultNavigationBar(D params) {
        super(params);
    }


    @Override
    public int bindLayoutId() {
        return R.layout.title_toolbar;
    }

    @Override
    public void applyView() {
        //绑定效果参数
        setText(R.id.tv_title, getParams().mTitle);

        setText(R.id.tv_top_title, getParams().mTopTitle);

        setText(R.id.tv_floor_title, getParams().mFloorTitle);

        setText(R.id.tv_right, getParams().mRightText);

        setIcon(R.id.iv_right, getParams().mRightIcon);

        setOnClickListener(R.id.iv_back, getParams().mLeftOnClickListener);

        setOnClickListener(R.id.tv_right, getParams().mRightTextOnClickListener);

        setOnClickListener(R.id.iv_right, getParams().mRightIconOnClickListener);
    }


    public static class Builder extends AbsNavigationBar.Builder {

        DefaultNavigationParams P;

        public Builder(Context context, ViewGroup parent) {
            super(context, parent);
            P = new DefaultNavigationParams(context, parent);
        }

        public Builder(Context context) {
            super(context, null);
            P = new DefaultNavigationParams(context, null);
        }

        @Override
        public AbsNavigationBar builder() {
            return new DefaultNavigationBar(P);
        }

        public Builder setTitle(String title) {
            P.mTitle = title;
            return this;
        }


        public Builder setTopTitle(String value) {
            P.mTopTitle = value;
            return this;
        }


        public Builder setFloorTitle(String value) {
            P.mFloorTitle = value;
            return this;
        }

        public Builder setRightText(String rightTitle) {
            P.mRightText = rightTitle;
            return this;
        }

        public Builder setRightIcon(int rightIcon) {
            P.mRightIcon = rightIcon;
            return this;
        }


        public Builder setSplitColor(int colorRes) {
            P.mColorRes = colorRes;
            return this;
        }


        public Builder setLeftClickListener(View.OnClickListener listener) {
            P.mLeftOnClickListener = listener;
            return this;
        }

        public Builder setRightTextClickListener(View.OnClickListener listener) {
            P.mRightTextOnClickListener = listener;
            return this;
        }

        public Builder setRightIconClickListener(View.OnClickListener listener) {
            P.mRightIconOnClickListener = listener;
            return this;
        }

        /**
         * 所有参数效果
         */
        public static class DefaultNavigationParams extends AbsNavigationParams {
            //中间标题
            public String mTitle;

            //中间头标题
            public String mTopTitle;

            //中间底部标题
            public String mFloorTitle;

            //右边文本
            public String mRightText;
            //右边图片
            public int mRightIcon;

            //分割线颜色
            public int mColorRes;


            //左边点击事件
            public View.OnClickListener mLeftOnClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ActivityManager.getInstance().getCurrentActivity().finish();
                }
            };

            //右边文本点击事件
            public View.OnClickListener mRightTextOnClickListener;

            //右边图片点击事件
            public View.OnClickListener mRightIconOnClickListener;


            public DefaultNavigationParams(Context context, ViewGroup parent) {
                super(context, parent);
            }

        }
    }
}
