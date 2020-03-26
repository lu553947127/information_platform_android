package com.shuangduan.zcy.view.mine;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.dialog.BaseDialog;
import com.shuangduan.zcy.dialog.CustomDialog;
import com.shuangduan.zcy.utils.PermissionUtils;
import com.shuangduan.zcy.utils.ShareUtils;
import com.shuangduan.zcy.utils.image.PicturesUtils;
import com.shuangduan.zcy.view.WebViewActivity;
import com.shuangduan.zcy.vm.PermissionVm;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnLongClick;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.view.mine
 * @ClassName: SharePictorialActivity
 * @Description: 分享画报页面
 * @Author: 鹿鸿祥
 * @CreateDate: 2020/1/10 10:58
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2020/1/10 10:58
 * @UpdateRemark: 更新说明
 * @Version: 1.1.0
 */
public class SharePictorialActivity extends BaseActivity {
    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ll_share_pictures)
    RelativeLayout llSharePictures;
    private PermissionVm permissionVm;
    private Bitmap bitmap;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_share_pictorial;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(getString(R.string.share_pictorial));

        permissionVm = getViewModel(PermissionVm.class);
        permissionVm.getLiveData().observe(this, integer -> {
            if (integer == PermissionVm.PERMISSION_STORAGE){
//                // 获取图片某布局
//                llSharePictures.setDrawingCacheEnabled(true);
//                llSharePictures.buildDrawingCache();
//                //获取图片
//                bitmap = llSharePictures.getDrawingCache();

                bitmap = PicturesUtils.createViewBitmap(llSharePictures);
                LogUtils.e(bitmap);
            } else if (integer == PermissionVm.PERMISSION_STORAGE_NO) {
                showStorageDialog();
            }
        });
        permissionVm.getPermissionAlbum(new RxPermissions(this));
    }

    private void showStorageDialog() {
        new CustomDialog(this)
                .setTip("为了更好的为您服务，请您打开您的存储权限!")
                .setCallBack(new BaseDialog.CallBack() {
                    @Override
                    public void cancel() {
                        finish();
                    }

                    @Override
                    public void ok(String s) {
                        PermissionUtils.toSelfSetting(getApplicationContext());
                    }
                }).showDialog();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            permissionVm.getPermissionAlbum(new RxPermissions(this));
        }
    }

    @OnClick({R.id.iv_bar_back,R.id.ll_wechat_friend,R.id.ll_wechat_circle,R.id.ll_qq_friend,R.id.ll_qq_stone,R.id.ll_download_pictures})
    void onClick(View view){
        switch (view.getId()){
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.ll_wechat_friend://分享到微信朋友
                ShareUtils.shareWechatFriend(this,new File(PicturesUtils.saveImageToLocal(this,bitmap,0)));
                break;
            case R.id.ll_wechat_circle://分享到微信朋友圈
                ShareUtils.shareWechatMoment(this,new File(PicturesUtils.saveImageToLocal(this,bitmap,0)));
                break;
            case R.id.ll_qq_friend://分享到QQ好友
                ShareUtils.shareImageToQQ(this,bitmap);
                break;
            case R.id.ll_qq_stone://分享到QQ空间
                break;
            case R.id.ll_download_pictures://保存图片到手机相册
                if (bitmap == null){
                    ToastUtils.showShort("保存图片失败");
                    return;
                }
                PicturesUtils.saveImageToLocal(this,bitmap,1);
//                //释放资源
//                llSharePictures.destroyDrawingCache();
                break;
        }
    }

    @OnLongClick(R.id.iv_qr_code)
    void onLongClick(){
        Bundle bundle = new Bundle();
        bundle.putString("register", "friend");
        bundle.putString("url", getIntent().getStringExtra("url"));
        ActivityUtils.startActivity(bundle, WebViewActivity.class);
    }
}
