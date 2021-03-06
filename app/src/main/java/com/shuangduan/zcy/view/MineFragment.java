package com.shuangduan.zcy.view;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SPUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adminManage.view.AdminManageActivity;
import com.shuangduan.zcy.app.CustomConfig;
import com.shuangduan.zcy.app.MyApplication;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.base.BaseFragment;
import com.shuangduan.zcy.model.bean.SupplierRoleBean;
import com.shuangduan.zcy.model.event.AvatarEvent;
import com.shuangduan.zcy.model.event.RechargeSuccessEvent;
import com.shuangduan.zcy.model.event.UserNameEvent;
import com.shuangduan.zcy.utils.AnimationUtils;
import com.shuangduan.zcy.utils.DensityUtil;
import com.shuangduan.zcy.utils.image.ImageConfig;
import com.shuangduan.zcy.utils.image.ImageLoader;
import com.shuangduan.zcy.view.design.SmartDesignOrderActivity;
import com.shuangduan.zcy.view.income.MineIncomeActivity;
import com.shuangduan.zcy.view.mine.HelperActivity;
import com.shuangduan.zcy.view.mine.InformationSubActivity;
import com.shuangduan.zcy.view.mine.RecommendFriendsActivity;
import com.shuangduan.zcy.view.mine.collection.MineCollectionActivity;
import com.shuangduan.zcy.view.mine.demand.MineDemandActivity;
import com.shuangduan.zcy.view.mine.history.ViewRecordActivity;
import com.shuangduan.zcy.view.mine.material.MaterialOrderActivity;
import com.shuangduan.zcy.view.mine.project.MineProjectActivity;
import com.shuangduan.zcy.view.mine.set.SetActivity;
import com.shuangduan.zcy.view.mine.user.AuthenticationActivity;
import com.shuangduan.zcy.view.mine.user.UserInfoActivity;
import com.shuangduan.zcy.view.mine.wallet.MineWalletActivity;
import com.shuangduan.zcy.vm.HomeVm;
import com.shuangduan.zcy.vm.UserInfoVm;
import com.shuangduan.zcy.weight.AdaptationScrollView;
import com.shuangduan.zcy.weight.CircleImageView;
import com.shuangduan.zcy.weight.HeadZoomScrollView;

import org.greenrobot.eventbus.Subscribe;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author ?????? QQ:876885613
 * @name ZICAICloudPlatform
 * @class name???com.example.zicaicloudplatform.view.fragment
 * @class ??????
 * @time 2019/7/5 13:29
 * @change
 * @chang time
 * @class describe
 */
public class MineFragment extends BaseFragment {

    @BindView(R.id.scroll)
    HeadZoomScrollView scrollView;
    @BindView(R.id.rl_toolbar)
    RelativeLayout toolbar;
    @BindView(R.id.iv_user)
    CircleImageView ivUser;
    @BindView(R.id.tv_username)
    AppCompatTextView tvUsername;
    @BindView(R.id.tv_username_top)
    TextView tvUsernameTop;
    @BindView(R.id.iv_sgs)
    AppCompatImageView ivSgs;
    @BindView(R.id.tv_num_of_people)
    AppCompatTextView tvNumOfPeople;
    @BindView(R.id.tv_authentication)
    AppCompatTextView tvAuthentication;
    @BindView(R.id.iv_red_envelopes)
    AppCompatImageView ivRedEnvelopes;
    @BindView(R.id.ll_admin_manage)
    LinearLayout llAdminManage;
    @BindView(R.id.tv_turnover_material)
    AppCompatTextView tvTurnoverMaterial;
    @BindView(R.id.tv_device_management)
    AppCompatTextView tvDeviceManagement;
    @BindView(R.id.tv_order_management)
    AppCompatTextView tvOrderManagement;

    private UserInfoVm userInfoVm;
    private HomeVm homeVm;

    //?????????????????????????????????
    private static int sColor;
    private static float sAlpha;
    private AnimatorRun animatorRun;
    private Handler mineHandler;
    private ObjectAnimator animator;

