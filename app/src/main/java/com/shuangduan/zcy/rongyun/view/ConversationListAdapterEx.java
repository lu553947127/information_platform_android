package com.shuangduan.zcy.rongyun.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

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

import io.rong.imkit.model.UIConversation;
import io.rong.imkit.widget.adapter.ConversationListAdapter;
import io.rong.imlib.model.Conversation;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.rongyun.view
 * @ClassName: ConversationListAdapterEx
 * @Description: 会话列表的适配器
 * @Author: 鹿鸿祥
 * @CreateDate: 2019/10/9 14:42
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2019/10/9 14:42
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class ConversationListAdapterEx extends ConversationListAdapter {

    public ConversationListAdapterEx(Context context) {
        super(context);
    }

    @Override
    protected View newView(Context context, int position, ViewGroup group) {
        return super.newView(context, position, group);
    }

    @Override
    protected void bindView(View v, int position, UIConversation data) {
        super.bindView(v, position, data);
        if (data != null) {
            if (data.getConversationType().equals(Conversation.ConversationType.PRIVATE)){
                getFriendData(data.getConversationTargetId(),v);
            }else {
                (v.findViewById(R.id.iv_sgs)).setVisibility(View.INVISIBLE);
            }
        }
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
                    }

                    @Override
                    public void onSuccess(com.lzy.okgo.model.Response<String> response) {
                        try {
                            IMWechatUserInfoBean bean=new Gson().fromJson(response.body(),IMWechatUserInfoBean.class);
                            if (bean.getCode().equals("200")){
                                if (bean.getData()!=null){
                                    if (bean.getData().getCard_status().equals("2")){
                                        (v.findViewById(R.id.iv_sgs)).setVisibility(View.VISIBLE);
                                    }else {
                                        (v.findViewById(R.id.iv_sgs)).setVisibility(View.INVISIBLE);
                                    }
                                }else {
                                    (v.findViewById(R.id.iv_sgs)).setVisibility(View.INVISIBLE);
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
