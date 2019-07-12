package com.shuangduan.zcy.view.recruit;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.blankj.utilcode.util.BarUtils;
import com.google.android.material.tabs.TabLayout;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.ViewPagerAdapter;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.dialog.BaseDialog;
import com.shuangduan.zcy.dialog.CustomDialog;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view.recruit
 * @class describe  招采信息详情
 * @time 2019/7/12 9:24
 * @change
 * @chang time
 * @class describe
 */
public class RecruitDetailActivity extends BaseActivity {
    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.iv_bar_right)
    AppCompatImageView ivBarRight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tab)
    TabLayout tab;
    @BindView(R.id.vp)
    ViewPager vp;
    @BindView(R.id.iv_collect)
    ImageView ivCollect;
    @BindView(R.id.tv_collect)
    TextView tvCollect;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_recruit_detail;
    }

    @Override
    protected void initDataAndEvent() {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(getString(R.string.message_detail));

        tvTitle.setText("山东省济南市莱芜区影城电子信息产业园一期");
        tvTime.setText(String.format(getString(R.string.format_release_time), "2019-06-20 09:30"));

        Fragment[] fragments = new Fragment[1];
        fragments[0] = IntroductionBidFragment.newInstance();

        tab.addTab(tab.newTab());
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), fragments, getResources().getStringArray(R.array.recruit_detail));
        vp.setAdapter(viewPagerAdapter);
        tab.setupWithViewPager(vp);
    }

    @OnClick({R.id.iv_bar_back, R.id.iv_bar_right, R.id.fl_collect, R.id.fl_read_detail})
    void onClick(View view){
        switch (view.getId()){
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.iv_bar_right:

                break;
            case R.id.fl_collect:

                break;
            case R.id.fl_read_detail:
                new CustomDialog(this)
                        .setTip("查看此消息请支付1亿元")
                        .setCallBack(new BaseDialog.CallBack() {
                            @Override
                            public void cancel() {

                            }

                            @Override
                            public void ok(String s) {

                            }
                        })
                        .showDialog();
                break;
        }
    }
}
