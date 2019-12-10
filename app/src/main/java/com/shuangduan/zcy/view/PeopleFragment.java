package com.shuangduan.zcy.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import com.blankj.utilcode.util.ActivityUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseLazyFragment;
import com.shuangduan.zcy.utils.BarUtils;
import com.shuangduan.zcy.view.income.IncomePeopleActivity;
import com.shuangduan.zcy.view.mine.AboutOursActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 徐玉 QQ:876885613
 * @name ZICAICloudPlatform
 * @class name：com.example.zicaicloudplatform.view.fragment
 * @class 人脉
 * @time 2019/7/5 13:29
 * @change
 * @chang time
 * @class describe
 */
public class PeopleFragment extends BaseLazyFragment {
    @BindView(R.id.fake_status_bar)
    View fakeStatusBar;
    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.iv_bar_right)
    AppCompatImageView ivBarRight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    public static PeopleFragment newInstance() {
        Bundle args = new Bundle();
        PeopleFragment fragment = new PeopleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_people;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.setStatusBarColorRes(fakeStatusBar, getResources().getColor(android.R.color.transparent));

        tvBarTitle.setText(getString(R.string.people));
        ivBarRight.setImageResource(R.drawable.icon_help_white);
    }

    @Override
    protected void initDataFromService() {

    }

    @OnClick({R.id.tv_first_degree, R.id.tv_second_degree, R.id.tv_three_degree,R.id.iv_bar_right})
    void onClick(View view){
        Bundle bundle = new Bundle();
        switch (view.getId()){
            case R.id.tv_first_degree:
                bundle.putInt(CustomConfig.PEOPLE_DEGREE, CustomConfig.FIRST_DEGREE);
                ActivityUtils.startActivity(bundle, IncomePeopleActivity.class);
                break;
            case R.id.tv_second_degree:
                bundle.putInt(CustomConfig.PEOPLE_DEGREE, CustomConfig.SECOND_DEGREE);
                ActivityUtils.startActivity(bundle, IncomePeopleActivity.class);
                break;
            case R.id.tv_three_degree:
                bundle.putInt(CustomConfig.PEOPLE_DEGREE, CustomConfig.THREE_DEGREE);
                ActivityUtils.startActivity(bundle, IncomePeopleActivity.class);
                break;
            case R.id.iv_bar_right:
                bundle.putString("income", "1");
                ActivityUtils.startActivity(bundle, AboutOursActivity.class);
                break;
        }
    }
}
