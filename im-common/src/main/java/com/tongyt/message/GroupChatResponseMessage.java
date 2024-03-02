package com.tongyt.message;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class GroupChatResponseMessage extends AbstractResponseMessage {

    /**
     * 消息发送用户
     */
    private String from;

    /**
     * 消息内容
     */
    private String content;

    public GroupChatResponseMessage(boolean success, String reason) {
        super(success, reason);
    }

    public GroupChatResponseMessage(String from, String content) {
        this.from = from;
        this.content = content;
    }
    @Override
    public int getMessageType() {
        return GroupChatResponseMessage;
    }
}
