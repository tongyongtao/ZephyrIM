package com.tongyt.message;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
public class Message implements Serializable {

    private static final Map<Integer, Class<? extends Message>> messageClasses = new HashMap<>();

    /**
     * 消息请求的顺序id
     */
    private int sequenceId;

    /**
     * 消息类型
     */
    private int messageType;


    public int getMessageType() {
        return messageType;
    }

    /**
     * 根据消息类型字节，获得对应的消息 class
     * @param messageType 消息类型字节
     * @return 消息 class
     */
    public static Class<? extends Message> getMessageClass(int messageType) {
        return messageClasses.get(messageType);
    }

    // 登录请求消息
    public static final int LoginRequestMessage = 0;
    // 登录响应消息
    public static final int LoginResponseMessage = 1;
    // 单聊请求消息
    public static final int ChatRequestMessage = 2;
    // 单聊响应消息
    public static final int ChatResponseMessage = 3;
    // 创建群请求消息
    public static final int GroupCreateRequestMessage = 4;
    // 创建群响应消息
    public static final int GroupCreateResponseMessage = 5;
    // 加入群请求消息
    public static final int GroupJoinRequestMessage = 6;
    // 加入群响应消息
    public static final int GroupJoinResponseMessage = 7;
    // 退出群请求消息
    public static final int GroupQuitRequestMessage = 8;
    // 退出群响应消息
    public static final int GroupQuitResponseMessage = 9;
    // 群聊请求消息
    public static final int GroupChatRequestMessage = 10;
    // 群聊响应消息
    public static final int GroupChatResponseMessage = 11;
    // 获取群成员请求消息
    public static final int GroupMembersRequestMessage = 12;
    // 获取群成员响应消息
    public static final int GroupMembersResponseMessage = 13;
    // Ping请求消息
    public static final int PingMessage = 14;
    // Pong响应消息
    public static final int PongMessage = 15;

    static {
        messageClasses.put(LoginRequestMessage, LoginRequestMessage.class);
        messageClasses.put(LoginResponseMessage, LoginResponseMessage.class);
        messageClasses.put(ChatRequestMessage, ChatRequestMessage.class);
        messageClasses.put(ChatResponseMessage, ChatResponseMessage.class);
        messageClasses.put(GroupCreateRequestMessage, GroupCreateRequestMessage.class);
        messageClasses.put(GroupCreateResponseMessage, GroupCreateResponseMessage.class);
        messageClasses.put(GroupJoinRequestMessage, GroupJoinRequestMessage.class);
        messageClasses.put(GroupJoinResponseMessage, GroupJoinResponseMessage.class);
        messageClasses.put(GroupQuitRequestMessage, GroupQuitRequestMessage.class);
        messageClasses.put(GroupQuitResponseMessage, GroupQuitResponseMessage.class);
        messageClasses.put(GroupChatRequestMessage, GroupChatRequestMessage.class);
        messageClasses.put(GroupChatResponseMessage, GroupChatResponseMessage.class);
        messageClasses.put(GroupMembersRequestMessage, GroupMembersRequestMessage.class);
        messageClasses.put(GroupMembersResponseMessage, GroupMembersResponseMessage.class);
        messageClasses.put(PingMessage, PingMessage.class);
        messageClasses.put(PongMessage, PongMessage.class);
    }


}