    public static MineFragment newInstance() {
        Bundle args = new Bundle();
        MineFragment fragment = new MineFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initDataAndEvent(Bundle savedInstanceState, View v) {

        userInfoVm = mActivity.getViewModel(UserInfoVm.class);
        userInfoVm.getInfoLiveData.observe(this, userInfoBean -> {
            SPUtils.getInstance().put(SpConfig.USERNAME, userInfoBean.getUsername());
            tvUsername.setText(userInfoBean.getUsername());
            tvUsernameTop.setText(userInfoBean.getUsername());
            tvNumOfPeople.setText(String.format(getString(R.string.format_num_of_people), userInfoBean.getCount()));
            //????????????????????????
            ivSgs.setVisibility(userInfoBean.getCardStatus() == 2 ? View.VISIBLE : View.INVISIBLE);
            ImageLoader.load(mContext, new ImageConfig.Builder()
                    .url(userInfoBean.getImage_thumbnail())
                    .placeholder(R.drawable.default_head)
                    .errorPic(R.drawable.default_head)
                    .imageView(ivUser)
                    .build());
        });

        //????????????????????????
        homeVm = mActivity.getViewModel(HomeVm.class);
        homeVm.supplierRoleLiveData.observe(this, supplierRoleBean -> {
            //?????????????????? 0???????????? 1??????????????? 2????????? 3?????? 4?????????????????? 5???????????????
            SPUtils.getInstance().put(CustomConfig.MANAGE_STATUS, supplierRoleBean.getManage_status());
            //?????????????????????
            for (SupplierRoleBean.RoleBean bean : supplierRoleBean.getRole()) {
                getAdminManagePermission(bean, supplierRoleBean.getManage_status());
            }
        });

        //????????????????????????
        scrollView.setOnScrollChangeListener(new AdaptationScrollView.OnScrollChangeListener() {
            private int mScrollY_2 = 0;
            private int lastScrollY = 0;
            private int h = DensityUtil.dp2px(50);

            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (lastScrollY < h) {
                    scrollY = Math.min(h, scrollY);
                    mScrollY_2 = scrollY > h ? h : scrollY;

                    sAlpha = 1f * mScrollY_2 / h;

                    sColor = ((255 * mScrollY_2 / h) << 24) | ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.colorPrimary) & 0x00ffffff;

                    toolbar.setAlpha(sAlpha);
                    //??????????????????????????????
                    toolbar.setBackgroundColor(sColor);
                } else {
                    toolbar.setVisibility(View.VISIBLE);
                }
                lastScrollY = scrollY;
            }
        });

        //????????????????????????
        if (SPUtils.getInstance().getInt(SpConfig.IS_VERIFIED) == 2) {
            tvAuthentication.setText("???????????????");
        } else {
            tvAuthentication.setText("??????????????????");
        }

