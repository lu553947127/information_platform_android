package com.shuangduan.zcy.view.activity;

import android.os.Handler;
import android.view.KeyEvent;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.view.fragment.HomeFragment;
import com.shuangduan.zcy.view.fragment.MineFragment;
import com.shuangduan.zcy.view.fragment.PeopleFragment;
import com.shuangduan.zcy.view.fragment.ReleaseFragment;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.rg_main)
    RadioGroup rgMain;

    private Fragment[] fragments = new Fragment[4];

    private String[] tags = {
            "home",
            "release",
            "people",
            "mine"
    };

    /**
     * 默认选中
     */
    private int currentChecked = 0;
    /**
     * 上一次选中
     */
    private int oldChecked = -1;

    @Override
    public void onAttachFragment(@NonNull Fragment fragment) {
        if (fragments[0] == null && fragment instanceof HomeFragment)
            fragments[0] = fragment;
        if (fragments[1] == null && fragment instanceof ReleaseFragment)
            fragments[1] = fragment;
        if (fragments[2] == null && fragment instanceof PeopleFragment)
            fragments[2] = fragment;
        if (fragments[3] == null && fragment instanceof MineFragment)
            fragments[3] = fragment;
    }

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void initDataAndEvent() {
        if (fragments[0] == null) {
            fragments[0] = HomeFragment.newInstance();
        }
        if (fragments[1] == null) {
            fragments[1] = ReleaseFragment.newInstance();
        }
        if (fragments[2] == null) {
            fragments[2] = PeopleFragment.newInstance();
        }
        if (fragments[3] == null) {
            fragments[3] = MineFragment.newInstance();
        }

        //初始化选中首页
        rgMain.check(R.id.rb_home);
        switchFragment(currentChecked);

        rgMain.setOnCheckedChangeListener((group, checkedId) -> {

            oldChecked = currentChecked;

            switch (checkedId) {
                case R.id.rb_home:
                    currentChecked = 0;
                    break;
                case R.id.rb_release:
                    currentChecked = 1;
                    break;
                case R.id.rb_people:
                    currentChecked = 2;
                    break;
                case R.id.rb_mine:
                    currentChecked = 3;
                    break;
            }
            //选中项不变不做处理
            if (oldChecked != currentChecked) {
                switchFragment(currentChecked);
            }

        });
    }

    /**
     * 切换fragment
     * @param position
     */
    private void switchFragment(int position) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        if (!fragments[position].isAdded()) {
            //存在上一个界面，先隐藏
            if (oldChecked == -1) {
                transaction.add(R.id.frame_main, fragments[position], tags[position])
                        .commit();
            } else {
                transaction.add(R.id.frame_main, fragments[position], tags[position])
                        .hide(fragments[oldChecked])
                        .commit();
            }
        } else {
            FragmentTransaction show = transaction.show(fragments[position]);
            if (oldChecked != -1) {
                show.hide(fragments[oldChecked]);
            }
            show.commit();
        }

    }

    private boolean mIsExit = false;

    /**
     * 双击返回键退出
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mIsExit) {
//                AppConfig.mLocationClient.onDestroy();//销毁定位客户端，同时销毁本地定位服务。
                ActivityUtils.finishAllActivities();
            } else {
                ToastUtils.showShort("再按一次退出");
                mIsExit = true;
                new Handler().postDelayed(() -> mIsExit = false, 2000);
            }
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

}
