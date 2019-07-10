package com.shuangduan.zcy.utils.matisse;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import androidx.core.content.FileProvider;
import androidx.core.os.EnvironmentCompat;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * <pre>
 *     author : 宁文强
 *     e-mail : ningwenqiang@lanhuiplay.com
 *     time   : 2018/09/04
 *     desc   : 调用系统相机
 *     version: 1.0
 * </pre>
 */

public class MatisseCamera {

    private final WeakReference<Activity> mContext;
    private final WeakReference<Fragment> mFragment;
    public static final int REQUEST_CODE = 606;

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
            dispatchTakePictureIntent(requestCode, fragment.getActivity(), authority);
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
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                photoURI = FileProvider.getUriForFile(activity, authority,
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                activity.startActivityForResult(takePictureIntent, requestCode);
            }
        }
    }

    static String mCurrentPhotoPath;

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp =
                new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = String.format("JPEG_%s.jpg", timeStamp);
        File storageDir;
        storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        if (!storageDir.exists()) storageDir.mkdirs();

        // Avoid joining path components manually
        File tempFile = new File(storageDir, imageFileName);

        mCurrentPhotoPath = tempFile.getAbsolutePath();

        // Handle the situation that user's external storage is not ready
        if (!Environment.MEDIA_MOUNTED.equals(EnvironmentCompat.getStorageState(tempFile))) {
            return null;
        }

        return tempFile;
    }

}
