package com.shuangduan.zcy.view.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseActivity;
import com.shuangduan.zcy.dialog.BaseDialog;
import com.shuangduan.zcy.dialog.BusinessExpDialog;
import com.shuangduan.zcy.dialog.SexDialog;
import com.shuangduan.zcy.dialog.SubscriptionTypeDialog;
import com.shuangduan.zcy.model.api.PageState;
import com.shuangduan.zcy.model.event.MultiAreaEvent;
import com.shuangduan.zcy.utils.AndroidBug5497Workaround;
import com.shuangduan.zcy.view.MainActivity;
import com.shuangduan.zcy.view.MultiAreaActivity;
import com.shuangduan.zcy.vm.IMConnectVm;
import com.shuangduan.zcy.vm.MineSubVm;
import com.shuangduan.zcy.vm.UserInfoVm;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author 徐玉 QQ:876885613
 * @name ZICAICloudPlatform
 * @class name：com.example.zicaicloudplatform.view.activity
 * @class describe     首次登录需要录入信息
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
    private int sex = 0;
    /*业务经验 1:"0-3年",2:"2-3年",3:"3-5年",4:"5-10年"*/
    private int exp = 0;
    private UserInfoVm userInfoVm;
    private MineSubVm mineSubVm;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_user_info_input;
    }

    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState) {
        AndroidBug5497Workaround.assistActivity(findViewById(android.R.id.content));
        BarUtils.addMarginTopEqualStatusBarHeight(toolbar);
        tvBarTitle.setText(getString(R.string.base_info));
        tvBarRight.setText(getString(R.string.save));


        IMConnectVm imConnectVm;
        //初始化，融云链接服务器
        imConnectVm = getViewModel(IMConnectVm.class);
        imConnectVm.tokenLiveData.observe(this, imTokenBean -> {
            String token = imTokenBean.getToken();
            SPUtils.getInstance().put(SpConfig.IM_TOKEN, token);
        });

        userInfoVm = getViewModel(UserInfoVm.class);
        userInfoVm.infoLiveData.observe(this, o -> {
            SPUtils.getInstance().put(SpConfig.INFO_STATUS, 1);
            //获取融云token
            imConnectVm.getToken();
            mineSubVm.myPhases();
        });
        userInfoVm.pageStateLiveData.observe(this, s -> {
            switch (s) {
                case PageState.PAGE_LOADING:
                    showLoading();
                    break;
                default:
                    hideLoading();
                    break;
            }
        });
        mineSubVm = getViewModel(MineSubVm.class);
        mineSubVm.phasesLiveData.observe(this, myPhasesBean -> {
            new SubscriptionTypeDialog(this)
                    .setItems(myPhasesBean)
                    .setCallBack(new BaseDialog.CallBack() {
                        @Override
                        public void cancel() {
                            ActivityUtils.startActivity(MainActivity.class);
                            finish();
                        }

                        @Override
                        public void ok(String s) {
                            mineSubVm.setPhases();
                        }
                    })
                    .showDialog();
        });
        mineSubVm.phasesSetLiveData.observe(this, o -> {
            ActivityUtils.startActivity(MainActivity.class);
            finish();
        });
        mineSubVm.pageStateLiveData.observe(this, s -> {
            switch (s) {
                case PageState.PAGE_LOADING:
                    showLoading();
                    break;
                default:
                    hideLoading();
                    break;
            }
        });
    }

    @OnClick({R.id.iv_bar_back, R.id.tv_bar_right, R.id.tv_sex, R.id.tv_business_area, R.id.tv_business_exp})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_bar_back:
                finish();
                break;
            case R.id.tv_bar_right://保存
                if (StringUtils.isTrimEmpty(edtName.getText().toString())) {
                    ToastUtils.showShort("请输入姓名");
                    return;
                }
                userInfoVm.infoSet(Objects.requireNonNull(edtName.getText()).toString(),
                        sex,
                        Objects.requireNonNull(edtCompany.getText()).toString(),
                        Objects.requireNonNull(edtOffice.getText()).toString(),
                        userInfoVm.multiAreaLiveData.getValue(),
                        exp,
                        edtProduction.getText().toString());
                break;
            case R.id.tv_sex://性别
                addDialog(new SexDialog(this)
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
                        }).showDialog());
                break;
            case R.id.tv_business_area://业务地区
                ActivityUtils.startActivityForResult(this, MultiAreaActivity.class, 200);
                break;
            case R.id.tv_business_exp://业务经验
                new BusinessExpDialog(this)
                        .setSingleCallBack((item, position) -> {
                            tvBusinessExp.setText(item);
                            exp = position + 1;
                        })
                        .showDialog();
                break;
        }
    }

//    @Subscribe
//    public void onEventServiceCity(MultiAreaEvent event) {
//        if (event.getCityResult().size()<=5){
//            userInfoVm.multiAreaLiveData.postValue(event);
//            tvBusinessArea.setText(event.getStringResult());
//        }else {
//            ToastUtils.showShort("业务地区最多只能选择5个");
//        }
//    }

    private List<Integer> cityList;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200 && resultCode == 200) {
            if (data != null) {
                String citys = data.getStringExtra("citys");
                String citystr = data.getStringExtra("citystr");
                cityList = new Gson().fromJson(citys, List.class);
                userInfoVm.multiAreaLiveData.postValue(new MultiAreaEvent(cityList,citystr));
                tvBusinessArea.setText(citystr);
            }
        }

    }
}
