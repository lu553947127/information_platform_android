package com.shuangduan.zcy.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import com.blankj.utilcode.util.ActivityUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.base.BaseLazyFragment;
import com.shuangduan.zcy.utils.BarUtils;
import com.shuangduan.zcy.view.income.IncomePeopleActivity;
import com.shuangduan.zcy.vm.IncomePeopleVm;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 宁文强 QQ:858777523
 * @name ZICAICloudPlatform
 * @class name：com.example.zicaicloudplatform.view.fragment
 * @class describe
 * @time 2019/7/5 13:29
 * @change
 * @chang time
 * @class describe
 */
public class PeopleFragment extends BaseLazyFragment {
    @BindView(R.id.fake_status_bar)
    View fakeStatusBar;
    @BindView(R.id.iv_bar_back)
    AppCompatImageView ivBarBack;
    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_income_amount)
    TextView tvIncomeAmount;
    private IncomePeopleVm incomePeopleVm;

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
        BarUtils.setStatusBarColorRes(fakeStatusBar, getResources().getColor(R.color.colorPrimary));
        ivBarBack.setVisibility(View.INVISIBLE);
        tvBarTitle.setText(getString(R.string.people));

        incomePeopleVm = ViewModelProviders.of(this).get(IncomePeopleVm.class);
        incomePeopleVm.showLiveData.observe(this, peopleBean -> {
            isInited = true;
            tvIncomeAmount.setText(peopleBean.getNetworking_funds()+"元");
        });
    }

    @Override
    protected void initDataFromService() {
        incomePeopleVm.show();
    }

    @OnClick({R.id.tv_first_degree, R.id.tv_second_degree, R.id.tv_three_degree, R.id.tv_four_degree, R.id.tv_five_degree, R.id.tv_six_degree, })
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
            case R.id.tv_four_degree:
                bundle.putInt(CustomConfig.PEOPLE_DEGREE, CustomConfig.FOUR_DEGREE);
                ActivityUtils.startActivity(bundle, IncomePeopleActivity.class);
                break;
            case R.id.tv_five_degree:
                bundle.putInt(CustomConfig.PEOPLE_DEGREE, CustomConfig.FIVE_DEGREE);
                ActivityUtils.startActivity(bundle, IncomePeopleActivity.class);
                break;
            case R.id.tv_six_degree:
                bundle.putInt(CustomConfig.PEOPLE_DEGREE, CustomConfig.SIX_DEGREE);
                ActivityUtils.startActivity(bundle, IncomePeopleActivity.class);
                break;
        }
    }
}
