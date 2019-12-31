package com.shuangduan.zcy.manage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.lifecycle.ViewModelProviders;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.dialog.ShareDialog;
import com.shuangduan.zcy.listener.BaseUiListener;
import com.shuangduan.zcy.model.bean.ShareBean;
import com.shuangduan.zcy.model.event.ShareEvent;
import com.shuangduan.zcy.utils.LoginUtils;
import com.shuangduan.zcy.utils.ShareUtils;
import com.shuangduan.zcy.vm.ShareVm;
import com.tencent.tauth.Tencent;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author xuyu
 * @Package com.shuangduan.zcy.manage$
 * @class ShareManage$
 * @class 分享工具类
 * @time 2019/9/28 9:00
 * @change
 * @class describe
 */
public class ShareManage {

    //------------------------------------分享类型----------------------------------------------------
    public static final int SHARE_PROJECT_TYPE = 1;                                         //      工程信息
    public static final int SHARE_RECOMMEND_FRIENDS_TYPE = 3;                               //      推荐好友
    public static final int SHARE_TENDERER_TYPE = 2;                                        //      招采信息
    public static final int SHARE_HEADLINES_TYPE = 4;                                        //     基建头条
    //-------------------------------------------------------------------------------------------------------


    private static ShareManage manage;

    private Tencent mTencent;
    private BaseUiListener qqListener;
    private ShareDialog dialog;
    private Disposable disposable;
    private ShareVm shareVm;

    private ShareBean.DataBean item;

    private Bitmap bitmap;


    public static ShareManage newInstance(Context context) {
        if (manage == null) {
            synchronized (ShareManage.class) {
                if (manage == null) {
                    manage = new ShareManage(context);
                }
            }
        }
        return manage;
    }


    public ShareManage(Context context) {
        // 其中APP_ID是分配给第三方应用的appid，类型为String。
        mTencent = Tencent.createInstance(ShareUtils.app_id_qq, context);
        qqListener = new BaseUiListener();
    }


    /**
     * @param activity
     * @param type     分享类型 1：基建详情
     */
    public void init(BaseActivity activity, int type, int id) {
        shareVm = ViewModelProviders.of(activity).get(ShareVm.class);

        switch (type) {
            case SHARE_PROJECT_TYPE:
                shareVm.projectShare(id);
                break;
            case SHARE_RECOMMEND_FRIENDS_TYPE:
                shareVm.userInfoShare();
                break;
            case SHARE_TENDERER_TYPE:
                shareVm.tendererShare(id);
                break;
            case SHARE_HEADLINES_TYPE:
                shareVm.headlinesShare(id);
                break;
        }

        shareVm.shareLiveData.observe(activity, item -> {
            this.item = item;
            EventBus.getDefault().post(new ShareEvent(item.getUrl()));
            getNetworkBitmap(item.getImage());
        });
    }

    private void getNetworkBitmap(String image) {
        Observable.create((ObservableOnSubscribe<Bitmap>) e -> {
            e.onNext(returnBitmap(image));
            e.onComplete();
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Bitmap>() {

                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(Bitmap bitmap) {
                        ShareManage.this.bitmap = bitmap;
                    }

                    @Override
                    public void onError(Throwable e) {
                        //释放订阅避免内存泄漏
                        if (disposable != null && !disposable.isDisposed()) {
                            disposable.dispose();
                        }
                    }

                    @Override
                    public void onComplete() {
                        //释放订阅避免内存泄漏
                        if (disposable != null && !disposable.isDisposed()) {
                            disposable.dispose();
                        }
                    }
                });
    }

    /**
     * 初始化 分享Dialog
     *
     * @param activity
     * @param url
     * @param title
     * @param des
     * @param image
     */
    public void initDialog(BaseActivity activity, String url, String title, String des, String image, Bitmap bitmap) {
        dialog = new ShareDialog(activity)
                .setOnShareListener(new ShareDialog.OnShareListener() {
                    @Override
                    public void qq() {
                        if (!LoginUtils.isQQClientAvailable(activity)) {
                            ToastUtils.showShort("您还没有安装QQ，请先安装QQ客户端");
                            return;
                        }
                        ShareUtils.shareQQ(activity, mTencent, qqListener, url, title, des, image);
                    }

                    @Override
                    public void qqStone() {
                        if (!LoginUtils.isQQClientAvailable(activity)) {
                            ToastUtils.showShort("您还没有安装QQ，请先安装QQ客户端");
                            return;
                        }
                        ShareUtils.shareQQStone(activity, mTencent, qqListener, url, title, des, image);
                    }

                    @Override
                    public void weChat() {
                        if (!LoginUtils.isWeiXinInstall(activity)) {
                            ToastUtils.showShort("您还没有安装微信，请先安装微信客户端");
                            return;
                        }
                        ShareUtils.shareWeChat(ShareUtils.FRIEND, url, title, des, bitmap);
                    }

                    @Override
                    public void friendCircle() {
                        if (!LoginUtils.isWeiXinInstall(activity)) {
                            ToastUtils.showShort("您还没有安装微信，请先安装微信客户端");
                            return;
                        }
                        ShareUtils.shareWeChat(ShareUtils.FRIEND_CIRCLE, url, title, des, bitmap);
                    }
                });


        showDialog();

        activity.addDialog(dialog);
    }

    //显示分享弹窗
    private void showDialog() {
        if (dialog != null) {
            dialog.showDialog();
        }
    }

    /**
     * 根据图片的url路径获得Bitmap对象
     *
     * @param url
     * @return
     */
    private Bitmap returnBitmap(String url) {
        if (!StringUtils.isEmpty(url)) {
            URL fileUrl = null;
            Bitmap bitmap = null;
            try {
                fileUrl = new URL(url);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            try {
                HttpURLConnection conn = (HttpURLConnection) fileUrl.openConnection();
                conn.setDoInput(true);
                conn.connect();
                InputStream is = conn.getInputStream();
                bitmap = BitmapFactory.decodeStream(is);
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }
        return null;
    }

    public BaseUiListener getQQListener() {
        return qqListener;
    }

    public ShareBean.DataBean getItem() {
        return item;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }
}
