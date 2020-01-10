package com.shuangduan.zcy.dialog;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.ConvertUtils;
import com.shuangduan.zcy.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.dialog
 * @class describe  分享的弹窗
 * @time 2019/8/15 16:50
 * @change
 * @chang time
 * @class describe
 */
public class ShareDialog extends BaseDialog {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_share_picture)
    ImageView ivSharePicture;
    @BindView(R.id.tv_share_picture)
    TextView tvSharePicture;
    private String title;

    public ShareDialog(@NonNull Activity activity ,String title) {
        super(activity);
        this.title = title;
    }

    @Override
    int initLayout() {
        return R.layout.dialog_share;
    }

    @Override
    void initData() {
        setWidth(ConvertUtils.dp2px(260));

        DialogUtils.enterCustomAnim(this);
    }

    @Override
    void initEvent() {
        tvTitle.setText(title);
        if (title.equals("邀请好友")) ivSharePicture.setVisibility(View.VISIBLE);tvSharePicture.setVisibility(View.VISIBLE);
    }

    @OnClick({R.id.iv_wechat_friend, R.id.tv_wechat_friend, R.id.tv_wechat_circle, R.id.iv_wechat_circle, R.id.iv_qq_friend, R.id.tv_qq_friend, R.id.iv_qq_stone, R.id.tv_qq_stone,R.id.iv_share_picture,R.id.tv_share_picture})
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
            case R.id.iv_share_picture:
            case R.id.tv_share_picture:
                if (onShareListener != null) onShareListener.sharePicture();
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
        void sharePicture();
    }
}
