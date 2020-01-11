package com.shuangduan.zcy.view.photo;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.cjt2325.cameralibrary.JCameraView;
import com.cjt2325.cameralibrary.listener.ErrorListener;
import com.cjt2325.cameralibrary.listener.JCameraListener;
import com.cjt2325.cameralibrary.util.FileUtil;
import com.shuangduan.zcy.R;

import java.io.File;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.view.photo
 * @ClassName: CameraActivity
 * @Description: 相机拍照页面
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/9/21 13:44
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/9/21 13:44
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class CameraActivity extends AppCompatActivity {

    JCameraView jCameraView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT != Build.VERSION_CODES.O) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        setContentView(R.layout.activity_camera);
        jCameraView =  findViewById(R.id.jc_camera_view);
        //设置视频保存路径
        jCameraView.setSaveVideoPath(Environment.getExternalStorageDirectory().getPath() + File.separator + "zcy/camera");
        jCameraView.setFeatures(JCameraView.BUTTON_STATE_BOTH);
//        jCameraView.setTip("轻触拍照，按住摄像");
        //设置录制视频的比特率
        jCameraView.setMediaQuality(JCameraView.MEDIA_QUALITY_MIDDLE);
        jCameraView.setErrorLisenter(new ErrorListener() {
            @Override
            public void onError() {
                //错误监听
                Intent intent = new Intent();
                setResult(103, intent);
                finish();
            }

            @Override
            public void AudioPermissionError() {
                ToastUtils.showShort("您没有打开录音权限");
            }
        });

        //点击上传拍照/录制小视频
        jCameraView.setJCameraLisenter(new JCameraListener() {
            @Override
            public void captureSuccess(Bitmap bitmap) {
                //获取图片bitmap
                LogUtils.e(bitmap);
                String path = FileUtil.saveBitmap("worker", bitmap);
                Intent intent = new Intent();
                intent.putExtra("path", path);
                setResult(101, intent);
                finish();
            }

            @Override
            public void recordSuccess(String url, Bitmap firstFrame) {
//                //获取视频路径
//                String path = FileUtil.saveBitmap("worker", firstFrame);
//                Log.e(TAG, "url = " + url + ", Bitmap = " + path);
//                Intent intent = new Intent();
//                intent.putExtra("path", url);
//                setResult(102, intent);
//                finish();
                ToastUtils.showShort("抱歉，拍摄小视频功能,暂未开放");
            }
        });

        //退出按钮
        jCameraView.setLeftClickListener(CameraActivity.this::finish);

//        //相册按钮
//        jCameraView.setRightClickListener(new ClickListener() {
//            @Override
//            public void onClick() {
//                toast.show("抱歉，目前暂未开通相册权限",R.layout.toast_custom_layout,R.id.toast_custom_layout_ll,CameraActivity.this);
//            }
//        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        //全屏显示
        if (Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        } else {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(option);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        jCameraView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        jCameraView.onPause();
    }
}
