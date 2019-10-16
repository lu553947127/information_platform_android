package com.shuangduan.zcy.rongyun.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.shuangduan.zcy.R;
import com.shuangduan.zcy.app.Common;
import com.shuangduan.zcy.app.SpConfig;
import com.shuangduan.zcy.model.api.retrofit.RetrofitHelper;
import com.shuangduan.zcy.model.bean.IMWechatUserInfoBean;

import io.rong.imkit.model.UIMessage;
import io.rong.imkit.widget.adapter.MessageListAdapter;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.UserInfo;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.rongyun.view
 * @ClassName: MessageListAdapterEx
 * @Description: java类作用描述
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/10/16 10:00
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/10/16 10:00
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class MessageListAdapterEx extends MessageListAdapter {


    public MessageListAdapterEx(Context context) {
        super(context);
    }

    @Override
    protected View newView(Context context, int position, ViewGroup group) {
        return super.newView(context, position, group);
    }

    @Override
    protected void bindView(View v, int position, UIMessage data) {
        super.bindView(v, position, data);
        if (data != null) {
            if (data.getConversationType().equals(Conversation.ConversationType.PRIVATE)||data.getConversationType().equals(Conversation.ConversationType.GROUP)){
                getFriendData(data.getTargetId(),v);
                getFriendData(String.valueOf(SPUtils.getInstance().getInt(SpConfig.USER_ID)),v);
            }else {
                getFriendData(String.valueOf(SPUtils.getInstance().getInt(SpConfig.USER_ID)),v);
            }
        }
    }

    //会话列表头像名称显示
    private UserInfo getFriendData(String userId, View v) {

        OkGo.<String>post(RetrofitHelper.BASE_TEST_URL+ Common.WECHAT_USER_INFO)
                .tag(this)
                .headers("token", SPUtils.getInstance().getString(SpConfig.TOKEN))//请求头
                .params("id",userId)//对应的用户编号
                .params("user_id", SPUtils.getInstance().getInt(SpConfig.USER_ID))//用户编号
                .execute(new com.lzy.okgo.callback.StringCallback() {//返回值

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        LogUtils.json(response.body());
                    }

                    @Override
                    public void onSuccess(com.lzy.okgo.model.Response<String> response) {
                        try {
                            IMWechatUserInfoBean bean=new Gson().fromJson(response.body(),IMWechatUserInfoBean.class);
                            LogUtils.json("MessageListAdapterEx----------"+response.body());
                            if (bean.getCode().equals("200")){
                                if (bean.getData()!=null){
                                    if (bean.getData().getCard_status()!=null){
                                        if (bean.getData().getCard_status().equals("2")){
                                            (v.findViewById(R.id.iv_sgs)).setVisibility(View.VISIBLE);
                                            (v.findViewById(R.id.iv_sgs_right)).setVisibility(View.VISIBLE);
                                        }else if (bean.getData().getCard_status().equals("0")){
                                            (v.findViewById(R.id.iv_sgs)).setVisibility(View.INVISIBLE);
                                            (v.findViewById(R.id.iv_sgs_right)).setVisibility(View.INVISIBLE);
                                        }else {
                                            (v.findViewById(R.id.iv_sgs)).setVisibility(View.INVISIBLE);
                                            (v.findViewById(R.id.iv_sgs_right)).setVisibility(View.INVISIBLE);
                                        }
                                    }else {
                                        (v.findViewById(R.id.iv_sgs)).setVisibility(View.INVISIBLE);
                                        (v.findViewById(R.id.iv_sgs_right)).setVisibility(View.INVISIBLE);
                                    }
                                }else {
                                    (v.findViewById(R.id.iv_sgs)).setVisibility(View.INVISIBLE);
                                    (v.findViewById(R.id.iv_sgs_right)).setVisibility(View.INVISIBLE);
                                }
                            }else {
                                ToastUtils.showShort(bean.getMsg());
                            }
                        }catch (JsonSyntaxException | IllegalStateException ignored){

                        }
                    }
                });
        return null;
    }
}
