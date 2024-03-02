package com.tongyt.message;

import lombok.Data;
import lombok.ToString;

import java.util.Set;

@Data
@ToString(callSuper = true)
public class GroupCreateRequestMessage extends Message {

    /**
     * 群名称
     */
    private String groupName;

    /**
     * 初始群成员
     */
    private Set<String> members;

    public GroupCreateRequestMessage(String groupName, Set<String> members) {
        this.groupName = groupName;
        this.members = members;
    }

    @Override
    public int getMessageType() {
        return GroupCreateRequestMessage;
    }
}
