package com.shuangduan.zcy.dialog;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.ConvertUtils;
import com.shuangduan.zcy.R;

import butterknife.OnClick;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.dialog
 * @class describe  分享的弹窗
 * @time 2019/8/15 16:50
 * @change
 * @chang time
 * @class describe
 */
public class ShareDialog extends BaseDialog {
    public ShareDialog(@NonNull Activity activity) {
        super(activity);
    }

    @Override
    int initLayout() {
        return R.layout.dialog_share;
    }

    @Override
    void initData() {
        setWidth(ConvertUtils.dp2px(260));
        setHeight(ConvertUtils.dp2px(223));
        DialogUtils.enterCustomAnim(this);
    }

    @Override
    void initEvent() {

    }

    @OnClick({R.id.iv_wechat_friend, R.id.tv_wechat_friend, R.id.tv_wechat_circle, R.id.iv_wechat_circle, R.id.iv_qq_friend, R.id.tv_qq_friend, R.id.iv_qq_stone, R.id.tv_qq_stone, })
    void onClick(View view){
        switch (view.getId()){
            case R.id.iv_wechat_friend:
            case R.id.tv_wechat_friend:
                if (onShareListener != null) onShareListener.weChat();
                break;
            case R.id.tv_wechat_circle:
            case R.id.iv_wechat_circle:
                if (onShareListener != null) onShareListener.friendCircle();
                break;
            case R.id.iv_qq_friend:
            case R.id.tv_qq_friend:
                if (onShareListener != null) onShareListener.qq();
                break;
            case R.id.iv_qq_stone:
            case R.id.tv_qq_stone:
                if (onShareListener != null) onShareListener.qqStone();
                break;
        }
    }

    private OnShareListener onShareListener;

    public ShareDialog setOnShareListener(OnShareListener onShareListener) {
        this.onShareListener = onShareListener;
        return this;
    }

    public interface OnShareListener{
        void qq();
        void qqStone();
        void weChat();
        void friendCircle();
    }
}
