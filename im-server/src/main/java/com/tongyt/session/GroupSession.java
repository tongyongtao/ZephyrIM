package com.tongyt.session;

import io.netty.channel.Channel;

import java.util.List;
import java.util.Set;

/**
 * 聊天组会话管理接口
 */
public interface GroupSession {

    /**
     * 创建一个聊天组, 如果创建成功返回true，否则返回flase
     * @param name 组名
     * @param members 成员
     * @return 创建成功返回true，否则返回flase
     */
    Boolean createGroup(String name, Set<String> members);

    /**
     * 加入聊天组
     * @param name 组名
     * @param member 成员名
     * @return 如果组不存在返回 null, 否则返回组对象
     */
    Group joinMember(String name, String member);

    /**
     * 移除组成员
     * @param name 组名
     * @param member 成员名
     * @return 如果组不存在返回 null, 否则返回组对象
     */
    Group removeMember(String name, String member);

    /**
     * 移除聊天组
     * @param name 组名
     * @return 如果组不存在返回 null, 否则返回组对象
     */
    Group removeGroup(String name);

    /**
     * 获取组成员
     * @param name 组名
     * @return 成员集合, 如果群不存在或没有成员会返回 empty set
     */
    Set<String> getMembers(String name);

    /**
     * 获取组成员的 channel 集合, 只有在线的 channel 才会返回
     * @param name 组名
     * @return 成员 channel 集合
     */
    List<Channel> getMembersChannel(String name);

    /**
     * 判断用户是否是群成员
     * @param groupName 群名称
     * @param username  用户名
     * @return 如果是群成员返回true，否则返回false
     */
    Boolean isMember(String groupName, String username);
}
