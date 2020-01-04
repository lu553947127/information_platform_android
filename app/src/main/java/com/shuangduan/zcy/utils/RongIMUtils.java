package com.shuangduan.zcy.utils;

import android.content.Context;

import com.blankj.utilcode.util.LogUtils;

import io.rong.imkit.RongIM;
import io.rong.imlib.IRongCallback;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.MessageContent;
import io.rong.message.ImageMessage;
import io.rong.message.RichContentMessage;
import io.rong.message.TextMessage;
import io.rong.message.VoiceMessage;

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

    public static void getSendMessageListener(){
        RongIM.getInstance().setSendMessageListener(new RongIM.OnSendMessageListener() {

            /**
             * 消息发送前监听器处理接口（是否发送成功可以从 SentStatus 属性获取）。
             *
             * @param message 发送的消息实例。
             * @return 处理后的消息实例。
             */
            @Override
            public Message onSend(Message message) {
                return message;
            }

            /**
             * 消息在 UI 展示后执行/自己的消息发出后执行,无论成功或失败。
             *
             * @param message              消息实例。
             * @param sentMessageErrorCode 发送消息失败的状态码，消息发送成功 SentMessageErrorCode 为 null。
             * @return true 表示走自己的处理方式，false 走融云默认处理方式。
             */
            @Override
            public boolean onSent(Message message, RongIM.SentMessageErrorCode sentMessageErrorCode) {

                if(message.getSentStatus()== Message.SentStatus.FAILED){
                    if(sentMessageErrorCode== RongIM.SentMessageErrorCode.NOT_IN_CHATROOM){
                        //不在聊天室
                    }else if(sentMessageErrorCode== RongIM.SentMessageErrorCode.NOT_IN_DISCUSSION){
                        //不在讨论组
                    }else if(sentMessageErrorCode== RongIM.SentMessageErrorCode.NOT_IN_GROUP){
                        //不在群组
                    }else if(sentMessageErrorCode== RongIM.SentMessageErrorCode.REJECTED_BY_BLACKLIST){
                        //你在他的黑名单中
                    }
                }

                MessageContent messageContent = message.getContent();
                if (messageContent instanceof TextMessage) {//文本消息
                    TextMessage textMessage = (TextMessage) messageContent;
                    LogUtils.e( "onSent-TextMessage:" + textMessage.getContent());
                } else if (messageContent instanceof ImageMessage) {//图片消息
                    ImageMessage imageMessage = (ImageMessage) messageContent;
                    LogUtils.e(  "onSent-ImageMessage:" + imageMessage.getRemoteUri());
                } else if (messageContent instanceof VoiceMessage) {//语音消息
                    VoiceMessage voiceMessage = (VoiceMessage) messageContent;
                    LogUtils.e(  "onSent-voiceMessage:" + voiceMessage.getUri().toString());
                } else if (messageContent instanceof RichContentMessage) {//图文消息
                    RichContentMessage richContentMessage = (RichContentMessage) messageContent;
                    LogUtils.e(  "onSent-RichContentMessage:" + richContentMessage.getContent());
                } else {
                    LogUtils.e(  "onSent-其他消息，自己来判断处理");
                }
                return false;
            }
        });
    }
}
