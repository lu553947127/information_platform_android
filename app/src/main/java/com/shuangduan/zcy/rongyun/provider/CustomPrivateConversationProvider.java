package com.shuangduan.zcy.rongyun.provider;

import android.content.Context;
import android.text.InputFilter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
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

import io.rong.imkit.model.ConversationProviderTag;
import io.rong.imkit.model.UIConversation;
import io.rong.imkit.widget.provider.PrivateConversationProvider;
import io.rong.imlib.model.Conversation;

/**
 * @author 徐玉 QQ:876885613
 * @name information_platform_android
 * @class name：com.shuangduan.zcy.rongyun.provider
 * @class 复写融云的会话列表布局
 * @time 2019/9/9 9:16
 * @change
 * @chang time
 * @class describe
 */
@ConversationProviderTag(conversationType = "private", portraitPosition = 1)
public class CustomPrivateConversationProvider extends PrivateConversationProvider {

    @Override
    public View newView(Context context, ViewGroup viewGroup) {
        return super.newView(context, viewGroup);
    }

    @Override
    public void bindView(View v, int position, UIConversation data) {
//        UserInfo data1 = RongUserInfoManager.getInstance().getUserInfo(data.getConversationTargetId());
//        data1.getExtra();
        if (data.getConversationType().equals(Conversation.ConversationType.PRIVATE)){
            getFriendData(data.getConversationTargetId(),v);
        }else {
            ((TextView)(v.findViewById(R.id.rc_conversation_title))).setFilters(new InputFilter[] { new InputFilter.LengthFilter(12)});
            (v.findViewById(R.id.tv_company)).setVisibility(View.GONE);
            (v.findViewById(R.id.tv_post)).setVisibility(View.GONE);
        }
        super.bindView(v, position, data);
    }

    //会话列表头像名称显示
    private void getFriendData(String userId, View v) {

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
                            LogUtils.json(response.body());
                            if (bean.getCode().equals("200")){
                                if (bean.getData()!=null){
                                    (v.findViewById(R.id.tv_company)).setVisibility(View.VISIBLE);
                                    (v.findViewById(R.id.tv_post)).setVisibility(View.VISIBLE);
                                    ((TextView)v.findViewById(R.id.tv_company)).setText(bean.getData().getCompany());
                                    ((TextView)v.findViewById(R.id.tv_post)).setText(bean.getData().getPosition());
                                    ((TextView)(v.findViewById(R.id.rc_conversation_title))).setFilters(new InputFilter[] { new InputFilter.LengthFilter(5)});
                                }else {
                                    (v.findViewById(R.id.tv_company)).setVisibility(View.GONE);
                                    (v.findViewById(R.id.tv_post)).setVisibility(View.GONE);
                                }
                            }else {
                                ToastUtils.showShort(bean.getMsg());
                            }
                        }catch (JsonSyntaxException | IllegalStateException ignored){

                        }
                    }
                });
    }
}
