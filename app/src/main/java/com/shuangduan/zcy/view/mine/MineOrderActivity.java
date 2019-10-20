package com.shuangduan.zcy.view.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.ViewPagerAdapter;
import com.shuangduan.zcy.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view.mine
 * @class describe  我的订单
 * @time 2019/8/12 16:28
 * @change
 * @chang time
 * @class describe
 */
public class MineOrderActivity extends BaseActivity {

    @BindView(R.id.vp)
    ViewPager vp;
    @BindView(R.id.tv_project)
    TextView tvProject;
    @BindView(R.id.tv_recruit)
    TextView tvRecruit;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_mine_order;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        Fragment[] fragments = new Fragment[]{
                ProjectOrderFragment.newInstance(),
                RecruitHistoryFragment.newInstance(),
                SupplierOrderFragment.newInstance()
        };
        vp.setOffscreenPageLimit(2);
        vp.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), fragments, null));
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position==0){
                    tvProject.setBackgroundResource(R.drawable.circular_white_half_left);
                    tvProject.setTextColor(getResources().getColor(R.color.colorPrimary));
                    tvRecruit.setBackgroundResource(R.drawable.circular_violet_half_right);
                    tvRecruit.setTextColor(getResources().getColor(R.color.colorFFF));
                }else if (position==1){
                    tvProject.setBackgroundResource(R.drawable.circular_violet_half_left);
                    tvProject.setTextColor(getResources().getColor(R.color.colorFFF));
                    tvRecruit.setBackgroundResource(R.drawable.circular_white_half_right);
                    tvRecruit.setTextColor(getResources().getColor(R.color.colorPrimary));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @OnClick({R.id.iv_bar_back,R.id.tv_project,R.id.tv_recruit})
    void onClick(View v){
        switch (v.getId()){
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.tv_project://工程信息
                tvProject.setBackgroundResource(R.drawable.circular_white_half_left);
                tvProject.setTextColor(getResources().getColor(R.color.colorPrimary));
                tvRecruit.setBackgroundResource(R.drawable.circular_violet_half_right);
                tvRecruit.setTextColor(getResources().getColor(R.color.colorFFF));
                vp.setCurrentItem(0);
                break;
            case R.id.tv_recruit://招采信息
                tvProject.setBackgroundResource(R.drawable.circular_violet_half_left);
                tvProject.setTextColor(getResources().getColor(R.color.colorFFF));
                tvRecruit.setBackgroundResource(R.drawable.circular_white_half_right);
                tvRecruit.setTextColor(getResources().getColor(R.color.colorPrimary));
                vp.setCurrentItem(1);
                break;
        }
    }
}
