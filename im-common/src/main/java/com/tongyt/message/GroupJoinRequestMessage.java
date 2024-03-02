package com.tongyt.message;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class GroupJoinRequestMessage extends Message {

    /**
     * 群名称
     */
    private String groupName;

    /**
     * 待加入用户
     */
    private String username;

    public GroupJoinRequestMessage(String username, String groupName) {
        this.groupName = groupName;
        this.username = username;
    }

    @Override
    public int getMessageType() {
        return GroupJoinRequestMessage;
    }
}
