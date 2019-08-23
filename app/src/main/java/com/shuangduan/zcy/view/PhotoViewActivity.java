package com.shuangduan.zcy.view;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.ViewPagerAdapter;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.utils.BarUtils;

import java.util.ArrayList;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view
 * @class describe  图片预览
 * @time 2019/7/17 16:56
 * @change
 * @chang time
 * @class describe
 */
public class PhotoViewActivity extends AppCompatActivity {
    private ArrayList<String> paths;
    private Fragment[] fragments;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_view);
        View fakeStatusBar = findViewById(R.id.fake_status_bar);
        BarUtils.setStatusBarColorRes(fakeStatusBar, getResources().getColor(android.R.color.transparent));
        ViewPager vp = findViewById(R.id.vp);
        TextView tvPositionShow = findViewById(R.id.tv_bar_right);

        paths = getIntent().getStringArrayListExtra(CustomConfig.PHOTO_VIEW_URL_LIST);
        //最先显示的图片position
        int position = getIntent().getIntExtra("position", 0);
        fragments = new Fragment[paths.size()];
        for (int i = 0; i < paths.size(); i++) {
            fragments[i] = PhotoViewFragment.newInstance(paths.get(i));
        }

        tvPositionShow.setText((position + 1) + "/" + fragments.length);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), fragments);
        vp.setAdapter(viewPagerAdapter);
        vp.setCurrentItem(position);
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tvPositionShow.setText((position + 1) + "/" + fragments.length);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        findViewById(R.id.iv_bar_back).setOnClickListener(l -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                finishAfterTransition();
            }else {
                finish();
            }
        });
    }
}