        mineHandler = MyApplication.getMainThreadHandler();
        animatorRun = new AnimatorRun();
        //????????????????????????
        mineHandler.postDelayed(animatorRun, 500);
    }

    @Override
    protected void initDataFromService() {

    }

    @Override
    public boolean isUseEventBus() {
        return true;
    }

    @OnClick({R.id.iv_help_top, R.id.iv_help, R.id.iv_set_top, R.id.iv_set, R.id.cl_user, R.id.tv_authentication, R.id.tv_wallet, R.id.rl_recommend_friends,
            R.id.tv_income, R.id.tv_mine_subscription, R.id.tv_read_history, R.id.tv_my_project, R.id.tv_my_demand,
            R.id.tv_my_collection, R.id.tv_my_material,R.id.tv_my_design, R.id.tv_turnover_material, R.id.tv_device_management, R.id.tv_order_management})
    void onClick(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.iv_help://??????
            case R.id.iv_help_top:
                ActivityUtils.startActivity(HelperActivity.class);
                break;
            case R.id.iv_set://??????
            case R.id.iv_set_top:
                ActivityUtils.startActivity(SetActivity.class);
                break;
            case R.id.cl_user://????????????
                bundle.putInt(CustomConfig.UID, SPUtils.getInstance().getInt(SpConfig.USER_ID));
                ActivityUtils.startActivity(bundle, UserInfoActivity.class);
                break;
            case R.id.tv_authentication://????????????
                bundle.putString(CustomConfig.UPLOAD_TYPE, CustomConfig.uploadTypeIdCard);
                bundle.putString(CustomConfig.AUTHENTICATION_TYPE, CustomConfig.ID_USER_INFO);
                ActivityUtils.startActivity(bundle, AuthenticationActivity.class);
                break;
            case R.id.tv_wallet://????????????
                ActivityUtils.startActivity(MineWalletActivity.class);
                break;
            case R.id.rl_recommend_friends://????????????
                ActivityUtils.startActivity(RecommendFriendsActivity.class);
                break;
            case R.id.tv_income://????????????
                ActivityUtils.startActivity(MineIncomeActivity.class);
                break;
            case R.id.tv_mine_subscription://????????????
                ActivityUtils.startActivity(InformationSubActivity.class);
                break;
            case R.id.tv_read_history://????????????
                ActivityUtils.startActivity(ViewRecordActivity.class);
                break;
            case R.id.tv_my_project://????????????
                ActivityUtils.startActivity(MineProjectActivity.class);
                break;
            case R.id.tv_my_demand://????????????
                ActivityUtils.startActivity(MineDemandActivity.class);
                break;
            case R.id.tv_my_collection://????????????
                ActivityUtils.startActivity(MineCollectionActivity.class);
                break;
            case R.id.tv_my_material: //????????????
                ActivityUtils.startActivity(MaterialOrderActivity.class);
                break;
            case R.id.tv_my_design://????????????
                ActivityUtils.startActivity(SmartDesignOrderActivity.class);
                break;
            case R.id.tv_turnover_material://????????????
                bundle.putInt(CustomConfig.IS_ADMIN_MANAGE, 1);
                ActivityUtils.startActivity(bundle, AdminManageActivity.class);
                break;
            case R.id.tv_device_management://????????????
                bundle.putInt(CustomConfig.IS_ADMIN_MANAGE, 2);
                ActivityUtils.startActivity(bundle, AdminManageActivity.class);
                break;
            case R.id.tv_order_management://????????????
                bundle.putInt(CustomConfig.IS_ADMIN_MANAGE, 3);
                ActivityUtils.startActivity(bundle, AdminManageActivity.class);
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        userInfoVm.userInfo();
        homeVm.getSupplierRole();
    }

    @Subscribe
    public void onEventUpdateUserName(UserNameEvent event) {
        tvUsername.setText(event.username);
        tvUsernameTop.setText(event.username);
    }

    @Subscribe
    public void onEventUpdateAvatar(AvatarEvent event) {
        ImageLoader.load(mContext, new ImageConfig.Builder()
                .url(event.getAvatar())
                .placeholder(R.drawable.default_head)
                .errorPic(R.drawable.default_head)
                .imageView(ivUser)
                .build());
    }

    @Subscribe
    public void onEventRechargeSuccess(RechargeSuccessEvent event) {
        userInfoVm.userInfo();
    }

    private int construction, equipment, equipment_order, construction_order;

    //????????????????????????
    private void getAdminManagePermission(SupplierRoleBean.RoleBean bean, int manage_status) {
        switch (bean.getMenu()) {
            case CustomConfig.CONSTRUCTION_LIST://??????????????????
                SPUtils.getInstance().put(CustomConfig.CONSTRUCTION_LIST, bean.getStatus());
                construction = bean.getStatus();
                break;
            case CustomConfig.CONSTRUCTION_DETAIL://??????????????????
                SPUtils.getInstance().put(CustomConfig.CONSTRUCTION_DETAIL, bean.getStatus());
                break;
            case CustomConfig.CONSTRUCTION_ADD://??????????????????
                SPUtils.getInstance().put(CustomConfig.CONSTRUCTION_ADD, bean.getStatus());
                break;
            case CustomConfig.CONSTRUCTION_EDIT://??????????????????
                SPUtils.getInstance().put(CustomConfig.CONSTRUCTION_EDIT, bean.getStatus());
                break;
            case CustomConfig.CONSTRUCTION_DELETE://??????????????????
                SPUtils.getInstance().put(CustomConfig.CONSTRUCTION_DELETE, bean.getStatus());
                break;
            case CustomConfig.EQIPMENT_LIST://????????????
                SPUtils.getInstance().put(CustomConfig.EQIPMENT_LIST, bean.getStatus());
                equipment = bean.getStatus();
                break;
            case CustomConfig.EQIPMENT_DETAIL://????????????
                SPUtils.getInstance().put(CustomConfig.EQIPMENT_DETAIL, bean.getStatus());
                break;
            case CustomConfig.EQIPMENT_ADD://????????????
                SPUtils.getInstance().put(CustomConfig.EQIPMENT_ADD, bean.getStatus());
                break;
            case CustomConfig.EQIPMENT_EDIT://????????????
                SPUtils.getInstance().put(CustomConfig.EQIPMENT_EDIT, bean.getStatus());
                break;
            case CustomConfig.EQIPMENT_DELETE://????????????
                SPUtils.getInstance().put(CustomConfig.EQIPMENT_DELETE, bean.getStatus());
                break;
            case CustomConfig.EQIPMENT_ORDER_LIST://??????????????????
                SPUtils.getInstance().put(CustomConfig.EQIPMENT_ORDER_LIST, bean.getStatus());
                equipment_order = bean.getStatus();
                break;
            case CustomConfig.EQIPMENT_ORDER_DETAIL://??????????????????
                SPUtils.getInstance().put(CustomConfig.EQIPMENT_ORDER_DETAIL, bean.getStatus());
                break;
            case CustomConfig.EQIPMENT_ORDER_EDIT://??????????????????
                SPUtils.getInstance().put(CustomConfig.EQIPMENT_ORDER_EDIT, bean.getStatus());
                break;
            case CustomConfig.CONSTRUCTION_ORDER_LIST://????????????????????????
                SPUtils.getInstance().put(CustomConfig.CONSTRUCTION_ORDER_LIST, bean.getStatus());
                construction_order = bean.getStatus();
                break;
            case CustomConfig.CONSTRUCTION_ORDER_DETAIL://????????????????????????
                SPUtils.getInstance().put(CustomConfig.CONSTRUCTION_ORDER_DETAIL, bean.getStatus());
                break;
            case CustomConfig.CONSTRUCTION_ORDER_EDIT://????????????????????????
                SPUtils.getInstance().put(CustomConfig.CONSTRUCTION_ORDER_EDIT, bean.getStatus());
                break;
        }
        getAdminEntrance(construction, equipment, equipment_order, construction_order, manage_status);
    }

    //??????????????????????????????
    private void getAdminEntrance(int construction, int equipment, int equipment_order, int construction_order, int manage_status) {
        switch (manage_status) {
            case 0://????????????
                llAdminManage.setVisibility(View.GONE);
                break;
            case 1://???????????????
                llAdminManage.setVisibility(View.VISIBLE);
                break;
            case 2://?????????
            case 3://??????
                llAdminManage.setVisibility(View.VISIBLE);
                SPUtils.getInstance().put(CustomConfig.INNER_SWITCH, 1);
                break;
            case 4://??????????????????
            case 5://???????????????
                SPUtils.getInstance().put(CustomConfig.INNER_SWITCH, 1);
                if (construction == 0 && equipment == 0 && equipment_order == 0 && construction_order == 0) {
                    llAdminManage.setVisibility(View.GONE);
                } else {
                    llAdminManage.setVisibility(View.VISIBLE);
                    if (construction == 1) {
                        tvTurnoverMaterial.setVisibility(View.VISIBLE);
                    } else {
                        tvTurnoverMaterial.setVisibility(View.GONE);
                    }
                    if (equipment == 1) {
                        tvDeviceManagement.setVisibility(View.VISIBLE);
                    } else {
                        tvDeviceManagement.setVisibility(View.GONE);
                    }
                    if (equipment_order == 1 || construction_order == 1) {
                        tvOrderManagement.setVisibility(View.VISIBLE);
                    } else {
                        tvOrderManagement.setVisibility(View.GONE);
                    }
                }
                break;
        }
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (sAlpha != 0 && sColor != 0) {
            toolbar.setAlpha(sAlpha);
            //??????????????????????????????
            toolbar.setBackgroundColor(sColor);
            toolbar.setVisibility(View.VISIBLE);
        }
    }

    class AnimatorRun implements Runnable {
        @Override
        public void run() {
            try {
                animator = AnimationUtils.tada(ivRedEnvelopes);
                animator.setRepeatCount(ValueAnimator.INFINITE);
                animator.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDestroyView() {
        if (animator != null) {
            animator.cancel();
            animator.clone();
            animator = null;
        }
        if (mineHandler != null) {
            mineHandler.removeCallbacks(animatorRun);
            mineHandler = null;
        }
        if (animatorRun != null) {
            animatorRun = null;
        }
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        sColor = 0;
        sAlpha = 0;
    }
}
