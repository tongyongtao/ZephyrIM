package com.tongyt.message;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class GroupQuitRequestMessage extends Message {

    /**
     * 群名称
     */
    private String groupName;

    /**
     * 待退出群用户
     */
    private String username;

    public GroupQuitRequestMessage(String username, String groupName) {
        this.groupName = groupName;
        this.username = username;
    }

    @Override
    public int getMessageType() {
        return GroupQuitRequestMessage;
    }
}
