package com.shuangduan.zcy.view.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.MainThread;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.dialog.BaseDialog;
import com.shuangduan.zcy.dialog.BusinessExpDialog;
import com.shuangduan.zcy.dialog.SexDialog;
import com.shuangduan.zcy.dialog.SubscriptionTypeDialog;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.model.event.CityEvent;
import com.shuangduan.zcy.utils.AndroidBug5497Workaround;
import com.shuangduan.zcy.view.MainActivity;
import com.shuangduan.zcy.view.mine.BusinessAreaActivity;
import com.shuangduan.zcy.vm.UserInfoVm;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 宁文强 QQ:858777523
 * @name ZICAICloudPlatform
 * @class name：com.example.zicaicloudplatform.view.activity
 * @class describe
 * @time 2019/7/5 10:53
 * @change
 * @chang time
 * @class describe
 */
public class UserInfoInputActivity extends BaseActivity {

    @BindView(R.id.tv_bar_title)
    AppCompatTextView tvBarTitle;
    @BindView(R.id.tv_bar_right)
    AppCompatTextView tvBarRight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.edt_name)
    AppCompatEditText edtName;
    @BindView(R.id.tv_sex)
    AppCompatTextView tvSex;
    @BindView(R.id.edt_mobile)
    AppCompatEditText edtMobile;
    @BindView(R.id.edt_id_card)
    AppCompatEditText edtIdCard;
    @BindView(R.id.edt_email)
    AppCompatEditText edtEmail;
    @BindView(R.id.edt_company)
    AppCompatEditText edtCompany;
    @BindView(R.id.edt_office)
    AppCompatEditText edtOffice;
    @BindView(R.id.tv_business_area)
    AppCompatTextView tvBusinessArea;
    @BindView(R.id.tv_business_exp)
    AppCompatTextView tvBusinessExp;
    @BindView(R.id.edt_production)
    EditText edtProduction;

    /*性别  1男 2女 0未选择*/
    private int sex = -1;
    /*业务经验 1:"0-3年",2:"2-3年",3:"3-5年",4:"5-10年"*/
    private int exp = 0;
    private int[] area;
    private UserInfoVm userInfoVm;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_user_info_input;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(getString(R.string.base_info));
        tvBarRight.setText(getString(R.string.save));
        AndroidBug5497Workaround.assistActivity(findViewById(android.R.id.content));

        userInfoVm = ViewModelProviders.of(this).get(UserInfoVm.class);
    }

    @OnClick({R.id.iv_bar_back, R.id.tv_bar_right, R.id.tv_sex, R.id.tv_business_area, R.id.tv_business_exp})
    void onClick(View view){
        switch (view.getId()){
            case R.id.iv_bar_back:
                ActivityUtils.finishAllActivities();
                break;
            case R.id.tv_bar_right:
//                new SubscriptionTypeDialog(this)
//                        .setCallBack(new BaseDialog.CallBack() {
//                            @Override
//                            public void cancel() {
//
//                            }
//
//                            @Override
//                            public void ok(String s) {
//                                ActivityUtils.startActivity(MainActivity.class);
//                            }
//                        })
//                        .showDialog();
                userInfoVm.infoSet(Objects.requireNonNull(edtName.getText()).toString(),
                        sex,
                        edtCompany.getText().toString(),
                        edtOffice.getText().toString(),
                        area,
                        exp,
                        edtProduction.getText().toString());
                userInfoVm.infoLiveData.observe(this, o -> {
                    SPUtils.getInstance().put(SpConfig.INFO_STATUS, 1);
                    finish();
                });
                userInfoVm.pageStateLiveData.observe(this, s -> {
                    switch (s){
                        case PageState.PAGE_LOADING:
                            showLoading();
                            break;
                        case PageState.PAGE_NET_ERROR:
                            ToastUtils.showShort(getString(R.string.net_not));
                            break;
                            default:
                                hideLoading();
                                break;
                    }
                });
                break;
            case R.id.tv_sex:
                new SexDialog(this)
                        .setSex(sex)
                        .setOnSexSelectListener(new SexDialog.OnSexSelectListener() {
                            @Override
                            public void man() {
                                sex = 1;
                                tvSex.setText(getString(R.string.man));
                            }

                            @Override
                            public void woman() {
                                sex = 2;
                                tvSex.setText(getString(R.string.woman));
                            }
                        });
                break;
            case R.id.tv_business_area:
                ActivityUtils.startActivity(BusinessAreaActivity.class);
                break;
            case R.id.tv_business_exp:
                new BusinessExpDialog(this)
                        .setSingleCallBack((item, position) -> {
                            tvBusinessExp.setText(item);
                            exp = position + 1;
                        })
                        .showDialog();
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void cityChange(CityEvent event){
        tvBusinessArea.setText(event.city);
        area = event.citys;
    }

    /**
     * 返回键退出应用
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            ActivityUtils.finishAllActivities();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

}
