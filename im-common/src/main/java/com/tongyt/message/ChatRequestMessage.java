package com.tongyt.message;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class ChatRequestMessage extends Message {

    /**
     * 消息内容
     */
    private String content;

    /**
     * 消息接收用户
     */
    private String to;

    /**
     * 消息发送用户
     */
    private String from;



    public ChatRequestMessage() {
    }

    public ChatRequestMessage(String from, String to, String content) {
        this.from = from;
        this.to = to;
        this.content = content;
    }

    @Override
    public int getMessageType() {
        return ChatRequestMessage;
    }
}
