package com.shuangduan.zcy.utils.matisse;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;

import androidx.core.content.FileProvider;
import androidx.core.os.EnvironmentCompat;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

/**
 * <pre>
 *     author : 徐玉
 *     e-mail : ningwenqiang@lanhuiplay.com
 *     time   : 2018/09/04
 *     desc   : 调用系统相机
 *     version: 1.0
 * </pre>
 */

public class MatisseCamera {

    private final WeakReference<Activity> mContext;
    private final WeakReference<Fragment> mFragment;
    // 是否是Android 10以上手机
//    public static boolean isAndroidQ = Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q;

    private MatisseCamera(Activity activity) {
        this(activity, null);
    }

    private MatisseCamera(Fragment fragment) {
        this(fragment.getActivity(), fragment);
    }

    private MatisseCamera(Activity activity, Fragment fragment) {
        mContext = new WeakReference<>(activity);
        mFragment = new WeakReference<>(fragment);
    }

    public static MatisseCamera from(Activity activity) {
        return new MatisseCamera(activity);
    }

    public static MatisseCamera from(Fragment fragment) {
        return new MatisseCamera(fragment);
    }

    public static String obtainPathResult() {
        return mCurrentPhotoPath;
    }

    public static Uri obtainUriResult() {
        return photoURI;
    }

    public void forResult(int requestCode, String authority) {
        Fragment fragment = mFragment.get();
        if (fragment != null) {
            dispatchTakePictureIntent(requestCode, Objects.requireNonNull(fragment.getActivity()), authority);
        } else {
            dispatchTakePictureIntent(requestCode, mContext.get(), authority);
        }
    }

    private static Uri photoURI;
    private void dispatchTakePictureIntent(int requestCode, Activity activity, String authority) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(activity.getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
//            if (isAndroidQ) {
//                // 适配android 10
//                photoURI = createImageUri(activity);
//            } else {
//                photoFile = createImageFile();
//                if (photoFile != null) {
//                    mCurrentPhotoPath = photoFile.getAbsolutePath();
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                        //适配Android 7.0文件权限，通过FileProvider创建一个content类型的Uri
//                        photoURI = FileProvider.getUriForFile(activity, authority, photoFile);
//                    } else {
//                        photoURI = Uri.fromFile(photoFile);
//                    }
//                }
//            }

            photoFile = createImageFile();
            if (photoFile != null) {
                mCurrentPhotoPath = photoFile.getAbsolutePath();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    //适配Android 7.0文件权限，通过FileProvider创建一个content类型的Uri
                    photoURI = FileProvider.getUriForFile(activity, authority, photoFile);
                } else {
                    photoURI = Uri.fromFile(photoFile);
                }
            }
            if (photoURI != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                takePictureIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                activity.startActivityForResult(takePictureIntent, requestCode);
            }
        }
    }

    /**
     * 创建保存图片的文件
     */
    private static String mCurrentPhotoPath;
    private File createImageFile(){
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = String.format("JPEG_%s.jpg", timeStamp);
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        if (!Objects.requireNonNull(storageDir).exists()) storageDir.mkdirs();
        // Avoid joining path components manually
        File tempFile = new File(storageDir, imageFileName);
        // Handle the situation that user's external storage is not ready
        if (!Environment.MEDIA_MOUNTED.equals(EnvironmentCompat.getStorageState(tempFile))) {
            return null;
        }
        return tempFile;
    }

    /**
     * 创建图片地址uri,用于保存拍照后的照片 Android 10以后使用这种方法
     */
    private Uri createImageUri(Activity activity) {
        String status = Environment.getExternalStorageState();
        // 判断是否有SD卡,优先使用SD卡存储,当没有SD卡时使用手机存储
        if (status.equals(Environment.MEDIA_MOUNTED)) {
            return activity.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new ContentValues());
        } else {
            return activity.getContentResolver().insert(MediaStore.Images.Media.INTERNAL_CONTENT_URI, new ContentValues());
        }
    }
}
