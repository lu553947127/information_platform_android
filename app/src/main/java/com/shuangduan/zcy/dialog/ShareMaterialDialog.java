package com.shuangduan.zcy.dialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.adapter.ShareFriendAdapter;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.listener.BaseUiListener;
import com.shuangduan.zcy.manage.ShareManage;
import com.shuangduan.zcy.model.bean.IMFriendListBean;
import com.shuangduan.zcy.model.bean.MaterialDetailBean;
import com.shuangduan.zcy.model.bean.ShareBean;
import com.shuangduan.zcy.rongyun.view.IMFriendMoreActivity;
import com.shuangduan.zcy.utils.LoginUtils;
import com.shuangduan.zcy.utils.RongIMUtils;
import com.shuangduan.zcy.utils.ShareUtils;
import com.tencent.tauth.Tencent;

import java.util.List;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.dialog
 * @ClassName: ShareMaterialDialog
 * @Description: java类作用描述
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/12/31 16:34
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/12/31 16:34
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class ShareMaterialDialog {
    private Activity activity;
    private List<IMFriendListBean.ListBean> imFriendList;
    private ShareBean.DataBean shareBean;
    private BottomSheetDialogs btn_dialog;
    private Tencent mTencent;
    private BaseUiListener qqListener;
    private ShareManage shareManage;
    private String type,unitPrice;
    private MaterialDetailBean materialDetailBean;

    public ShareMaterialDialog(Activity activity,List<IMFriendListBean.ListBean> imFriendList,ShareBean.DataBean shareBean,String type,MaterialDetailBean materialDetailBean,String unitPrice) {
        this.activity = activity;
        this.imFriendList = imFriendList;
        this.shareBean = shareBean;
        this.type = type;
        this.materialDetailBean = materialDetailBean;
        this.unitPrice = unitPrice;
        mTencent = Tencent.createInstance(ShareUtils.app_id_qq, activity);
        qqListener = new BaseUiListener();
        shareManage = ShareManage.newInstance(activity.getApplicationContext());
    }

    //开始分享操作
    @SuppressLint("InflateParams")
    public void showShareDialog() {
        //底部滑动对话框
        btn_dialog = new BottomSheetDialogs(activity);
        //设置自定view
        View dialog_view = activity.getLayoutInflater().inflate(R.layout.dialog_share_material, null);
        //把布局添加进去
        btn_dialog.setContentView(dialog_view);
        //去除系统默认的背景色
        try {
            // hack bg color of the BottomSheetDialog
            ViewGroup parent = (ViewGroup) dialog_view.getParent();
            parent.setBackgroundResource(android.R.color.transparent);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ((ViewGroup.MarginLayoutParams) dialog_view.findViewById(R.id.tv_cancel).getLayoutParams()).bottomMargin = 60;
        RecyclerView recyclerView = dialog_view.findViewById(R.id.rv);
        if (imFriendList!=null&&imFriendList.size()>1){
            if (imFriendList.get(0).getUserId().equals("18"))imFriendList.remove(0);
            if (!imFriendList.get(imFriendList.size()-1).getName().equals("更多"))imFriendList.add(new IMFriendListBean.ListBean("更多"));
            recyclerView.setLayoutManager(new GridLayoutManager(activity, 5));
            ShareFriendAdapter shareFriendAdapter = new ShareFriendAdapter(R.layout.adapter_share_friend, imFriendList);
            recyclerView.setAdapter(shareFriendAdapter);
            shareFriendAdapter.setOnItemClickListener((adapter, view, position) -> {
                IMFriendListBean.ListBean listBean = shareFriendAdapter.getData().get(position);
                if (listBean.getName().equals("更多")){
                    Bundle bundle = new Bundle();
                    bundle.putInt("flag",1);
                    bundle.putString("type",type);
                    bundle.putString("unitPrice",unitPrice);
                    bundle.putString("material_name",materialDetailBean.getMaterialName());
                    bundle.putString("images",materialDetailBean.getImages().get(0).getUrl());
                    bundle.putInt("id",materialDetailBean.getId());
                    ActivityUtils.startActivity(bundle, IMFriendMoreActivity.class);
                }else {
                    if (Integer.valueOf(listBean.getUserId())!= SPUtils.getInstance().getInt(SpConfig.USER_ID)){
                        RongIMUtils.getImageContentMessage(activity
                                ,type
                                ,materialDetailBean.getMaterialName()
                                ,unitPrice
                                ,materialDetailBean.getImages().get(0).getUrl()
                                ,materialDetailBean.getId()
                                ,listBean.getUserId()
                                ,listBean.getName());
                    }
                }
                btn_dialog.cancel();
            });
        }else {
            recyclerView.setVisibility(View.GONE);
        }
        dialog_view.findViewById(R.id.ll_wechat_friend).setOnClickListener(view -> {
            if (!LoginUtils.isWeiXinInstall(activity)) {
                ToastUtils.showShort("您还没有安装微信，请先安装微信客户端");
                return;
            }
            ShareUtils.shareWeChat(ShareUtils.FRIEND, shareBean.getUrl(), shareBean.getTitle(), shareBean.getDes(), shareManage.getBitmap());
            btn_dialog.cancel();
        });
        dialog_view.findViewById(R.id.ll_wechat_circle).setOnClickListener(view -> {
            if (!LoginUtils.isWeiXinInstall(activity)) {
                ToastUtils.showShort("您还没有安装微信，请先安装微信客户端");
                return;
            }
            ShareUtils.shareWeChat(ShareUtils.FRIEND_CIRCLE,shareBean.getUrl(), shareBean.getTitle(), shareBean.getDes(), shareManage.getBitmap());
            btn_dialog.cancel();
        });
        dialog_view.findViewById(R.id.ll_qq_friend).setOnClickListener(view -> {
            if (!LoginUtils.isQQClientAvailable(activity)) {
                ToastUtils.showShort("您还没有安装QQ，请先安装QQ客户端");
                return;
            }
            ShareUtils.shareQQ(activity, mTencent, qqListener, shareBean.getUrl(), shareBean.getTitle(), shareBean.getDes(), shareBean.getImage());
            btn_dialog.cancel();
        });
        dialog_view.findViewById(R.id.ll_qq_stone).setOnClickListener(view -> {
            if (!LoginUtils.isQQClientAvailable(activity)) {
                ToastUtils.showShort("您还没有安装QQ，请先安装QQ客户端");
                return;
            }
            ShareUtils.shareQQStone(activity, mTencent, qqListener, shareBean.getUrl(), shareBean.getTitle(), shareBean.getDes(), shareBean.getImage());
            btn_dialog.cancel();
        });
        dialog_view.findViewById(R.id.tv_cancel).setOnClickListener(view -> btn_dialog.cancel());
        btn_dialog.show();
    }
}
