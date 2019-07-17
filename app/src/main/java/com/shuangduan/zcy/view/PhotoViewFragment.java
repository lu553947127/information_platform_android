package com.shuangduan.zcy.view;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.transition.ChangeBounds;

import com.github.chrisbanes.photoview.PhotoView;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.utils.image.ImageConfig;
import com.shuangduan.zcy.utils.image.ImageLoader;

/**
 * <pre>
 *     author : 宁文强
 *     e-mail : ningwenqiang@lanhuiplay.com
 *     time   : 2018/06/08
 *     desc   : 图片预览
 *     version: 1.0
 * </pre>
 */

public class PhotoViewFragment extends Fragment {

    private String path;
    PhotoView photoView;

    public static PhotoViewFragment newInstance(String path) {

        Bundle args = new Bundle();
        args.putString("path", path);
        PhotoViewFragment fragment = new PhotoViewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        path = getArguments().getString("path");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setupWindowAnimations();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_photo_view, container, false);
        photoView = inflate.findViewById(R.id.photo_view);
        return inflate;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ImageLoader.load(getContext(), new ImageConfig.Builder().url(path).imageView(photoView).build());

        photoView.setOnClickListener(v -> {
            if (getActivity() != null){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getActivity().finishAfterTransition();
                }else {
                    getActivity().finish();
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setupWindowAnimations() {
        ChangeBounds changeBounds = new ChangeBounds();
        changeBounds.setDuration(300);
        //排除状态栏
        changeBounds.excludeTarget(android.R.id.statusBarBackground, true);
        //是否同时执行
        setAllowEnterTransitionOverlap(false);
        setAllowReturnTransitionOverlap(false);
        //进入
        setEnterTransition(changeBounds);
    }
}
