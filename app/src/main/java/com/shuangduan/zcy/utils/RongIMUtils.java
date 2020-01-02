package com.shuangduan.zcy.utils;

import android.content.Context;

import io.rong.imkit.RongIM;
import io.rong.imlib.IRongCallback;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.message.RichContentMessage;

/**
 * @ProjectName: information_platform_android
 * @Package: com.shuangduan.zcy.utils
 * @ClassName: RongIMUtils
 * @Description: 融云监听工具类
 * @Author: 鹿鸿祥
 * @CreateDate: 2020/1/2 9:46
 * @UpdateUser: 鹿鸿祥
 * @UpdateDate: 2020/1/2 9:46
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class RongIMUtils {

    /**
     * @param title  图文标题。
     * @param content 图文内容。
     * @param imageUrl 图文图片地址。
     * @param id 物资详情id。
     * @param targetId 供应商id。
     */
    public static void getImageContentMessage(Context context,String type,String title, String content, String imageUrl, int id, String targetId, String company){
        RichContentMessage richContentMessage = RichContentMessage.obtain(title,content,imageUrl);
        richContentMessage.setUrl(type);
        richContentMessage.setExtra(String.valueOf(id));
        Message myMessage = Message.obtain(targetId, Conversation.ConversationType.PRIVATE, richContentMessage);
        RongIM.getInstance().sendMessage(myMessage, null, null, new IRongCallback.ISendMessageCallback() {
            @Override
            public void onAttached(Message message) {
                //消息本地数据库存储成功的回调
            }

            @Override
            public void onSuccess(Message message) {
                //消息通过网络发送成功的回调
                RongIM.getInstance().startPrivateChat(context, targetId,company);
            }

            @Override
            public void onError(Message message, RongIMClient.ErrorCode errorCode) {
                //消息发送失败的回调
            }
        });
    }
}
