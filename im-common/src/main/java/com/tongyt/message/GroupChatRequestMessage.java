package com.tongyt.message;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class GroupChatRequestMessage extends Message {

    /**
     * 消息内容
     */
    private String content;

    /**
     * 群名称
     */
    private String groupName;

    /**
     * 消息发送者
     */
    private String from;



    public GroupChatRequestMessage(String from, String groupName, String content) {
        this.content = content;
        this.groupName = groupName;
        this.from = from;
    }

    @Override
    public int getMessageType() {
        return GroupChatRequestMessage;
    }
}
