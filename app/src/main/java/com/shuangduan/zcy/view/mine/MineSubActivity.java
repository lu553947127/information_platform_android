package com.shuangduan.zcy.view.mine;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.blankj.utilcode.util.BarUtils;
import com.google.android.material.tabs.TabLayout;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.ViewPagerAdapter;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.dialog.BaseDialog;
import com.shuangduan.zcy.dialog.SubscriptionTypeDialog;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.model.bean.MyPhasesBean;
import com.shuangduan.zcy.vm.MineSubVm;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 宁文强 QQ:858777523
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.view.mine
 * @class describe  我的订阅
 * @time 2019/8/2 17:40
 * @change
 * @chang time
 * @class describe
 */
public class MineSubActivity extends BaseActivity {

    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.tv_bar_right)
    AppCompatTextView tvBarRight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;


    @BindView(R.id.fl_content)
    FrameLayout flConteng;

    private MineSubVm mineSubVm;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_mine_sub;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(getString(R.string.subscribe_message));
        tvBarRight.setText(getString(R.string.push_select));



        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        // 加载当前显示的Fragment
        transaction.replace(R.id.fl_content,  ProjectSubFragment.newInstance());
        transaction.commit(); // 提交创建Fragment请求


        mineSubVm = ViewModelProviders.of(this).get(MineSubVm.class);
        mineSubVm.phasesLiveData.observe(this, myPhasesBean -> {
            new SubscriptionTypeDialog(this)
                    .setItems(myPhasesBean)
                    .setCallBack(new BaseDialog.CallBack() {
                        @Override
                        public void cancel() {

                        }

                        @Override
                        public void ok(String s) {
                            if (mineSubVm.phasesLiveData.getValue() == null) {
                                return;
                            }
                            mineSubVm.phasesId.clear();
                            for (MyPhasesBean bean: mineSubVm.phasesLiveData.getValue()) {
                                if (bean.getIs_select() == 1)
                                    mineSubVm.phasesId.add(bean.getId());
                            }
                            mineSubVm.setPhases();
                        }
                    })
                    .showDialog();
        });
        mineSubVm.phasesSetLiveData.observe(this, o -> {
            mineSubVm.myProject();
            mineSubVm.myRecruit();
        });
        mineSubVm.pageStateLiveData.observe(this, s -> {
            switch (s){
                case PageState.PAGE_LOADING:
                    showLoading();
                    break;
                default:
                    hideLoading();
                    break;
            }
        });
    }

    @OnClick({R.id.iv_bar_back, R.id.tv_bar_right})
    void onClick(View v){
        switch (v.getId()){
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.tv_bar_right:
                mineSubVm.myPhases();
                break;
        }
    }
}
