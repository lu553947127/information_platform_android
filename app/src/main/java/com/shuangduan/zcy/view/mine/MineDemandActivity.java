package com.shuangduan.zcy.view.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ConvertUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.ViewPagerAdapter;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.dialog.pop.CommonPopupWindow;
import com.shuangduan.zcy.weight.MyViewPager;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view.mine
 * @class describe  我的需求
 * @time 2019/8/12 16:28
 * @change
 * @chang time
 * @class describe
 */
public class MineDemandActivity extends BaseActivity {
    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.tv_bar_right)
    AppCompatTextView tvBarRight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.vp)
    MyViewPager vp;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_mine_demand;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(getString(R.string.my_demand));
        tvBarRight.setText(getString(R.string.filter));

        Fragment[] fragments = new Fragment[]{
                FindRelationshipFragment.newInstance(),
                FindSubstanceFragment.newInstance(),
                FindBuyerFragment.newInstance()
        };
        vp.setOffscreenPageLimit(2);
        vp.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), fragments, null));
    }

    private CommonPopupWindow popupWindow;
    @OnClick({R.id.iv_bar_back, R.id.tv_bar_right})
    void onClick(View v){
        switch (v.getId()){
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.tv_bar_right:
                if (popupWindow == null){
                    popupWindow = new CommonPopupWindow.Builder(this)
                            .setView(R.layout.dialog_filter_demand)
                            .setOutsideTouchable(true)
                            .setWidthAndHeight(ConvertUtils.dp2px(82), ConvertUtils.dp2px(104))
                            .setViewOnclickListener((view, layoutResId) -> {
                                TextView tvFindRelationship = view.findViewById(R.id.tv_find_relationship);
                                TextView tvFindSubstance = view.findViewById(R.id.tv_find_substance);
                                TextView tvFindBuyer = view.findViewById(R.id.tv_find_buyer);
                                //初始颜色
                                tvFindRelationship.setTextColor(getResources().getColor(R.color.colorPrimary));
                                tvFindRelationship.setOnClickListener(l ->{
                                    tvFindRelationship.setTextColor(getResources().getColor(R.color.colorPrimary));
                                    tvFindSubstance.setTextColor(getResources().getColor(R.color.color_666666));
                                    tvFindBuyer.setTextColor(getResources().getColor(R.color.color_666666));
                                    vp.setCurrentItem(0);
                                    popupWindow.dismiss();
                                });
                                tvFindSubstance.setOnClickListener(l ->{
                                    tvFindRelationship.setTextColor(getResources().getColor(R.color.color_666666));
                                    tvFindSubstance.setTextColor(getResources().getColor(R.color.colorPrimary));
                                    tvFindBuyer.setTextColor(getResources().getColor(R.color.color_666666));
                                    vp.setCurrentItem(1);
                                    popupWindow.dismiss();
                                });
                                tvFindBuyer.setOnClickListener(l ->{
                                    tvFindRelationship.setTextColor(getResources().getColor(R.color.color_666666));
                                    tvFindSubstance.setTextColor(getResources().getColor(R.color.color_666666));
                                    tvFindBuyer.setTextColor(getResources().getColor(R.color.colorPrimary));
                                    vp.setCurrentItem(2);
                                    popupWindow.dismiss();
                                });
                            })
                            .create();
                }
                if (!popupWindow.isShowing()){
                    popupWindow.showAsDropDown(tvBarRight, ConvertUtils.dp2px(-40), ConvertUtils.dp2px(-10));
                }
                break;
        }
    }
}
